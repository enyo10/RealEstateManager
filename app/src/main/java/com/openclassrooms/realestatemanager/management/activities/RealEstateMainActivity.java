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
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.injection.Injection;
import com.openclassrooms.realestatemanager.injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.management.realestatedetails.EstateDetailsFragment;
import com.openclassrooms.realestatemanager.management.search.RealEstateSearchFragment;
import com.openclassrooms.realestatemanager.models.User;
import com.openclassrooms.realestatemanager.viewmodel.RealEstateViewModel;

import java.util.ArrayList;
import java.util.List;

public class RealEstateMainActivity extends AppCompatActivity {
    private static final String TAG = RealEstateMainActivity.class.getName();

    public final int USER_ID = 1;
    public RealEstateViewModel mRealEstateViewModel;
    public View mRootView;

    private static final String SEARCH_DIALOG_TAG="SEARCH DIALOG";

    MaterialToolbar mToolbar;
    BottomNavigationView mBottomNavigationView;
    EstateDetailsFragment mEstateDetailsFragment;
    RealEstateSearchFragment mRealEstateSearchFragment;
    public List<String>nearBy=new ArrayList<>();

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
        if(item.getItemId()==R.id.action_search)
            createSearchDialogFragment();

        NavController navController = Navigation.findNavController(this, R.id.my_nav_host_fragment);

        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }


    private void configureAndShowDetailFragment(){
        /*Log.d(TAG, " Configure and show Fragment ");
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

        }*/
    }


    public void showDetailsFragment(){
        Navigation.findNavController(this,R.id.my_nav_host_fragment).navigate(R.id.action_estateListFragment_to_estateDetailsFragment);

    }


    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((MaterialCheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.park_check_box:
                if (checked){

                    mRealEstateViewModel.nearbyValues.add(getResources().getString(R.string.park));
                }

                else{
                    mRealEstateViewModel.nearbyValues.remove(getResources().getString(R.string.park));
                }
                break;

            case R.id.bus_station_check_box:
                if (checked){
                    mRealEstateViewModel.nearbyValues.add(getResources().getString(R.string.bus_station));}
                else{
                    mRealEstateViewModel.nearbyValues.remove(getResources().getString(R.string.bus_station));}
                break;

            case R.id.sport_center_check_box:
                if (checked){
                    mRealEstateViewModel.nearbyValues.add(getResources().getString(R.string.sport_center));}
                else{
                    mRealEstateViewModel.nearbyValues.remove(getResources().getString(R.string.sport_center));}
                break;

            case R.id.shopping_center_check_box:
                if (checked){
                    mRealEstateViewModel.nearbyValues.add(getResources().getString(R.string.shopping_center));}
                else{
                    mRealEstateViewModel.nearbyValues.remove(getResources().getString(R.string.shopping_center));}
                break;
            case R.id.hospital_check_box:
                if (checked){
                    mRealEstateViewModel.nearbyValues.add(getResources().getString(R.string.hospital));}
                else{
                    mRealEstateViewModel.nearbyValues.remove(getResources().getString(R.string.hospital));
                }
                break;

            case R.id.school_check_box:
                if (checked){
                    mRealEstateViewModel.nearbyValues.add(getResources().getString(R.string.school));
                }
                else{
                    mRealEstateViewModel.nearbyValues.remove(getResources().getString(R.string.school));
                }
                break;


        }
        Log.d(TAG, " list value "+ mRealEstateViewModel.nearbyValues.toString());
    }



    public boolean ischecked(View view){
        MaterialCheckBox box=(MaterialCheckBox) view;
        List<String> list=new ArrayList<>();
        if(nearBy.contains("Park"))
            if(view.getId()==R.id.park_check_box)
                box.setChecked(true);
            return true;


    }


    public void dialogButtonClicked(View view){

        if(view.getId()==R.id.search_button_cancel){
            Log.d(TAG, "Cancel Button clicked");
            mRealEstateSearchFragment.dismiss();

        }
        if(view.getId()==R.id.action_search){
            mRealEstateSearchFragment.startQuery();
            Navigation.findNavController(this,R.id.my_nav_host_fragment).navigate(R.id.action_estateListFragment_to_searchResultFragment);

            Log.d(TAG, "Search Button clicked");


        }

    }

    private void createSearchDialogFragment(){
        mRealEstateSearchFragment= new RealEstateSearchFragment();
       // searchDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE,R.style.Dialog_FullScreen);
        FragmentManager manager=getSupportFragmentManager();
        mRealEstateSearchFragment.show(manager,SEARCH_DIALOG_TAG);


        Log.i(TAG, " Dialog created");
    }


   /* @BindingAdapter("app:latLong")
    public static void bindLocationToMap(MapView mapView, LatLng latLong) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLong, 10);
       // mapView.animateCamera(cameraUpdate);
        mapView.animate();
    }
*/



}
