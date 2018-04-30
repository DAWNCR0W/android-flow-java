package com.donghyeokseo.flow;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public final class Util {

    //out list status code
    public static final int OUTGO_APPROVED = 1;
    public static final int OUTGO_DECLINED = 2;
    public static final int OUTGO_REQUESTED = 3;
    public static final int OUTSLEEP_APPROVED = 4;
    public static final int OUTSLEEP_DECLINED = 5;
    public static final int OUTSLEEP_REQUESTED = 6;

    public static int deviceNavbarHeight = 0;
    public static String SERVER_HOST = "http://flow.cafe24app.com/";

    public static void getDeviceNavigationBarHeigh(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            deviceNavbarHeight = resources.getDimensionPixelSize(resourceId);
        }
    }

    public static String encryption(String input) {
        String output = "";
        StringBuilder sb = new StringBuilder();

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if (md == null) {
            return "";
        }

        md.update(input.getBytes());

        byte[] msgb = md.digest();

        for (byte temp : msgb) {
            StringBuilder str = new StringBuilder(Integer.toHexString(temp & 0xFF));
            while (str.length() < 2) {
                str.insert(0, "0");
            }
            str = new StringBuilder(str.substring(str.length() - 2));
            sb.append(str);
        }
        output = sb.toString();

        return output;
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

    public static boolean isSchoolEmail(String email) {
        return email != null && Pattern.matches("[\\w\\~\\-\\.]+@(dgsw\\.hs\\.kr)+$", email);
    }

    public static boolean isValidPassword(String password) {
        return password != null && Pattern.matches("^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~`]{8,16}$", password);
    }

    public static boolean hasSpecialCharacter(String string) {
        if (TextUtils.isEmpty(string)) {
            return false;
        }
        for (int i = 0; i < string.length(); i++) {
            if (!Character.isLetterOrDigit(string.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}
