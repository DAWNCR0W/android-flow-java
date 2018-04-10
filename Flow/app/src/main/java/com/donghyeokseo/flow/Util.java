package com.donghyeokseo.flow;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {
    public static int deviceNavbarHeight = 0;
    public static String SERVER_HOST = "http://flow.cafe24app.com/";

    public static void getDeviceNavigationBarHeigh(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            deviceNavbarHeight = resources.getDimensionPixelSize(resourceId);
        }
    }

    public static String encryption(String str){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(str.getBytes());
            byte byteData[] = md.digest();

            //convert the byte to hex format method 1
            StringBuilder hashCodeBuffer = new StringBuilder();
            for (byte aByteData : byteData) {
                hashCodeBuffer.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
            }
            return hashCodeBuffer.toString();
        } catch (NoSuchAlgorithmException ignored) {
            return null;
        }
    }

    public static String inputStreamToString(HttpURLConnection urlConn) throws IOException {
        InputStreamReader isr;
        try {
            isr = new InputStreamReader(urlConn.getErrorStream());
        } catch (Exception e) {
            isr = new InputStreamReader(urlConn.getInputStream());
        }
        final BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }

        br.close();

        return sb.toString();
    }
}
