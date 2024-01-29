package com.pranavkd.campustracker_cloud;

import android.content.Context;
import android.content.SharedPreferences;

public class constantsetup {
    private String url = null;
    private String key = null;
    private int faculity = 0;
    SharedPreferences sharedPreferences;

    public constantsetup(Context context) {
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    }

    public void addUser(String url, String key, int faculity) {
        sharedPreferences.edit().putString("url", url).apply();
        sharedPreferences.edit().putString("key", key).apply();
        sharedPreferences.edit().putInt("faculity", faculity).apply();
    }

    public String getURL() {
        url = sharedPreferences.getString("url", "");
        return url;
    }

    public String getKey() {
        key = sharedPreferences.getString("key", "");
        return key;
    }
    public int getFaculity() {
        faculity = sharedPreferences.getInt("faculity", 0);
        return faculity;
    }

    public boolean isLoginDetailsExist() {
        url = sharedPreferences.getString("url", "");
        key = sharedPreferences.getString("key", "");
        faculity = sharedPreferences.getInt("faculity", 0);
        if (url.equals("") || key.equals("") || faculity == 0) {
            return false;
        } else {
            return true;
        }
    }
}