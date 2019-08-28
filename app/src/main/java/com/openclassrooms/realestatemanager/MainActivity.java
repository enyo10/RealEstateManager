package com.openclassrooms.realestatemanager;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;

import com.openclassrooms.realestatemanager.authentication.LoginActivity;
import com.openclassrooms.realestatemanager.base.BaseActivity;
import com.openclassrooms.realestatemanager.management.fragments.EstateDetailsFragment;
import com.openclassrooms.realestatemanager.management.fragments.EstateListFragment;

public class MainActivity extends BaseActivity {
    private static final String TAG=MainActivity.class.getName();
    private static final int REQUEST_SIGN_IN = 1;


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
        configureAndShowEstateDetailsFragment();
        configureAndShowEstateListFragment();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }



    private void configureAndShowEstateListFragment(){
        // A - Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        mEstateListFragment = (EstateListFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);

        if (mEstateListFragment == null) {
            // B - Create new main fragment
            mEstateListFragment = new EstateListFragment();
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
    }

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

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
}
