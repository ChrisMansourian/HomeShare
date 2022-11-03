package com.bgs.homeshare.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class Util {
    public static Bitmap ConvertBytesToImage(byte[] img) {
        if (img != null && img.length > 0) {
            return BitmapFactory.decodeByteArray(img,0,img.length);
        }
        return null;
    }

    public static byte[] ConvertImageToBytes(Bitmap img) {
        if (img == null) {
            return new byte[0];
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }

    public static Boolean returnBoolFromInt(int i){
        Boolean z = true;
        if(i >= 1){
            return z;
        }
        z = false;
        return z;
    }


}
