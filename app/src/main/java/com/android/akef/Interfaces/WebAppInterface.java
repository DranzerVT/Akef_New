package com.android.akef.Interfaces;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.android.akef.Database.Repository;
import com.android.akef.Tables.User;
import com.android.akef.Utils.Variables;

public class WebAppInterface {
    Context mContext;
    Repository repository;
    boolean finishAfterFuncCall = false;

    /** Instantiate the interface and set the context */
    public WebAppInterface(Context c,boolean finishAfterFuncCall) {
        mContext = c;
        repository = Repository.getInstance(((Activity)mContext).getApplication());
    }

 /*   *//** Show a toast from the web page *//*
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }
*/

    @JavascriptInterface
    public void onLogoutSuccess() {
        repository.deleteAllUsers();
    }

    @JavascriptInterface
    public void onLoginSuccess(String Id,String userName,String profilePic,String profileLink) {
        Log.e("WebAppInterface", "onLoginSuccess: " + userName );
        Variables.setSuccessToast(mContext,"Login Successful!!");
        long userID = Long.parseLong(Id);
        User user = new User(userID,userName,profilePic,"0",profileLink);
        repository.insertUser(user, new DatabaseFetchListener() {
            @Override
            public <T> void onLoadingFinished(T o) {

                ((Activity)mContext).finish();
            }
        });
    }
}