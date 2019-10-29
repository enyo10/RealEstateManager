package com.openclassrooms.realestatemanager.management.addrealestate;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentRealEstateAddBinding;
import com.openclassrooms.realestatemanager.injection.Injection;
import com.openclassrooms.realestatemanager.injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.management.activities.RealEstateMainActivity;
import com.openclassrooms.realestatemanager.models.Address;
import com.openclassrooms.realestatemanager.models.RealEstate;
import com.openclassrooms.realestatemanager.models.RealEstateImage;
import com.openclassrooms.realestatemanager.utils.DataConverter;
import com.openclassrooms.realestatemanager.utils.MyListener;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.utils.ViewUtils;
import com.openclassrooms.realestatemanager.viewmodel.RealEstateViewModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class RealEstateAddFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener, MyListener {
    private static final String TAG = RealEstateAddFragment.class.getName();

    protected static final String IMAGE_DIRECTORY = "/estate";
    protected static final int RC_IMAGE_PERM = 200;
    protected static final int RC_CAMERA_PERM = 300;

    VideoView videoView;
    Uri videoFileUri;
    protected static int VIDEO_CAPTURE = 3;
    protected static final int GALLERY = 1;
    protected static final int CAMERA = 2;

    protected ArrayList<RealEstateImage> mEstateImages = new ArrayList<>();
    protected View rootView;
    protected RealEstateViewModel mRealEstateViewModel;
    protected FragmentRealEstateAddBinding binding;
    private AddImageRecyclerViewAdapter mAddImageRecyclerViewAdapter;
    protected static  int USER_ID;

    protected Uri fileUri;

    protected Bitmap mBitmap;
    protected String mString = "";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_real_estate_add, container, false);

        // Observe the insertion.
        binding.setLifecycleOwner(this);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (this.getActivity() != null) {
            rootView = ((RealEstateMainActivity) this.getActivity()).mRootView;
            boolean isActionEdit = ((RealEstateMainActivity) this.getActivity()).actionEdit;
            ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(getContext());
           // this.mRealEstateViewModel = ViewModelProviders.of(this, viewModelFactory).get(RealEstateViewModel.class);
            mRealEstateViewModel=((RealEstateMainActivity) this.getActivity()).mRealEstateViewModel;
            USER_ID=((RealEstateMainActivity) this.getActivity()).USER_ID;
            binding.setRealEstateViewModel(mRealEstateViewModel);
            binding.setDataConverter(new DataConverter());
            //Check if the device hat camera.
            mRealEstateViewModel.hasCamera.setValue(hasCamera());

            initAndSetRecyclerViewAdapter();

            if(isActionEdit)
                editRealEstate();
            else
                initNewRealEstateCreation();

            //Set listener for the button click.
            binding.setMyListener(this);

           // initAndSetRecyclerViewAdapter();
        }

        mRealEstateViewModel.getInsertResult().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                if (aLong > 0)
                    Toast.makeText(getContext(), " Insertion to DB success", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getContext(), "Insertion to db fail", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void initNewRealEstateCreation(){
        Log.d(TAG, "init Real estate creation");
        initRealEstateTypeAdapter();
        initRealEstateCreationData();

    }

    private void editRealEstate(){
        Log.d(TAG, " Edit real estate");
        updateRealStateStatusUI();
        mRealEstateViewModel.getSelectedRealEstate().observe(this,this::updateWithSelectedRealEstate);
       // mRealEstateViewModel.getSelectedRealEstate().observe(this,this::updateWithSelectedRealEstate);

    }
    private void initRealEstateCreationData(){
       // binding.setDataConverter(new DataConverter());
        RealEstate realEstate=new RealEstate();
        realEstate.setUserId(USER_ID);
        realEstate.setAddress(new Address());
        mRealEstateViewModel.realEstate.setValue(realEstate);

        binding.realEstateUpdateStatus.setVisibility(View.GONE);

    }

    private void initRealEstateTypeAdapter(){
        String[] real_estate_types = getResources().getStringArray(R.array.real_estate_type);
        binding.setSpinAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, real_estate_types));

    }



    public RealEstateAddFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }


    protected void getTextValuesAndSaveRealEstate() {
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

            for(RealEstateImage estateImage:mEstateImages){
                saveImage(estateImage);

                Log.i(TAG, " image: " + " uri " +estateImage.getUri() + " "+ " name "+estateImage.getImageName()+"  "+estateImage.getBitmap());
            }
            mRealEstateViewModel.setRealEstateImagesString(Utils.objectToJson(mEstateImages));
            mRealEstateViewModel.onRealEstateSave(USER_ID);
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.save) {
            Log.i(TAG, " item id " + id);

        }
        if (id == R.id.pick_images) {
            Log.i(TAG, " item id " + id);
            showPictureChoiceDialog();
        }
        Log.i(TAG, " Item " + item.getTitle());

        return true;
    }


    protected void showPictureChoiceDialog() {

        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getContext());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
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
    protected void chooseImageFromGallery() {
        if(getActivity()!=null)
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
    protected void makeNewPictureWithCamera() {
        if(getActivity()!=null)
        if (!EasyPermissions.hasPermissions(getActivity(), Manifest.permission.CAMERA)) {
            EasyPermissions.requestPermissions(this, getString(R.string.gallery_permission), RC_CAMERA_PERM, Manifest.permission.CAMERA);
            return;
        }

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // To Do. Handle when use do not allow.
        handleResponse(requestCode, resultCode, data);
    }


    protected void handleResponse(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_CANCELED) {
            // Request again permission or return.
            Log.i(TAG, " Result code  " + RESULT_CANCELED);
            return;
        }
        if(requestCode==VIDEO_CAPTURE){
            if (resultCode == RESULT_OK) {
                Toast.makeText(getContext(), "Video has been saved to:\n" +
                        data.getData(), Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getContext(), "Video recording cancelled.",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Failed to record video",
                        Toast.LENGTH_LONG).show();
            }
        }

        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();

                try {
                    this.mBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                    Toast.makeText(getContext(), "Picture choose from gallery", Toast.LENGTH_LONG).show();


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA && null != data.getExtras()) {
            Log.i(TAG, " data type " + data.getExtras().get("data").getClass());
            mBitmap = (Bitmap) data.getExtras().get("data");

            Toast.makeText(getContext(), "Picture made properly ", Toast.LENGTH_SHORT).show();
        }
        if(mBitmap!=null)
        createDialog();

    }


    /**
     * This method to save the Real Estate data in the external directory.
     * @param realEstateImage, an real estate data (Bit map and description.)
     * @return, the path of the Bitmap.
     */
    protected String saveImage(RealEstateImage realEstateImage){
        // Here I have a real estate image with bitmap.
        Bitmap myBitmap = realEstateImage.getBitmap();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);

        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
                /*File f = new File(wallpaperDirectory, Calendar.getInstance()
                        .getTimeInMillis() + ".jpg");*/
                    File f = new File(wallpaperDirectory, Utils.getRandomNumber(1,999999) + ".jpg");
                    f.createNewFile();
                    FileOutputStream fo = new FileOutputStream(f);
                    fo.write(bytes.toByteArray());
                    MediaScannerConnection.scanFile(getContext(),
                            new String[]{f.getPath()},
                            new String[]{"image/jpeg"}, null);
                    fo.close();

                    realEstateImage.setUri(f.getAbsolutePath());

                    return f.getAbsolutePath();

            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return "";
        }



    protected void initAndSetRecyclerViewAdapter(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        binding.recyclerView.setLayoutManager(linearLayoutManager);
        mAddImageRecyclerViewAdapter =new AddImageRecyclerViewAdapter(this.mEstateImages,getContext());
        binding.recyclerView.setAdapter(mAddImageRecyclerViewAdapter);
    }


    protected void updateImageList(RealEstateImage image){
            this.mEstateImages.add(image);
           mAddImageRecyclerViewAdapter.notifyDataSetChanged();

    }


    @Override
    //  - After permission granted or refused
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }




    protected void createDialog(){

        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle("Add name");

        final EditText inputText = new EditText(getActivity());
        inputText.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(inputText);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

               mString = inputText.getText().toString();
               Log.i(TAG, " input text -> "+mString);
               RealEstateImage realEstateImage = new RealEstateImage(mString,mBitmap,mString);
               Log.d(TAG, " Real estate image ");
               updateImageList(realEstateImage);

            }
        });
        builder.setNegativeButton(" Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
        builder.show();
    }



    @Override
    public void onRealEstateTypeSelected(String type) {

    }

    @Override
    public void onChoosePictureButtonClicked(View v) {
        Log.d(TAG, " on choosePicture");
        this.showPictureChoiceDialog();

    }

    @Override
    public void onRealEstateSaveButtonClicked(View v) {
        // Check if all text field are filled and save the real estate.
        getTextValuesAndSaveRealEstate();

    }


    protected boolean hasCamera() {

        return (getActivity()!=null && getActivity().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_ANY));

    }
    @Override
    public void onStartRecording(View view)
    {
        File mediaFile = new
                File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/my_video.mp4");

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        fileUri = Uri.fromFile(mediaFile);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, VIDEO_CAPTURE);
        
        Log.i(TAG, "Video recorded");
    }


    //***************************Method for Edit.******************************************************

    /**
     * this method update the state of the check button,
     * @param realEstate, the real estate to be edited.
     */
    private void updateNearbyCheckButton(RealEstate realEstate){
        List<String>nearbyList=realEstate.getNearbyPointOfInterest();

        if(nearbyList.contains(getResources().getString(R.string.park)))
            binding.parkCheckBox.setChecked(true);
        if(nearbyList.contains(getResources().getString(R.string.school)))
            binding.schoolCheckBox.setChecked(true);
        if(nearbyList.contains(getResources().getString(R.string.hospital)))
            binding.hospitalCheckBox.setChecked(true);
        if(nearbyList.contains(getResources().getString(R.string.bus_station)))
            binding.busStationCheckBox.setChecked(true);
        if(nearbyList.contains(getResources().getString(R.string.shopping_center)))
            binding.shoppingCenterCheckBox.setChecked(true);
        if(nearbyList.contains(getResources().getString(R.string.sport_center)))
            binding.sportCenterCheckBox.setChecked(true);

        if(realEstate.isSold())
            binding.soldRadioButton.setChecked(true);
        else binding.soldRadioButton.setChecked(false);

    }


    /**
     * This method to retrieve the list of real estate pictures.
     * @param realEstate, the real estate to update.
     */
    private void loadImageListAndUpdateUI(RealEstate realEstate){

        ArrayList<RealEstateImage>images = Utils.jsonStringToRealEstateImageList(realEstate.getImages());

        for(RealEstateImage realEstateImage:images) {
            if(realEstateImage.getBitmap()!=null)
                Log.d(TAG, " Bitmap "+realEstateImage.getBitmap());
            Log.d(TAG, " image  " + realEstateImage);
            if (mEstateImages != null)
                Log.d(TAG, "images size " + mEstateImages.size());
            if (mAddImageRecyclerViewAdapter != null) {
                Log.d(TAG, "adapter not null");
               // updateImageList(realEstateImage);
                Bitmap bitmap = BitmapFactory.decodeFile(realEstateImage.getUri());
                Log.d(TAG, " converted bitmap" +bitmap);
                RealEstateImage rImage=new RealEstateImage(realEstateImage.getImageName(),bitmap,realEstateImage.getImageName());
                updateImageList(rImage);

            }
        }
    }

    private void updateRealStateStatusUI(){
        binding.realEstateAddSaveButton.setText(getResources().getString(R.string.update));
        binding.realEstateUpdateStatus.setVisibility(View.VISIBLE);
        binding.realEstateUpdateStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(mRealEstateViewModel.realEstate!=null)
                    if(checkedId==R.id.sold_radio_button){
                        mRealEstateViewModel.realEstate.getValue().setSold(true);
                        mRealEstateViewModel.realEstate.getValue().setDateOfSale(new Date());
                    }
                    else if(checkedId==R.id.unsold_radio_button){
                        mRealEstateViewModel.realEstate.getValue().setSold(false);
                        mRealEstateViewModel.realEstate.getValue().setDateOfSale(null);
                    }

            }
        });

    }



    private void updateWithSelectedRealEstate(RealEstate realEstate){
        Log.d(TAG, " in update with selectedRealEstateMethod");
        if(realEstate!=null){
        Log.d(TAG," Real estate not null" );
       mRealEstateViewModel.realEstate.setValue(realEstate);

        updateNearbyCheckButton(realEstate);
        loadImageListAndUpdateUI(realEstate);}


    }


}
