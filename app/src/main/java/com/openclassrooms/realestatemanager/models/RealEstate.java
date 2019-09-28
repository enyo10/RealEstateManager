package com.openclassrooms.realestatemanager.models;


import android.content.ContentValues;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
       /* ,
        @ForeignKey(entity = Address.class,
        parentColumns = "id", childColumns = "mAddressId")},indices={@Index(value="userId"),@Index(value="mAddressId")})*/
public class RealEstate {

    @PrimaryKey(autoGenerate = true)
    private Long id;
    private Long userId;
    private int numberOfBedRooms;
    private int numberOfBathrooms;
    private double price;
    private double area;
    private int numberOfPieces;
    private String completeDescription;
    @Embedded
     private Address address;
    @Type
    private String type;
    @Status
    private String status;
   // @NearbyPOI
    private ArrayList<String>nearbyPointOfInterest=new ArrayList<>();
    private Date dateOfEntry;
    private Date dateOfSale;
    private ArrayList<RealEstateImage>realEstateImages=new ArrayList<>();



    public RealEstate(){

    }

    public RealEstate(Long userId, @Type String type,double price, double area, int numberOfPieces,int numberOfBathrooms,int numberOfBedRooms, String completeDescription,List<RealEstateImage>imageList,Address address) {

        this.userId = userId;
        this.address=address;
        this.type=type;
        this.price = price;
        this.area = area;
        this.numberOfPieces = numberOfPieces;
        this.numberOfBathrooms=numberOfBathrooms;
        this.numberOfBedRooms=numberOfBedRooms;
        this.completeDescription = completeDescription;
        this.realEstateImages.addAll(imageList);
        this.status = Status.UNSOLD;
        this.dateOfEntry = new Date();
        this.dateOfSale = null;

    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getArea() {
        return this.area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getCompleteDescription() {
        return this.completeDescription;
    }

    public void setCompleteDescription(String completeDescription) {
        this.completeDescription = completeDescription;
    }
    @Type
    public String getType() {
        return this.type;
    }

    public void setType(@Type String type) {
        this.type = type;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getNumberOfPieces() {
        return this.numberOfPieces;
    }

    public void setNumberOfPieces(int numberOfPieces) {
        this.numberOfPieces = numberOfPieces;
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

    public int getNumberOfBathrooms() {
        return numberOfBathrooms;
    }

    public void setNumberOfBathrooms(int numberOfBathrooms) {
        this.numberOfBathrooms = numberOfBathrooms;
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

    public ArrayList<RealEstateImage> getRealEstateImages() {
        return realEstateImages;
    }

    public void setRealEstateImages(ArrayList<RealEstateImage> realEstateImages) {
        this.realEstateImages = realEstateImages;
    }

    public static RealEstate fromContentValues(ContentValues contentValues){
        final RealEstate realEstate = new RealEstate();


        if (contentValues.containsKey("area")) realEstate.setArea(contentValues.getAsDouble("area"));
        if (contentValues.containsKey("description")) realEstate.setCompleteDescription(contentValues.getAsString("description"));
        if (contentValues.containsKey("price")) realEstate.setPrice(contentValues.getAsLong("price"));
        if (contentValues.containsKey("surface")) realEstate.setArea(contentValues.getAsDouble("surface"));
        if (contentValues.containsKey("room")) realEstate.setNumberOfPieces(contentValues.getAsInteger("room"));
        if (contentValues.containsKey("bathroom")) realEstate.setNumberOfBathrooms(contentValues.getAsInteger("bathroom"));
        if (contentValues.containsKey("bedroom")) realEstate.setNumberOfBedRooms(contentValues.getAsInteger("bedroom"));
      //  if (contentValues.containsKey("pictureUrl")) realEstate.setPhotosUrls((ArrayList<String>)contentValues.get("pictureUrl"));
        if (contentValues.containsKey("detail_address_line1_num")) realEstate.setAddress((Address) contentValues.get("detail_address_line1_num")); //Risk
        if (contentValues.containsKey("status")) realEstate.setStatus(contentValues.getAsString("status"));
        if (contentValues.containsKey("entryDate")) realEstate.setDateOfEntry((Date) contentValues.get("entryDate"));//Risk
        if (contentValues.containsKey("soldDate")) realEstate.setDateOfSale((Date) contentValues.get("soldDate"));//Risk
        if (contentValues.containsKey("userId")) realEstate.setUserId(contentValues.getAsLong("userId"));
        if(contentValues.containsKey("realEstateImages"))realEstate.setRealEstateImages((ArrayList<RealEstateImage>)contentValues.get("realEstateImages"));
        return realEstate;

    }



}
