package com.openclassrooms.realestatemanager.management.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.injection.Injection;
import com.openclassrooms.realestatemanager.injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.management.realestatedetails.EstateDetailsFragment;
import com.openclassrooms.realestatemanager.management.views.RealEstateViewModel;
import com.openclassrooms.realestatemanager.models.User;

public class RealEstateMainActivity extends AppCompatActivity {
    private static final String TAG = RealEstateMainActivity.class.getName();

    public final int USER_ID = 1;
    public RealEstateViewModel mRealEstateViewModel;
    public View mRootView;

    //  @BindView(R.id.toolBar)
    MaterialToolbar mToolbar;
    BottomNavigationView mBottomNavigationView;
    EstateDetailsFragment mEstateDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_estate_main);
        Log.d(TAG, " in on Create ");

        initViewModel();
        getCurrentUser(USER_ID);


        NavController navController = Navigation.findNavController(this, R.id.my_nav_host_fragment);
        DrawerLayout drawerLayout =findViewById(R.id.drawer_layout);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).setDrawerLayout(drawerLayout).build();



        MaterialToolbar toolbar = findViewById(R.id.my_toolBar);
        setSupportActionBar(toolbar);
      //  NavigationUI.setupWithNavController(toolbar, navController);

        NavigationUI.setupWithNavController(toolbar,navController,drawerLayout);
       // NavigationUI.setupWithNavController(bottomNavigationView,navController);

       // NavigationView navView = findViewById(R.id.nav_view);
     //   NavigationUI.setupWithNavController(navView, navController);


        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {

                FrameLayout frameLayout=findViewById(R.id.frame_estate_details_Fragment);

                if(frameLayout!=null)

                if(destination.getId() != R.id.estateListFragment) {

                      frameLayout.setVisibility(View.GONE);
                  //  toolbar.setVisibility(View.GONE);
                   // bottomNavigationView.setVisibility(View.GONE);


                } else {
                    frameLayout.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.VISIBLE);
                   // bottomNavigationView.setVisibility(View.VISIBLE);
                }
            }
        });
         mRootView=findViewById(android.R.id.content);
        configureAndShowDetailFragment();
    }


    public void initViewModel(){
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.mRealEstateViewModel = ViewModelProviders.of(this, viewModelFactory).get(RealEstateViewModel.class);
        this.mRealEstateViewModel.init(USER_ID);


    }

    // 3 - Get Current User
    private void getCurrentUser(int userId){
        this.mRealEstateViewModel.getUser(userId).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Log.d(TAG, "user "+user);
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


    private void configureAndShowDetailFragment(){
        Log.d(TAG, " Configure and show Fragment ");
        mEstateDetailsFragment = (EstateDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.frame_estate_details_Fragment);

        Log.d(TAG, " details fragment " +mEstateDetailsFragment);

        //A - We only add DetailFragment in Tablet mode (If found frame_layout_detail)
        if (mEstateDetailsFragment == null && findViewById(R.id.frame_estate_details_Fragment) != null) {
            Log.i(TAG, " nous sommes bien sur une tablette ");
            mEstateDetailsFragment = new EstateDetailsFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_estate_details_Fragment, mEstateDetailsFragment)
                    .commit();
            Log.i(TAG, "Fragment created");

        }
    }






}
