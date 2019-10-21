package com.openclassrooms.realestatemanager.models;


import android.content.ContentValues;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.utils.DataConverter;

import java.util.ArrayList;
import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

/*@Entity(foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "userId",onDelete = CASCADE,
        onUpdate = CASCADE ),indices=@Index(value="userId"))*/

@Entity(foreignKeys = {
        @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "userId",onDelete = CASCADE,
        onUpdate = CASCADE )},indices={@Index(value="userId")})

public class RealEstate {
    private static final String TAG =RealEstate.class.getName();

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long userId;
    private int numberOfBedRooms;
    private int numberOfBathRooms;
    private int numberOfRooms;
    private double price;
    private double surface;
    private String completeDescription;
   // private String location;
    @Embedded
     private Address address;
    private String type;
    private String status=Status.UNSOLD;
   // @NearbyPOI
    private ArrayList<String>nearbyPointOfInterest=new ArrayList<>();
    private Date dateOfEntry;
    private Date dateOfSale;
    private String images;
    private boolean sold;


    public RealEstate(){

    }

    public RealEstate(long userId,  String type,double price, double area, int numberOfPieces,int numberOfBathrooms,int numberOfBedRooms, String completeDescription,String imageList,Address address,ArrayList<String>nearbyPointOfInterest) {

        this.userId = userId;
        this.address=address;
        this.type=type;
        this.price = price;
        this.surface = area;
        this.numberOfRooms = numberOfPieces;
        this.numberOfBathRooms=numberOfBathrooms;
        this.numberOfBedRooms=numberOfBedRooms;
        this.completeDescription = completeDescription;
        this.images=imageList;
        this.status = Status.UNSOLD;
        this.sold =false;
        this.dateOfEntry = new Date();
        this.dateOfSale = null;
        this.images=imageList;
        this.nearbyPointOfInterest=nearbyPointOfInterest;

    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSurface() {
        return this.surface;
    }

    public void setSurface(double surface) {
        this.surface = surface;
    }

    public String getCompleteDescription() {
        return this.completeDescription;
    }

    public void setCompleteDescription(String completeDescription) {
        this.completeDescription = completeDescription;
    }

    public String getType() {
        return this.type;
    }

    public void setType( String type) {
        this.type = type;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }



    @NonNull
    public Address getAddress() {
        return address;
    }

    public void setAddress(@NonNull Address address) {
        this.address = address;
    }


    @Status
    public String getStatus() {
        return this.status;
    }

    public void setStatus(@Status String status) {
        this.status = status;
    }


    public Date getDateOfEntry() {
        return this.dateOfEntry;
    }

    public void setDateOfEntry(Date dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
    }

    public Date getDateOfSale() {
        return this.dateOfSale;
    }

    public void setDateOfSale(Date dateOfSale) {
        this.dateOfSale = dateOfSale;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumberOfBathRooms(int numberOfBathRooms) {
        this.numberOfBathRooms = numberOfBathRooms;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public ArrayList<String> getNearbyPointOfInterest() {
        return nearbyPointOfInterest;
    }

    public void setNearbyPointOfInterest(ArrayList<String> nearbyPointOfInterest) {
        this.nearbyPointOfInterest = nearbyPointOfInterest;
    }

    public void addNearbyPointOfInterest( String nearbyPOI){
        this.nearbyPointOfInterest.add(nearbyPOI);

    }

    public int getNumberOfBedRooms() {
        return numberOfBedRooms;
    }

    public void setNumberOfBedRooms(int numberOfBedRooms) {
        this.numberOfBedRooms = numberOfBedRooms;
    }

    public int getNumberOfBathRooms() {
        return numberOfBathRooms;
    }

    public void setNumberOfBathrooms(int numberOfBathrooms) {
        this.numberOfBathRooms = numberOfBathrooms;
    }

    public String getImages() {

        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

  /*  public String getLocation() {
        return this.address.format();
    }

    public void setLocation(String location) {
        this.location = location;
    }
*/
    public void addNearBy(View view){
        Log.i(TAG, " view : " +view.getId());

    }

    public boolean isInteger(String value){

            try
            {
                Integer.parseInt( value );
                return true;
            }
            catch( Exception e)
            {
                return false;
            }
        }

        public boolean isPriceValide(){


        return false;

        }

        public String getFormattedPrice(){
        return DataConverter.formatPriceToDollarFormat(getPrice());
        }




    public static RealEstate fromContentValues(ContentValues contentValues){
        final RealEstate realEstate = new RealEstate();


        if (contentValues.containsKey("surface")) realEstate.setSurface(contentValues.getAsDouble("surface"));
        if(contentValues.containsKey("type"))realEstate.setType(contentValues.getAsString("type"));
        if (contentValues.containsKey("completeDescription")) realEstate.setCompleteDescription(contentValues.getAsString("completeDescription"));
        if (contentValues.containsKey("price")) realEstate.setPrice(contentValues.getAsDouble("price"));
        if (contentValues.containsKey("numberOfRooms")) realEstate.setNumberOfRooms(contentValues.getAsInteger("room"));
        if (contentValues.containsKey("numberOfBathRooms")) realEstate.setNumberOfBathrooms(contentValues.getAsInteger("bathroom"));
        if (contentValues.containsKey("numberOfBedRooms")) realEstate.setNumberOfBedRooms(contentValues.getAsInteger("bedroom"));
        if (contentValues.containsKey("status")) realEstate.setStatus(contentValues.getAsString("status"));
        if (contentValues.containsKey("entryDate")) realEstate.setDateOfEntry((Date) contentValues.get("entryDate"));
        if (contentValues.containsKey("soldDate")) realEstate.setDateOfSale((Date) contentValues.get("soldDate"));
        if (contentValues.containsKey("userId")) realEstate.setUserId(contentValues.getAsInteger("userId"));
        if(contentValues.containsKey("images"))realEstate.setImages(contentValues.getAsString("images"));
        if(contentValues.containsKey("address"))realEstate.setAddress((Address)contentValues.get("address"));
        if(contentValues.containsKey("sold"))realEstate.setSold(contentValues.getAsBoolean("sold"));

        if(contentValues.containsKey("nearbyPointOfInterest"))realEstate.setNearbyPointOfInterest((ArrayList<String>) contentValues.get("nearbyPointOfInterest"));
        return realEstate;

    }



    @BindingAdapter("address")
    public static void loadMapFromUri(ImageView view, Address location){
        Log.i(TAG," the load image from uri method is call");
       String uri="";
      // String uri= Utils.apiUri+location.format()+Utils.apiKey+ BuildConfig.map_api_key;
        Log.d(TAG, uri);
        Glide.with(view.getContext())
                .load(uri)
                .apply(new RequestOptions())
                .into(view);
    }

}
