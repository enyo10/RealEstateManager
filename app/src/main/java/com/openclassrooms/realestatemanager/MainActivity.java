package com.openclassrooms.realestatemanager;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.openclassrooms.realestatemanager.base.BaseActivity;
import com.openclassrooms.realestatemanager.management.activities.RealEstateMainActivity;
import com.openclassrooms.realestatemanager.management.realestatedetails.EstateDetailsFragment;
import com.openclassrooms.realestatemanager.management.realestatelist.EstateListFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG=MainActivity.class.getName();
    private static final int REQUEST_SIGN_IN = 1;

     /* private TextView textViewMain;
    private TextView textViewQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // this.textViewMain = findViewById(R.id.activity_second_activity_text_view_main);
        this.textViewMain=findViewById(R.id.activity_main_activity_text_view_main);
        this.textViewQuantity = findViewById(R.id.activity_main_activity_text_view_quantity);

        this.configureTextViewMain();
        this.configureTextViewQuantity();
    }

    private void configureTextViewMain(){
        this.textViewMain.setTextSize(15);
        this.textViewMain.setText("Le premier bien immobilier enregistré vaut ");
    }

    private void configureTextViewQuantity(){
        int quantity = Utils.convertDollarToEuro(100);
        this.textViewQuantity.setTextSize(20);
      //  this.textViewQuantity.setText(quantity);
        this.textViewQuantity.setText(String.format(Locale.FRANCE,"%d",quantity));
    }*/



    // The Views.
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
   // @BindView(R.id.mainTextInputLayout)
    TextInputLayout mTextInputLayout;

    TextView mUserEmailTextView;
    ImageView mImageView;
    TextView mUsernameTextView;



    private EstateListFragment mEstateListFragment;
    private EstateDetailsFragment mEstateDetailsFragment;


    @Override
    public int getActivityLayout() {

        return R.layout.activity_main;
    }

    @Override
    public void configureViewModel() {

    }

    @Override
    public void configureView() {
        initViews();
      //  configureAndShowEstateListFragment();
       // configureAndShowEstateDetailsFragment();



       // Intent intent = new Intent(this, LoginActivity.class);
        Intent intent = new Intent(this, RealEstateMainActivity.class);
        startActivity(intent);

    }



   /* private void configureAndShowEstateListFragment(){
        Log.d(TAG, " in configure and show list fragment");
        // A - Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        mEstateListFragment = (EstateListFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);

        if (mEstateListFragment == null) {
            // B - Create new main fragment
            mEstateListFragment = new EstateListFragment();
            Log.d(TAG, " Estate fragment created...");
            // C - Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_main,mEstateListFragment)
                    .commit();
        }
    }

    private void configureAndShowEstateDetailsFragment(){
        mEstateDetailsFragment = (EstateDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_details);

        //A - We only add D in Tablet mode (If found frame_layout_detail)
        if (mEstateDetailsFragment == null && findViewById(R.id.frame_layout_details) != null) {
            mEstateDetailsFragment = new EstateDetailsFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_details, mEstateDetailsFragment)
                    .commit();
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      /*  if (id == R.id.action_settings) {
            Log.d(TAG," Settings clicked");

        } else if (id == R.id.action_logout) {
            Log.d(TAG, "On logout clicked");
            return true;

        } else if (id == R.id.action_search) {
            Log.d(TAG, "On search clicked");
            return true;

        }*/
        return super.onOptionsItemSelected(item);
    }



   private void initViews(){

        toolbar.setTitle("Real Estate Manager");
        setSupportActionBar(toolbar);
       configureNavHeader();

       DrawerLayout drawer = findViewById(R.id.drawer_layout);
       ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
               this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
       drawer.addDrawerListener(toggle);
       toggle.syncState();

       NavigationView navigationView = findViewById(R.id.nav_view);
       navigationView.setNavigationItemSelectedListener(this);
   }

   private void configureNavHeader(){

       View view= mNavigationView.getHeaderView(0);
       mUserEmailTextView =view.findViewById(R.id.nav_header_email_textView);
       mUsernameTextView=  view.findViewById(R.id.nav_header_username_textView);
       mImageView =view.findViewById(R.id.nav_header_imageView);

   }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.setting) {
            // start profile Activity

        } else if (id == R.id.logout) {

        } else if (id == R.id.your_lunch) {
            // Go to your lunch.

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
