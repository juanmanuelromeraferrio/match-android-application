package com.match.activity.chat;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.match.R;
import com.match.client.entities.User;
import com.match.service.factory.ServiceFactory;

import java.util.ArrayList;


public class ChatActivity extends AppCompatActivity {

    private ArrayList<String> candidatesNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        User localUser = ServiceFactory.getUserService().getLocalUser();

        ListView listMatches = (ListView) findViewById(R.id.listViewCandidates);
        candidatesNameList = new ArrayList<String>();
        candidatesNameList.add("Candidato1");
        candidatesNameList.add("Candidato2");
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, candidatesNameList);
        listMatches.setAdapter(arrayAdapter);

        listMatches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // argument position gives the index of item which is clicked
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {

                String selectedAnimal = candidatesNameList.get(position);
                Toast.makeText(ChatActivity.this, "", Toast.LENGTH_SHORT).show();
                //st.makeText(getApplicationContext(), "Animal Selected : " + selectedAnimal, Toast.LENGTH_LONG).show();
            }
        });



        // Get the reference of ListViewAnimals
      /*  ListView animalList = (ListView) findViewById(R.id.listViewCandidates);


        candidatesNameList = new ArrayList<String>();
        getAnimalNames();
        // Create The Adapter with passing ArrayList as 3rd parameter
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, candidatesNameList);
        // Set The Adapter
        animalList.setAdapter(arrayAdapter);

        // register onClickListener to handle click events on each item
        animalList.setOnItemClickListener(new OnItemClickListener() {
            // argument position gives the index of item which is clicked
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {

                String selectedAnimal = animalsNameList.get(position);
                Toast.makeText(getApplicationContext(), "Animal Selected : " + selectedAnimal, Toast.LENGTH_LONG).show();
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();*/
    }



}
