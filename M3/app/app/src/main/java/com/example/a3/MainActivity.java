package com.example.a3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.a3.nv_fragments.Home_Fragment;
import com.example.a3.nv_fragments.Map_Fragment;
import com.example.a3.nv_fragments.Memoir_Fragment;
import com.example.a3.nv_fragments.MovieSearch_Fragment;
import com.example.a3.nv_fragments.Reports_Fragment;
import com.example.a3.nv_fragments.WatchList_Fragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        View header = navigationView.getHeaderView(0);
        TextView nav_header_title = header.findViewById(R.id.nav_header_title);
        TextView nav_header_subtitle = header.findViewById(R.id.nav_header_subtitle);
        SharedPreferences sp = getSharedPreferences(String.valueOf(Login.login_id), Context.MODE_PRIVATE);
        String fname = sp.getString("fisrtname", "");
        String email = sp.getString("email", "");
        nav_header_title.setText(fname);
        nav_header_subtitle.setText(email);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle("My Movie Memoir");

        replaceFragment(new Home_Fragment());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.Menu_fragment:
                replaceFragment(new Home_Fragment());
                break;
            case R.id.Movie_Search:
                replaceFragment(new MovieSearch_Fragment());
                break;
            case R.id.Watchlist:
                replaceFragment(new WatchList_Fragment());
                break;
            case R.id.Movie_Memoir:
                replaceFragment(new Memoir_Fragment());
                break;
            case R.id.Reports:
                replaceFragment(new Reports_Fragment());
                break;
            case R.id.Maps:
                replaceFragment(new Map_Fragment());
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void replaceFragment(Fragment nextFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, nextFragment);
        fragmentTransaction.commit();
    }
}
