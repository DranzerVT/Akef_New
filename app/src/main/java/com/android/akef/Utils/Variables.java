package com.android.akef.Utils;

import android.util.Log;

import com.google.gson.Gson;

//class to store static variables
public class Variables {

    public static final String WEBVIEW_URL_KEY = "WebViewURL";
    public static final String JAVASCRIPT_FUNC = "\"javascript:(function() { \" +\n" +
            "                        \"var head = document.getElementsByClassName('edgtf-mobile-header')[0].style.display='none'; \" +\n" +
            "                        \"var head = document.getElementsByClassName('blog-sidebar')[0].style.display='none'; \" +\n" +
            "                        \"var head = document.getElementsByClassName('footer-container')[0].style.display='none'; \" +\n" +
            "                        \"})()\"";

    public static final String BASE_URL = "https://akef.in/staging/wp-json/wp/v2/";


    public static void showSendData(Object obj, String serviceName) {
        Log.i("serviceData", "serviceName :" + serviceName + ",  showJsonData: " + new Gson().toJson(obj));

    }

    public static void showResponseData(Object obj, String serviceName) {
        Log.d("serviceData", "serviceName :" + serviceName + ", showResponseData: " + new Gson().toJson(obj));
    }

    public static void showErrorData(Object obj, String serviceName) {

        Log.e("serviceData", "serviceName :" + serviceName + ", showerrorData: " + new Gson().toJson(obj));
    }

    public static void showRequesturl(String url, String serviceName) {
        Log.e("serviceData", "serviceName :" + serviceName + ", showRequesturl: " + url);
    }
}
