package com.android.akef.UI;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.akef.R;
import com.android.akef.Utils.Variables;

public class WebViewActivity extends AppCompatActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String url = getIntent().getExtras().getString(Variables.WEBVIEW_URL_KEY,"");
        String jsKey = getIntent().getExtras().getString(Variables.WEBVIEW_JAVASCRIPT_KEY,"");
        String title = getIntent().getExtras().getString(Variables.WEBVIEW_TITLE,"AKEF");
        boolean requiresRefresh = getIntent().getBooleanExtra(Variables.REQUIRES_REFRESH,false);
        getSupportActionBar().setTitle(title);
        loadWebView(url,jsKey,requiresRefresh);
    }

    public void loadWebView(final String weburl,String jsKey,boolean requiresRefresh){
        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebChromeClient(new WebChromeClient(){
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
        });
        // webView.loadDataWithBaseURL("",html, "text/html", "UTF-8","");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url)
            {
/*                webView.loadUrl("javascript:(function() { " +
                        "var head = document.getElementsByClassName('edgtf-mobile-header')[0].style.display='none'; " +
                        "var head = document.getElementsByClassName('ubermenu-responsive-toggle ubermenu-responsive-toggle-main ubermenu-skin-grey-white ubermenu-loc-header-menu ubermenu-responsive-toggle-content-align-left ubermenu-responsive-toggle-align-full ')[0].style.display='none'; " +
                        "var head = document.getElementsByClassName('navbar-wrapper')[0].style.display='none'; " +
                        "var head = document.getElementById('footer').style.display='none'; " +
                        "})()");*/
                Log.e("WebviewActivity", "onPageFinished: " + jsKey );
                webView.loadUrl(jsKey);
            }
        });
        webView.loadUrl(weburl);
        if(requiresRefresh) {
            webView.reload();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}