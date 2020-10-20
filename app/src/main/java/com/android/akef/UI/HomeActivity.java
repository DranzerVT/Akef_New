package com.android.akef.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.akef.Database.Repository;
import com.android.akef.R;
import com.android.akef.Tables.User;
import com.android.akef.Utils.CircleTransform;
import com.android.akef.Utils.Variables;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.w3c.dom.Text;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setupNavDrawer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        repository = Repository.getInstance(getApplication());
        currentUser = repository.getLoggedInUser();
        if(currentUser!=null){
            isLoggedIn = true;
        }else{
            isLoggedIn = false;
        }

        checkLoginState();
    }

    public void checkLoginState(){

        if(isLoggedIn){
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
            userLevel.setVisibility(View.GONE);
            userLevel.setText(currentUser.getLevel());
            userName.setText(currentUser.getUserName());
            Glide.with(HomeActivity.this)
                    .load(currentUser.getProfile())
                    .transform(new CircleTransform())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.progress_animation)
                            .error(R.drawable.noimage))
                    .into(userImage);

        }else{
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
            userName.setText("Guest");
            userLevel.setVisibility(View.GONE);
            Glide.with(HomeActivity.this)
                    .load(R.drawable.noimage)
                    .transform(new CircleTransform())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.progress_animation)
                            .error(R.drawable.noimage))
                    .into(userImage);
        }

    }

    public void setupNavDrawer(){
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        userName = navigationView.getHeaderView(0).findViewById(R.id.user_name_txt);
        userLevel = navigationView.getHeaderView(0).findViewById(R.id.level_txt);
        userImage = navigationView.getHeaderView(0).findViewById(R.id.profile_imageView);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_tournament,
                R.id.nav_home, R.id.nav_latest_updates, R.id.nav_streamers_connect,R.id.nav_forum)
                .setDrawerLayout(drawer)
                .build();

        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //to hide nav drawer items
        navigationView.getMenu().findItem(R.id.nav_latest_updates).setVisible(false);

        //Add additional click events which are not covered in nav graph such as log out or settings activity
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_settings:
                        Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_login:
                        Intent loginIntent = new Intent(HomeActivity.this, WebViewActivity.class);
                        loginIntent.putExtra(Variables.WEBVIEW_URL_KEY,"https://akef.in/staging/function-test-2/");
                        loginIntent.putExtra(Variables.WEBVIEW_JAVASCRIPT_KEY,Variables.JS_KEY_LOGIN);
                        loginIntent.putExtra(Variables.REQUIRES_REFRESH,false);
                        loginIntent.putExtra(Variables.WEBVIEW_TITLE,"Login");
                        startActivity(loginIntent);
                        break;

                    case R.id.nav_logout:
                        Intent logoutIntent = new Intent(HomeActivity.this, WebViewActivity.class);
                        logoutIntent.putExtra(Variables.WEBVIEW_URL_KEY,"https://akef.in/staging/wp-login.php?action=logout&redirect_to=https://akef.in/staging/function-test-2/");
                        logoutIntent.putExtra(Variables.WEBVIEW_JAVASCRIPT_KEY,Variables.JS_KEY);
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
}