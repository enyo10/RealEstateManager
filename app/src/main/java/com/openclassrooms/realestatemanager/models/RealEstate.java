package com.openclassrooms.realestatemanager.models;


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

    private double price;
    private double area;
    private int numberOfPieces;
    private String completeDescription;
   // private long mAddressId;
//    @Embedded(prefix = "a")
//    @NonNull
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
    private ArrayList<String>photosUrls=new ArrayList<>();



    public RealEstate(){

    }

    public RealEstate(Long userId, @Type String type,double price, double area, int numberOfPieces, String completeDescription, List<String> photosUrls,Address address) {

        this.userId = userId;
        this.address=address;
        this.type=type;
        this.price = price;
        this.area = area;
        this.numberOfPieces = numberOfPieces;
        this.completeDescription = completeDescription;
        this.photosUrls.addAll(photosUrls);
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

    public ArrayList<String> getPhotosUrls() {
        return photosUrls;
    }

    public void addPhotoUrl(String url){
        this.photosUrls.add(url);
    }

    public void setPhotosUrls(ArrayList<String> photosUrls) {
        this.photosUrls = photosUrls;
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


   // @NonNull


   // public void setAddress(@NonNull Address address) {
   //     address = address;
//    }


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









}
