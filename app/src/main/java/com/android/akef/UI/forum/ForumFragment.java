package com.android.akef.UI.forum;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.akef.Interfaces.WebAppInterface;
import com.android.akef.R;
import com.android.akef.UI.WebViewActivity;
import com.android.akef.Utils.Variables;

public class ForumFragment extends Fragment {

    private ForumViewModel mViewModel;
    WebView webView;

    public static ForumFragment newInstance() {
        return new ForumFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.forum_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView = view.findViewById(R.id.forum_view);
        webView.addJavascriptInterface(new WebAppInterface(getActivity(),false), "Android");
        String url = "https://akef.in/staging/forums/";
        loadWebView(url, Variables.JS_KEY,false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ForumViewModel.class);
        // TODO: Use the ViewModel
    }

    public void loadWebView(final String weburl,String jsKey,boolean requiresRefresh){
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
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url)
            {
                Log.e("WebviewActivity", "onPageFinished: " + jsKey );
                webView.loadUrl(jsKey);
            }
        });
        webView.loadUrl(weburl);
        webView.reload();
        if(requiresRefresh) {
            webView.reload();
        }
    }


}