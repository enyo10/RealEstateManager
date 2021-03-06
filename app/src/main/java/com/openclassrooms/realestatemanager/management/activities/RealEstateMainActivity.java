package com.openclassrooms.realestatemanager.management.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.navigation.NavigationView;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.injection.Injection;
import com.openclassrooms.realestatemanager.injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.management.realestatedetails.EstateDetailsFragment;
import com.openclassrooms.realestatemanager.management.realestatelist.EstateListFragment;
import com.openclassrooms.realestatemanager.management.search.RealEstateSearchFragment;
import com.openclassrooms.realestatemanager.management.taxation.TaxationDialogFragment;
import com.openclassrooms.realestatemanager.models.User;
import com.openclassrooms.realestatemanager.viewmodel.RealEstateViewModel;

import java.util.ArrayList;
import java.util.List;

public class RealEstateMainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = RealEstateMainActivity.class.getName();

    public final int USER_ID = 1;
    public RealEstateViewModel mRealEstateViewModel;
    public View mRootView;

    private static final String SEARCH_DIALOG_TAG="SEARCH_DIALOG";
    private static final String TAXATION_DIALOG_TAG ="TAXATION_DIALOG";

    public MaterialToolbar mToolbar;
    public DrawerLayout mDrawerLayout;
    public NavController mNavController;
    public NavigationView mNavigationView;

    public boolean priceInDollar=true;

    // BottomNavigationView mBottomNavigationView;
    EstateDetailsFragment mEstateDetailsFragment;
    RealEstateSearchFragment mRealEstateSearchFragment;
    TaxationDialogFragment mTaxationDialogFragment;
    public List<String>nearBy=new ArrayList<>();

    public double selectedRealEstatePrice;
    public boolean actionEdit;
    private boolean landscape;

    public boolean isActionEdit() {
        return actionEdit;
    }

    public void setActionEdit(boolean actionEdit) {
        this.actionEdit = actionEdit;
    }

    public boolean isLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

    }

    public void setLandscape(boolean landscape) {
        this.landscape = landscape;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_estate_main);
        Log.d(TAG, " in on Create RealEstateMainActivity");

        initViewModel();
        getCurrentUser(USER_ID);
        setUpNavigation();

        mNavController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {

                FrameLayout frameLayout=findViewById(R.id.frame_estate_details_Fragment);

                if(frameLayout!=null)

                if(destination.getId() != R.id.estateListFragment) {

                      frameLayout.setVisibility(View.GONE);

                } else {
                    frameLayout.setVisibility(View.VISIBLE);
                    mToolbar.setVisibility(View.VISIBLE);

                }
            }
        });
         mRootView=findViewById(android.R.id.content);

    }


    public void initViewModel(){
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.mRealEstateViewModel = ViewModelProviders.of(this, viewModelFactory).get(RealEstateViewModel.class);
        this.mRealEstateViewModel.init(USER_ID);

    }
    private void setUpNavigation(){
        setUpToolBar();

         mNavController = Navigation.findNavController(this, R.id.my_nav_host_fragment);
         mDrawerLayout  = findViewById(R.id.activity_real_estate_main_drawer_layout);
         mNavigationView = findViewById(R.id.nav_view);

        NavigationUI.setupWithNavController(mToolbar,mNavController,mDrawerLayout);

        mNavigationView.setNavigationItemSelectedListener(this);


    }

    private void setUpToolBar(){
        mToolbar = findViewById(R.id.my_toolBar);
        setSupportActionBar(mToolbar);

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
            getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(menu.findItem(R.id.price_conversion)!=null)
            menu.findItem(R.id.price_conversion).setTitle((this.isPriceInDollar() ? getString(R.string.convert_to_euro) : getString(R.string.convert_to_dollar)));
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_search)
            createSearchDialogFragment();
        if(item.getItemId()==R.id.action_taxation)
            createTaxationDialogFragment();
        if(item.getItemId()==R.id.price_conversion)
            doConversion();

        NavController navController = Navigation.findNavController(this, R.id.my_nav_host_fragment);

        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }


    private void doConversion(){

        if(isPriceInDollar())
            setPriceInDollar(false);
        else setPriceInDollar(true);

        NavHostFragment navHostFragment =(NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment);
       EstateListFragment estateListFragment=(EstateListFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
       if(estateListFragment!=null){
           Log.d(TAG, "fragment found");
           estateListFragment.mRealEstateRecyclerViewAdapter.notifyDataSetChanged();

       }


    }



    private void configureAndShowDetailFragment(){
        Log.d(TAG, " Configure and show Fragment ");
        mEstateDetailsFragment = (EstateDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.frame_estate_details_Fragment);

        Log.d(TAG, " details fragment " +mEstateDetailsFragment);

        //A - We only add DetailFragment in Tablet mode (If found frame_layout_detail)
        if (mEstateDetailsFragment == null) {
            Log.i(TAG, " nous sommes bien sur une tablette ");
            mEstateDetailsFragment = new EstateDetailsFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_estate_details_Fragment, mEstateDetailsFragment)
                    .commit();
            Log.i(TAG, "Fragment created");

        }
    }


    public void showDetailsFragment(){
        Log.d(TAG, "show details method call");
        Log.d(TAG, " Device type "+isTablet());
        if(isTablet() && isLandscape()){
            Log.d(TAG ," Orientation" +isLandscape());
            configureAndShowDetailFragment();}
        else
            Navigation.findNavController(this,R.id.my_nav_host_fragment).navigate(R.id.action_estateListFragment_to_estateDetailsFragment);


    }

    public void navigateToDetailsFragment(){
        Navigation.findNavController(this,R.id.my_nav_host_fragment).navigate(R.id.action_estateListFragment_to_estateDetailsFragment);

    }

    private boolean isTablet(){
        return getApplicationContext().getResources().getBoolean(R.bool.isTablet);
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

        if(view.getId()==R.id.taxation_stop_calc_button){
            Log.d(TAG, "Cancel button");
            mTaxationDialogFragment.dismiss();
        }
        if(view.getId()==R.id.taxation_calc_button){
            if(selectedRealEstatePrice!=0)
            mTaxationDialogFragment.calculateRealEstateRate(selectedRealEstatePrice);
        }

    }



    private void createSearchDialogFragment(){
        mRealEstateSearchFragment= new RealEstateSearchFragment();
       // searchDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE,R.style.Dialog_FullScreen);
        FragmentManager manager=getSupportFragmentManager();
        mRealEstateSearchFragment.show(manager,SEARCH_DIALOG_TAG);

        Log.i(TAG, " Dialog created");
    }


    private void createTaxationDialogFragment(){
        mTaxationDialogFragment =new TaxationDialogFragment();
        FragmentManager manager =getSupportFragmentManager();
        mTaxationDialogFragment.show(manager,TAXATION_DIALOG_TAG);

        Log.i(TAG," Taxation dialog created");

    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.my_nav_host_fragment), mDrawerLayout);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);

        mDrawerLayout.closeDrawers();

        int id = item.getItemId();

        switch (id) {
            case R.id.realEstateAddFragment:
                mNavController.navigate(R.id.realEstateAddFragment);
                break;

            case R.id.map_navigation_button:
                mNavController.navigate(R.id.mapViewFragment);
                break;


        }
        return true;
    }


    public boolean isPriceInDollar() {
        return priceInDollar;
    }

    public void setPriceInDollar(boolean priceInDollar) {
        this.priceInDollar = priceInDollar;
    }
}
