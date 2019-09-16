package com.openclassrooms.realestatemanager.management.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.openclassrooms.realestatemanager.R;

public class RealEstateMainActivity extends AppCompatActivity {

  //  @BindView(R.id.toolBar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_estate_main);

        NavController navController = Navigation.findNavController(this, R.id.my_nav_host_fragment);
        DrawerLayout drawerLayout =findViewById(R.id.drawer_layout);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).setDrawerLayout(drawerLayout).build();


        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
      //  NavigationUI.setupWithNavController(toolbar, navController);

        NavigationUI.setupWithNavController(toolbar,navController,drawerLayout);

       // NavigationView navView = findViewById(R.id.nav_view);
     //   NavigationUI.setupWithNavController(navView, navController);


        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId() != R.id.estateListFragment) {

                  //  toolbar.setVisibility(View.GONE);
                   // bottomNavigationView.setVisibility(View.GONE);


                } else {
                    toolbar.setVisibility(View.VISIBLE);
                   // bottomNavigationView.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
     //  return super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.my_nav_host_fragment);
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }
}
