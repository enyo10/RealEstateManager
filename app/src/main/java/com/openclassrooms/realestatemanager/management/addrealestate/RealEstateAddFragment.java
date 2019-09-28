package com.openclassrooms.realestatemanager.management.addrealestate;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentRealEstateAddBinding;
import com.openclassrooms.realestatemanager.management.activities.RealEstateMainActivity;
import com.openclassrooms.realestatemanager.management.views.RealEstateViewModel;
import com.openclassrooms.realestatemanager.models.RealEstate;
import com.openclassrooms.realestatemanager.models.RealEstateImage;
import com.openclassrooms.realestatemanager.utils.DataConverter;
import com.openclassrooms.realestatemanager.utils.ViewUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_CANCELED;


/**
 * A simple {@link Fragment} subclass.
 */
public class RealEstateAddFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    private static final String TAG= RealEstateAddFragment.class.getName();

    private static final String IMAGE_DIRECTORY = "/estate";
    private static final int RC_IMAGE_PERM = 200;
    private static final int RC_CAMERA_PERM=300;



    private static final int GALLERY = 1;
    private static final int  CAMERA = 2;

    private String mType;
    private ArrayList<RealEstateImage> mEstateImages=new ArrayList<>();
    private View rootView;
    private RealEstateViewModel mRealEstateViewModel;
    private FragmentRealEstateAddBinding binding;
    private AddImageRecyclerViewAdapter mAddImageRecyclerViewAdapter;
    private MaterialButton mChoosePictureButton;
    private MaterialButton mSaveRealEstateButton;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_real_estate_add, container, false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        binding.recyclerView.setLayoutManager(linearLayoutManager);

        String[] real_estate_types = getResources().getStringArray(R.array.real_estate_type);

        binding.setSpinAdapter(new ArrayAdapter<String>( getContext(),
               android.R.layout.simple_spinner_dropdown_item, real_estate_types));



        initAndSetRecyclerViewAdapter();


        binding.setLifecycleOwner(this);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      //  BottomNavigationView bottomNavigationView=view.findViewById(R.id.bottom_nav_view);
      //  bottomNavigationView.setOnNavigationItemSelectedListener(this);
       // mType = Type.PENTHOUSE;
         mSaveRealEstateButton=view.findViewById(R.id.real_estate_add_save_button);
         mSaveRealEstateButton.setOnClickListener(this);

         mChoosePictureButton =view.findViewById(R.id.real_estate_add_picture_button);
         mChoosePictureButton.setOnClickListener(this);

        setHasOptionsMenu(true);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getActivity()!=null){
            rootView =((RealEstateMainActivity) getActivity()).mRootView;
            mRealEstateViewModel=((RealEstateMainActivity) getActivity()).mRealEstateViewModel;

            binding.setDataConverter(new DataConverter());
            binding.setRealEstateViewModel(mRealEstateViewModel);
        }
        binding.setDataConverter(new DataConverter());
        binding.setRealEstateViewModel(mRealEstateViewModel);

        String type =mRealEstateViewModel.type.getValue();

        Log.i(TAG, " type -> "+type);


       // mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        //binding.setVariable(myViewModel, mViewModel);
        mRealEstateViewModel.getRealEstate().observe(this, new Observer<RealEstate>() {
            @Override
            public void onChanged(RealEstate realEstate) {

            }
        });
    }

    public RealEstateAddFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }



