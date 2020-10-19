package com.android.akef.Utils;

import android.util.Log;

import com.google.gson.Gson;

//class to store static variables
public class Variables {

    public static final String WEBVIEW_URL_KEY = "WebViewURL";
    public static final String WEBVIEW_JAVASCRIPT_KEY = "WebViewJS";
    public static final String BASE_URL = "https://akef.in/staging/wp-json/wp/v2/";
    public static final String REQUIRES_REFRESH = "RequiresRefresh" ;
    public static final String WEBVIEW_TITLE = "WebViewTitle" ;


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
