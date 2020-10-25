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
        this.finishAfterFuncCall = finishAfterFuncCall;
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
    public void onLoginSuccess(String Id,String userName,String profilePic,String coverPic,String profileLink) {
        Log.e("WebAppInterface", "onLoginSuccess: " + userName );
        long userID = Long.parseLong(Id);
        if(userID == 0){
            repository.deleteAllUsers();
            return;
        }
        User user = new User(userID,userName,profilePic,"0",profileLink,coverPic);
        if (finishAfterFuncCall) {
            Variables.setSuccessToast(mContext, "Login Successful!!");
        }
        repository.insertUser(user, new DatabaseFetchListener() {
            @Override
            public <T> void onLoadingFinished(T o) {

                if(finishAfterFuncCall) {
                    ((Activity) mContext).finish();
                }
            }
        });

    }
}