/*
    @OnClick({R.id.school, R.id.hospital, R.id.shopping_center, R.id.bus_station, R.id.park, R.id.sport_center})
    private void onCheckboxClicked(View view) {

        // ArrayList<String> list=new ArrayList();
        // Is the view now checked?
        boolean checked = ((MaterialCheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {

            case R.id.hospital:
                if (checked) {
                    list.add(NearbyPOI.HOSPITAL);
                    Log.d(TAG, " checked box -- " + R.id.hospital);
                } else {
                    list.remove(NearbyPOI.HOSPITAL);
                    Log.d(TAG, " removed -- " + NearbyPOI.HOSPITAL);
                }
                break;

            case R.id.school:
                if (checked) {
                    // mRealEstate.addNearbyPointOfInterest(NearbyPOI.SCHOOL);
                    Log.d(TAG, " checked box -- " + NearbyPOI.SCHOOL);
                    list.add(NearbyPOI.SCHOOL);
                } else {
                    Log.d(TAG, " removed -- " + NearbyPOI.SCHOOL);
                    list.remove(NearbyPOI.SCHOOL);
                }
                break;

            case R.id.park:
                if (checked) {

                    Log.d(TAG, " checked box -- " + NearbyPOI.PARK);
                    list.add(NearbyPOI.PARK);
                } else {
                    Log.d(TAG, " removed -- " + NearbyPOI.PARK);
                    list.remove(NearbyPOI.PARK);
                }


                break;
            case R.id.bus_station:
                if (checked) {
                    //   mRealEstate.addNearbyPointOfInterest(NearbyPOI.BUS_STATION);
                    Log.d(TAG, " checked box -- " + NearbyPOI.BUS_STATION);
                    list.add(NearbyPOI.BUS_STATION);
                } else {
                    Log.d(TAG, " removed -- " + NearbyPOI.BUS_STATION);
                    list.remove(NearbyPOI.BUS_STATION);
                }
                break;
            case R.id.shopping_center:
                if (checked) {
                    Log.d(TAG, " checked box -- " + NearbyPOI.SHOPPING_CENTER);
                    // mRealEstate.addNearbyPointOfInterest(NearbyPOI.SHOPPING_CENTER);
                    list.add(NearbyPOI.SHOPPING_CENTER);
                } else {
                    Log.d(TAG, " removed -- " + NearbyPOI.SHOPPING_CENTER);
                    list.remove(NearbyPOI.SHOPPING_CENTER);
                }
                break;

            case R.id.sport_center:
                if (checked) {
                    //  mRealEstate.addNearbyPointOfInterest(NearbyPOI.SPORT_CENTER);
                    Log.d(TAG, " checked box -- " + NearbyPOI.SPORT_CENTER);
                    list.add(NearbyPOI.SPORT_CENTER);
                } else {
                    Log.d(TAG, " removed -- " + NearbyPOI.SPORT_CENTER);
                    list.add(NearbyPOI.SPORT_CENTER);
                }

                break;


        }
        Log.i(TAG, " " + list.toString());
    }*/

   // @OnClick(R.id.estate_save_button)
    public void getTextValues() {
        Log.d(TAG, " on save button clicked");

        final List<TextInputLayout> textInputLayouts = ViewUtils.findViewsWithType(
                rootView, TextInputLayout.class);

        boolean noErrors = true;
        for (TextInputLayout textInputLayout : textInputLayouts) {
            String editTextString = textInputLayout.getEditText().getText().toString();
            if (editTextString.isEmpty()) {
                textInputLayout.setError(getResources().getString(R.string.error_string));
                noErrors = false;

            } else {
                textInputLayout.setError(null);
            }
        }

        if (noErrors) {
            // All fields are valid!
           /* int numberOfPieces = Integer.valueOf(( (TextInputEditText) rootView.findViewById(R.id.real_estate_pieces_number_text_edit)).getText().toString());
            double area = Double.valueOf(( (TextInputEditText) rootView.findViewById(R.id.real_estate_surface_text_edit)).getText().toString());
            String description = ( (TextInputEditText) rootView.findViewById(R.id.real_estate_description_edit_text)).getText().toString();
            String  city= ( (TextInputEditText) rootView.findViewById(R.id.real_estate_city_edit_text)).getText().toString();
            String  country= ( (TextInputEditText) rootView.findViewById(R.id.real_estate_country_text_edit)).getText().toString();
            String  street= ( (TextInputEditText) rootView.findViewById(R.id.real_estate_street_edit_text)).getText().toString();
            int zip = Integer.valueOf(( (TextInputEditText) rootView.findViewById(R.id.real_estate_zip_edit_text)).getText().toString());
            double price = Double.valueOf(( (TextInputEditText) rootView.findViewById(R.id.real_estate_price_edit_text)).getText().toString());
*/
           Integer  numberOfPieces=  mRealEstateViewModel.numberOfPieces.getValue();
           Double area =mRealEstateViewModel.area.getValue();
           String city = mRealEstateViewModel.city.getValue();
           String description=mRealEstateViewModel.description.getValue();
           String country=mRealEstateViewModel.country.getValue();
           String street =mRealEstateViewModel.street.getValue();
           Integer zip =mRealEstateViewModel.zip.getValue();
           Double price =mRealEstateViewModel.price.getValue();

            Log.d(TAG, " numberOfPieces: "+numberOfPieces);
            Log.d(TAG, " area "+area);
            Log.d(TAG, " description: "+description);
            Log.d(TAG, " city: "+city);
            Log.d(TAG, " country: "+country);
            Log.d(TAG, " street: "+street);
            Log.d(TAG, " zip: "+zip);
            Log.d(TAG, " price: "+price);

        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        int id =item.getItemId();
        if(id==R.id.save){
            Log.i(TAG, " item id "+id);
            saveRealEstate();

        }
        if(id==R.id.pick_images){
            Log.i(TAG, " item id "+id);
            showPictureChoiceDialog();
        }
        Log.i(TAG, " Item " +item.getTitle());

        return true;
    }

@OnClick(R.id.real_estate_add_picture_button)
    public void onShowPictureButtonClicked(View v){
        Log.i(TAG, " Picture button clicked");
        showPictureChoiceDialog();

    }



    private void showPictureChoiceDialog(){

        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getContext());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                chooseImageFromGallery();
                                break;
                            case 1:
                                makeNewPictureWithCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    /**
     * This method to take picture from user gallery.
     */
    @AfterPermissionGranted(RC_IMAGE_PERM)
    private void chooseImageFromGallery(){

        if (!EasyPermissions.hasPermissions(getActivity(), WRITE_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(this, getString(R.string.gallery_permission), RC_IMAGE_PERM, WRITE_EXTERNAL_STORAGE);
            return;
        }
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);

    }

    /**
     * This to make a new picture with camera.
     */
    @AfterPermissionGranted(RC_CAMERA_PERM)
    private void makeNewPictureWithCamera(){
        if (!EasyPermissions.hasPermissions(getActivity(),  Manifest.permission.CAMERA)) {
            EasyPermissions.requestPermissions(this, getString(R.string.gallery_permission), RC_CAMERA_PERM,  Manifest.permission.CAMERA);
            return;
        }

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // To Do. Handle when use do not allow.
        handleResponse(requestCode,resultCode,data);
    }



    private void handleResponse(int requestCode,int resultCode,Intent data){

        if (resultCode == RESULT_CANCELED) {
            // Request again permission or return.
            Log.i(TAG, " Result code  "+RESULT_CANCELED);
        }

        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Log.i(TAG, " Image load from Gallery : "+ path);

                    Toast.makeText(getContext(), "Image Saved!", Toast.LENGTH_SHORT).show();

                    //TO DO
                    // retrieve path

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA && null!=data.getExtras()) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
           // imageview.setImageBitmap(thumbnail);

          String path=  saveImage(thumbnail);
          Log.i(TAG, " Image load from Camera with : " +path );


            Toast.makeText(getContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
        }

    }

    private void saveRealEstate(){
       // mRealEstateViewModel.imagesList.setValue(imageUris);

        mRealEstateViewModel.onRealEstateSave();
    }


    private String saveImage(Bitmap myBitmap){

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);

        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }


        try {
                File f = new File(wallpaperDirectory, Calendar.getInstance()
                        .getTimeInMillis() + ".jpg");
                f.createNewFile();
                FileOutputStream fo = new FileOutputStream(f);
                fo.write(bytes.toByteArray());
                MediaScannerConnection.scanFile(getContext(),
                        new String[]{f.getPath()},
                        new String[]{"image/jpeg"}, null);
                fo.close();
                Log.d(TAG, "File Saved::--->" + f.getAbsolutePath());
                Log.d(TAG, "File name " +f.getName());
                Log.d(TAG, " path "+f.getPath());
                Log.d(TAG, " canonical file" +f.getCanonicalFile());

            RealEstateImage m=new RealEstateImage(f.getName(),f.getAbsolutePath(),"description");
            this.updateImageList(m);


                return f.getAbsolutePath();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return "";
        }



    private void initAndSetRecyclerViewAdapter(){
            mAddImageRecyclerViewAdapter =new AddImageRecyclerViewAdapter(this.mEstateImages,getContext());
            binding.recyclerView.setAdapter(mAddImageRecyclerViewAdapter);
    }

    private void updateImageList(RealEstateImage image){
            this.mEstateImages.add(image);
            mAddImageRecyclerViewAdapter.notifyDataSetChanged();

    }





    @Override
    //  - After permission granted or refused
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==mChoosePictureButton.getId())
            showPictureChoiceDialog();
        else
        if(v.getId()==mSaveRealEstateButton.getId())
            saveRealEstate();


    }
}
