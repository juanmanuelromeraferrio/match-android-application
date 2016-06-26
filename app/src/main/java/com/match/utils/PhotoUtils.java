package com.match.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Base64;

import com.match.MatchApplication;
import com.match.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Juan Manuel Romera on 24/9/2015.
 */
public class PhotoUtils {

    public static int PHOTO_WIDHT_MAX = 800;
    public static int PHOTO_HEIGHT_MAX = 600;

    public static Bitmap resizeImage(Bitmap bitmap, int width, int height) {
        float factorH = height / (float) bitmap.getHeight();
        float factorW = width / (float) bitmap.getWidth();

        if (factorH > 1 || factorW > 1) {
            return bitmap;
        }

        float factorToUse = (factorH > factorW) ? factorW : factorH;
        Bitmap bm = Bitmap.createScaledBitmap(bitmap,
                (int) (bitmap.getWidth() * factorToUse),
                (int) (bitmap.getHeight() * factorToUse), false);
        return bm;
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, int wantedWidth, int wantedHeight) {
        Bitmap output = Bitmap.createBitmap(wantedWidth, wantedHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Matrix m = new Matrix();
        m.setScale((float) wantedWidth / bitmap.getWidth(), (float) wantedHeight / bitmap.getHeight());
        canvas.drawBitmap(bitmap, m, new Paint());

        return output;
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static Bitmap base64ToBitmap(String base64) {
        byte[] imageAsBytes = Base64.decode(base64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

    public static Bitmap decodeMediaImage(Context context, Intent data) {
        if (data == null) {
            return null;
        }
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(data.getData());
            Bitmap photo = BitmapFactory.decodeStream(inputStream);
            Bitmap bitmapResized = scaleBitmap(photo, PhotoUtils.PHOTO_WIDHT_MAX, PhotoUtils.PHOTO_HEIGHT_MAX);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmapResized.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            byte[] bitmapArray = bos.toByteArray();
            return BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}

