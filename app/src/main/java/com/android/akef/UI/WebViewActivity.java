package com.android.akef.UI;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.akef.R;
import com.android.akef.Utils.Variables;

public class WebViewActivity extends AppCompatActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        String url = getIntent().getExtras().getString(Variables.WEBVIEW_URL_KEY,"");
        loadWebView(url);
    }

    public void loadWebView(final String weburl){
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
                webView.loadUrl("javascript:(function() { " +
                        "var head = document.getElementsByClassName('edgtf-mobile-header')[0].style.display='none'; " +
                        "var head = document.getElementsByClassName('edgtf-page-footer')[0].style.display='none'; " +
                        "var head = document.getElementsByClassName('blog-sidebar')[0].style.display='none'; " +
                        "var head = document.getElementsByClassName('footer-container')[0].style.display='none'; " +
                        "})()");
                //webView.loadUrl(Variables.JAVASCRIPT_FUNC);
            }
        });
        webView.loadUrl(weburl);
        webView.reload();
    }

}