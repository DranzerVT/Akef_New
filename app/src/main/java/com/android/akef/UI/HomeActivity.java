package com.android.akef.UI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.akef.Database.Repository;
import com.android.akef.Interfaces.WebAppInterface;
import com.android.akef.R;
import com.android.akef.Tables.User;
import com.android.akef.Utils.CircleTransform;
import com.android.akef.Utils.Variables;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    Repository repository;
    boolean isLoggedIn = false;
    User currentUser;
    DrawerLayout drawer;
    NavigationView navigationView;
    TextView userName;
    TextView userLevel;
    ImageView userImage;
    LinearLayout coverPic;
    WebView webView;
    FloatingActionButton fabVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fabVideo = findViewById(R.id.fab);
        fabVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isStoragePermissionGranted()){
                    Intent intent = new Intent(HomeActivity.this,ReelUploadActivity.class);
                    startActivity(intent);
                }
            }
        });

        repository = Repository.getInstance(getApplication());
        webView = new WebView(this);
        webView.setVisibility(View.GONE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebAppInterface(HomeActivity.this,false), "Android");
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.loadUrl(Variables.REFRESH_URL);
        setupNavDrawer();
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkLoginState();
    }

    public void checkLoginState(){

        currentUser = repository.getLoggedInUser();
        if(currentUser!=null && currentUser.getUserID() != 0){
            isLoggedIn = true;
        }else{
            isLoggedIn = false;
        }
        
        if(isLoggedIn){
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
            navigationView.getMenu().findItem(R.id.profile).setVisible(true);
            navigationView.getMenu().findItem(R.id.settings).setVisible(true);
            navigationView.getMenu().findItem(R.id.mytournament).setVisible(true);
            navigationView.getMenu().findItem(R.id.matches).setVisible(true);
            userLevel.setVisibility(View.GONE);
            fabVideo.setVisibility(View.VISIBLE);
            userLevel.setText(currentUser.getLevel());
            userName.setText(currentUser.getUserName());
            Glide.with(HomeActivity.this)
                    .load(currentUser.getProfile())
                    .transform(new CircleTransform())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.progress_animation)
                            .error(R.drawable.noimage))
                    .into(userImage);

            Glide.with(HomeActivity.this)
                    .load(currentUser.getCoverPic())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.progress_animation)
                            .error(R.drawable.side_nav_bar))
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            coverPic.setBackground(resource);
                        }
                    });


        }else{
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
            navigationView.getMenu().findItem(R.id.profile).setVisible(false);
            navigationView.getMenu().findItem(R.id.settings).setVisible(false);
            navigationView.getMenu().findItem(R.id.mytournament).setVisible(false);
            navigationView.getMenu().findItem(R.id.matches).setVisible(false);
            userName.setText("Guest");
            userLevel.setVisibility(View.GONE);
            fabVideo.setVisibility(View.GONE);
            Glide.with(HomeActivity.this)
                    .load(R.drawable.noimage)
                    .transform(new CircleTransform())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.progress_animation)
                            .error(R.drawable.noimage))
                    .into(userImage);

            Glide.with(HomeActivity.this)
                    .load(R.drawable.side_nav_bar)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.progress_animation)
                            .error(R.drawable.side_nav_bar))
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            coverPic.setBackground(resource);
                        }
                    });
        }

    }

    public void setupNavDrawer(){
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        coverPic = navigationView.getHeaderView(0).findViewById(R.id.cover_pic);
        userName = navigationView.getHeaderView(0).findViewById(R.id.user_name_txt);
        userLevel = navigationView.getHeaderView(0).findViewById(R.id.level_txt);
        userImage = navigationView.getHeaderView(0).findViewById(R.id.profile_imageView);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_tournament,
                R.id.nav_home, R.id.nav_profile,R.id.nav_latest_updates, R.id.nav_streamers_connect,R.id.nav_forum,
                R.id.nav_settings,R.id.nav_mytournament,R.id.nav_matches)
                .setDrawerLayout(drawer)
                .build();

        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //hide nav items that are currently not supported
        navigationView.getMenu().findItem(R.id.nav_latest_updates).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_streamers_connect).setVisible(false);

        //Add additional click events which are not covered in nav graph such as log out or settings activity
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Bundle bundle;
                if(menuItem.getItemId() != R.id.nav_login && menuItem.getItemId() != R.id.nav_logout){
                    webView.reload();
                    checkLoginState();
                }
                switch (menuItem.getItemId()) {
                    case R.id.forum:
                        bundle = new Bundle();
                        bundle.putString("URL", Variables.FORUMS_URL);
                        navController.navigate(R.id.action_global_nav_forum,bundle);
                        break;
                    case R.id.profile:
                        bundle = new Bundle();
                        bundle.putString("URL", currentUser.getLink());
                        navController.navigate(R.id.action_global_nav_profile,bundle);
                        break;
                    case R.id.mytournament:
                        bundle = new Bundle();
                        bundle.putString("URL", Variables.MYTOURNAMENTS_URL);
                        navController.navigate(R.id.action_global_nav_mytournament,bundle);
                        break;
                    case R.id.matches:
                        bundle = new Bundle();
                        bundle.putString("URL", Variables.MATCHES_URL);
                        navController.navigate(R.id.action_global_nav_matches,bundle);
                        break;
                    case R.id.settings:
                        bundle = new Bundle();
                        bundle.putString("URL", Variables.SETTINGS_URL);
                        navController.navigate(R.id.action_global_nav_settings,bundle);
                        break;
                    case R.id.nav_game_reels:
                        Intent reelIntent = new Intent(HomeActivity.this,GameReelsActivity.class);
                        startActivity(reelIntent);
                        break;
                    case R.id.nav_login:
                        Intent loginIntent = new Intent(HomeActivity.this, WebViewActivity.class);
                        loginIntent.putExtra(Variables.WEBVIEW_URL_KEY,Variables.LOGIN_URL);
                        loginIntent.putExtra(Variables.WEBVIEW_JAVASCRIPT_KEY,Variables.JS_KEY_LOGIN);
                        loginIntent.putExtra(Variables.REQUIRES_REFRESH,false);
                        loginIntent.putExtra(Variables.WEBVIEW_TITLE,"Login");
                        startActivity(loginIntent);
                        break;
                    case R.id.nav_logout:
                        Intent logoutIntent = new Intent(HomeActivity.this, WebViewActivity.class);
                        logoutIntent.putExtra(Variables.WEBVIEW_URL_KEY,Variables.LOGOUT_URL);
                        logoutIntent.putExtra(Variables.WEBVIEW_JAVASCRIPT_KEY,Variables.JS_KEY_LOGIN);
                        logoutIntent.putExtra(Variables.REQUIRES_REFRESH,false);
                        logoutIntent.putExtra(Variables.WEBVIEW_TITLE,"Logout");
                        startActivity(logoutIntent);
                        break;
                }
                //This is for maintaining the behavior of the Navigation view
                NavigationUI.onNavDestinationSelected(menuItem,navController);
                //This is for closing the drawer after acting on it
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("HomeActivity", "Permission is granted");
                return true;
            } else {
                Dexter.withContext(this)
                        .withPermissions(
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ).withListener(new MultiplePermissionsListener() {
                    @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if(report.areAllPermissionsGranted()){
                            Log.v("HomeActivity", "Permission is granted");
                        }
                    }
                    @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
                }).check();
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("HomeActivity", "Permission is granted");
            return true;
        }
    }



}