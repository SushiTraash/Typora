package com.example.greenpoint;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity_bar extends AppCompatActivity {

    FragmentManager fm;
    FragmentTransaction beginTransaction;
    Store_Detail f1;
    store f2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_bar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0x15658734);//将状态栏设置成透明色
            //   getWindow().setNavigationBarColor(0xeeeeee00);//将导航栏设置为透明色
        }

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications,R.id.Store,R.id.power)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener()
        {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
              try {
                  FragmentTransaction tran = getSupportFragmentManager().beginTransaction();
                  tran.hide(f1);


                  tran.commit();
              }catch (Exception e){

              }



            }

        });

    }

    public void ppppp(View v){


    }



    public void rrrrr(View v){

        FragmentTransaction tran = getSupportFragmentManager().beginTransaction();
        tran.hide(f1);


        tran.commit();

    }

    public void aaaaab(View v){

        init();

    }

    private void init() {

        fm = getSupportFragmentManager();

        //开启事务
          beginTransaction = fm.beginTransaction();
        f1  = new Store_Detail();
         f2 = new store();



//        beginTransaction.replace(R.id.frag,f1);

        beginTransaction.add(R.id.nav_host_fragment,f1);
        beginTransaction.add(R.id.nav_host_fragment,f2);


        beginTransaction.show(f1);
        beginTransaction.hide(f2);
        beginTransaction.commit();

    }




}