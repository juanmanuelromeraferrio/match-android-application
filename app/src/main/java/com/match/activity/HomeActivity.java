package com.match.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.match.R;
import com.match.fragment.HomeFragment;
import com.match.utils.WaitForInternet;
import com.match.utils.WaitForInternetCallback;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private String currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WaitForInternetCallback callback = new WaitForInternetCallback(this) {
            public void onConnectionSuccess() {
                setContentView(R.layout.activity_home);

                toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);

                FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
                Fragment fragment = new HomeFragment();
                tx.replace(R.id.frame, fragment);
                tx.commit();


                // Fill user information in header.
/*                CircleImageView imageProfileView = (CircleImageView) findViewById(R.id.profile_image);
                imageProfileView.setImageResource(R.drawable.user_male);*/

/*
                TextView userName = (TextView) findViewById(R.id.username);
                userName.setText(user.getName());

                TextView email = (TextView) findViewById(R.id.email);
                if (user.getEmail() != null) {
                    email.setText(user.getEmail());
                }*/

                navigationView = (NavigationView) findViewById(R.id.navigation_view);
                navigationView.setCheckedItem(R.id.drawer_home);
                navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        return selectFragment(menuItem);
                    }
                });

                drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
                ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(mActivity, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {
                    @Override
                    public void onDrawerClosed(View drawerView) {
                        super.onDrawerClosed(drawerView);
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        super.onDrawerOpened(drawerView);
                    }
                };
                drawerLayout.setDrawerListener(actionBarDrawerToggle);
                actionBarDrawerToggle.syncState();
            }
        };
        WaitForInternet.setCallback(callback);
    }

    private boolean selectFragment(MenuItem menuItem) {
        Fragment fragment = null;
        FragmentTransaction fragmentTransaction;

        switch (menuItem.getItemId()) {
            case R.id.drawer_home:
                fragment = new HomeFragment();
                currentFragment = getResources().getString(R.string.app_name);
                break;
            default:
                break;
        }

        if (fragment != null) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.commit();
            getSupportActionBar().setTitle(currentFragment);
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            super.onBackPressed();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Desea salir de la aplicación?")
                .setCancelable(false)
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        HomeActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
