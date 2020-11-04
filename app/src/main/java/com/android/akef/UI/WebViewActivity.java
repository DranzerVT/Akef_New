package com.android.akef.UI;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.akef.Interfaces.WebAppInterface;
import com.android.akef.R;
import com.android.akef.Utils.Variables;

public class WebViewActivity extends AppCompatActivity {

    WebView webView;
    public ValueCallback<Uri[]> uploadMessage;
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadMessageForAndroid5;
    boolean finishAfterFuncCall = false;
    boolean promptLogin = false;
    Button loginBtn;
    LinearLayout loginPromptContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loginBtn = findViewById(R.id.login_prompt_btn);
        loginPromptContainer = findViewById(R.id.login_container);
        String url = getIntent().getExtras().getString(Variables.WEBVIEW_URL_KEY,"");
        String jsKey = getIntent().getExtras().getString(Variables.WEBVIEW_JAVASCRIPT_KEY,"");
        String title = getIntent().getExtras().getString(Variables.WEBVIEW_TITLE,"AKEF");
        boolean requiresRefresh = getIntent().getBooleanExtra(Variables.REQUIRES_REFRESH,false);
        promptLogin = getIntent().getBooleanExtra(Variables.PROMPT_LOGIN,false);
        getSupportActionBar().setTitle(title);
        loadWebView(url,jsKey,requiresRefresh);

        if(promptLogin){
            if(!Variables.isUserLoggedIn(getApplication())){
                loginPromptContainer.setVisibility(View.VISIBLE);
            }else{
                loginPromptContainer.setVisibility(View.GONE);
            }
        }
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(WebViewActivity.this, WebViewActivity.class);
                loginIntent.putExtra(Variables.WEBVIEW_URL_KEY,Variables.LOGIN_URL);
                loginIntent.putExtra(Variables.WEBVIEW_JAVASCRIPT_KEY,Variables.JS_KEY_LOGIN);
                loginIntent.putExtra(Variables.REQUIRES_REFRESH,false);
                loginIntent.putExtra(Variables.WEBVIEW_TITLE,"Login");
                startActivity(loginIntent);
                finish();
            }
        });

    }

    public void loadWebView(final String weburl,String jsKey,boolean requiresRefresh){
        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        if(weburl.equals(Variables.LOGIN_URL) || weburl.equals(Variables.LOGOUT_URL)) {
            webView.getSettings().setUserAgentString(Variables.WEBVIEW_USER_AGENT_CUSTOM);
            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            finishAfterFuncCall = true;
        }
        webView.addJavascriptInterface(new WebAppInterface(WebViewActivity.this,finishAfterFuncCall), "Android");
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url)
            {
                Log.e("WebviewActivity", "onPageFinished: " + jsKey );
               // webView.loadUrl(jsKey);
                webView.evaluateJavascript(jsKey,null);
                if (promptLogin) {
                    webView.evaluateJavascript(Variables.LOGIN_HIDE_JS, null);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return false;
            }
        });
        webView.loadUrl(weburl);
        if(requiresRefresh) {
            webView.reload();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (webView.canGoBack() && !webView.getUrl().equals(Variables.LOGIN_URL)) {
            webView.goBack();
        } else {
            onBackPressed();
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack() && !webView.getUrl().equals(Variables.LOGIN_URL)) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {
            final boolean remember = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(WebViewActivity.this);
            builder.setTitle("Locations");
            builder.setMessage("Would like to use your Current Location ")
                    .setCancelable(true).setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // origin, allow, remember
                    callback.invoke(origin, true, remember);
                }
            }).setNegativeButton("Don't Allow", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // origin, allow, remember
                    callback.invoke(origin, false, remember);
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }

        // For 3.0+ Devices (Start)
        // onActivityResult attached before constructor
        protected void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            startActivityForResult(Intent.createChooser(i, "File Chooser"), Variables.FILECHOOSER_RESULTCODE);
        }


        // For Lollipop 5.0+ Devices
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            uploadMessage = filePathCallback;
            openFileChooserImplForAndroid5(uploadMessage);
            return true;

        }

        //For Android 4.1 only
        protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            mUploadMessage = uploadMsg;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "File Chooser"), Variables.FILECHOOSER_RESULTCODE);
        }

        protected void openFileChooser(ValueCallback<Uri> uploadMsg) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            startActivityForResult(Intent.createChooser(i, "File Chooser"), Variables.FILECHOOSER_RESULTCODE);
        }
    }

    private void openFileChooserImplForAndroid5(ValueCallback<Uri[]> uploadMsg) {
        mUploadMessageForAndroid5 = uploadMsg;
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
        contentSelectionIntent.setType("image/*");

        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");

        startActivityForResult(chooserIntent, Variables.FILECHOOSER_RESULTCODE_FOR_ANDROID_5);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.v("ForumFragment", "ForumFragment # onActivityResult # requestCode=" + requestCode + " # resultCode=" + resultCode);
        if (requestCode == Variables.FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            Uri result = intent == null || resultCode != Activity.RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;

        } else if (requestCode == Variables.FILECHOOSER_RESULTCODE_FOR_ANDROID_5) {
            if (null == mUploadMessageForAndroid5)
                return;
            Uri result;

            if (intent == null || resultCode != Activity.RESULT_OK) {
                result = null;
            } else {
                result = intent.getData();
            }

            if (result != null) {
                Log.v("ForumFragment", "ForumFragment # result.getPath()=" + result.getPath());
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
            } else {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{});
            }
            mUploadMessageForAndroid5 = null;
        }
    }

}