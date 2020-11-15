package com.android.akef.Utils;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.akef.Database.Repository;
import com.android.akef.R;
import com.android.akef.Tables.User;
import com.google.gson.Gson;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.text.SimpleDateFormat;
import java.util.Date;

//class to store static variables
public class Variables {

    public static final String WEBVIEW_URL_KEY = "WebViewURL";
    public static final String WEBVIEW_JAVASCRIPT_KEY = "WebViewJS";

    public static final String REQUIRES_REFRESH = "RequiresRefresh" ;
    public static final String PROMPT_LOGIN = "PromptLogin";
    public static final String WEBVIEW_TITLE = "WebViewTitle" ;
    public static final long MAX_VIDEO_DURATION = 1 ; //in minutes
    public static final String COLLECTION_REEL = "game_reels";

    //URLS
    public static final String DOMAIN_URL = "https://staging.akef.in";
    public static final String BASE_URL = DOMAIN_URL+"/wp-json/wp/v2/";
    public static final String LOGIN_URL= DOMAIN_URL+"/function-test-2/";
    public static final String LOGOUT_URL= DOMAIN_URL+"/wp-login.php?action=logout&redirect_to="+DOMAIN_URL+"/function-test-2/";
    public static final String FORUMS_URL= DOMAIN_URL+"/forums/";
    public static final String PROFILE_URL= DOMAIN_URL+"/members/me/";
    public static final String SETTINGS_URL= PROFILE_URL + "settings/";
    public static final String REFRESH_URL= DOMAIN_URL+ "/function-test/";
    public static final String MYTOURNAMENTS_URL = PROFILE_URL+"tournaments/" ;
    public static final String MATCHES_URL = PROFILE_URL+ "matches/";

    public final static int FILECHOOSER_RESULTCODE = 1;
    public final static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 2;
    public static final String JS_KEY = "javascript:(function() { " +
            "var head = document.getElementsByClassName('ubermenu-responsive-toggle ubermenu-responsive-toggle-main ubermenu-skin-grey-white ubermenu-loc-header-menu ubermenu-responsive-toggle-content-align-left ubermenu-responsive-toggle-align-full ')[0].style.display='none'; " +
            "var head = document.getElementsByClassName('navbar-wrapper ')[0].style.display='none'; " +
            "var head = document.getElementById('footer').style.display='none'; " +
            "var head = document.getElementsByClassName('logoutItem')[0].style.display='none'; " +
            "var head = document.getElementsByClassName('logoutItem')[1].style.display='none'; " +
            "})();";

    public static String LOGIN_HIDE_JS ="javascript:(function() { " +
            "var head = document.getElementsByClassName('login-btn button-small jtournamentb')[0].style.display='none'; " +
            "var head = document.getElementsByClassName('login-btn button-small jtournamentb')[1].style.display='none'; " +
            "})();";
    public static final String JS_KEY_LOGIN = "javascript:(function() { " +
            "var head = document.getElementsByClassName('ubermenu-responsive-toggle ubermenu-responsive-toggle-main ubermenu-skin-grey-white ubermenu-loc-header-menu ubermenu-responsive-toggle-content-align-left ubermenu-responsive-toggle-align-full ')[0].style.display='none'; " +
            "var head = document.getElementsByClassName('navbar-wrapper ')[0].style.display='none'; " +
            "var head = document.getElementsByClassName('title_wrapper container')[0].style.display='none'; " +
            "var head = document.getElementById('footer').style.display='none'; " +
            "})();" ;

    public static final String WEBVIEW_USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Safari/537.36";
    public static final String WEBVIEW_USER_AGENT_CUSTOM = "AKEF";


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

    public static void setInfoToast(Context context, String message) {

        MDToast.makeText(context, message, MDToast.LENGTH_LONG, MDToast.TYPE_INFO).show();
    }

    public static void setSuccessToast(Context context, String message) {

        MDToast.makeText(context, message, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
    }

    public static void setWarningToast(Context context, String message) {

        MDToast.makeText(context, message, MDToast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
    }

    public static boolean isUserLoggedIn(Application applicationContext){
        Repository repository = Repository.getInstance(applicationContext);
        User currentUser = repository.getLoggedInUser();
        return currentUser != null && currentUser.getUserID() != 0;
    }

    public static String getDateTimeStamp(){
        String pattern = "yyyyMMddHHmmss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(new Date());
        return date;
    }
}
