package com.openclassrooms.realestatemanager.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(foreignKeys = @ForeignKey(entity = Administrator.class,
        parentColumns = "id",
        childColumns = "adminId"))

public class RealEstate {

    @PrimaryKey(autoGenerate = true)
    private Long id;
    private Long adminId;

    private int mPrice;
    private double mArea;
    private int mNumberOfPiece;
    private String mCompleteDescription;
    private String mPhotoUrl;
  //  private Address mAddress;
  //  private Status mStatus;
 //   private Date mDateOfEntry;
  //  private Date mDateOfSale;



    public RealEstate(){

    }

   /* public RealEstate(int price, double area, int numberOfPiece, String completeDescription, String photoUrl, Address address, Status status, Date dateOfEntry, Date dateOfSale, Administrator administrator) {
        mPrice = price;
        mArea = area;
        mNumberOfPiece = numberOfPiece;
        mCompleteDescription = completeDescription;
        mPhotoUrl = photoUrl;
        mAddress = address;
        mStatus = status;
        mDateOfEntry = dateOfEntry;
        mDateOfSale = dateOfSale;

    }*/

    public int getPrice() {
        return mPrice;
    }

    public void setPrice(int price) {
        mPrice = price;
    }

    public double getArea() {
        return mArea;
    }

    public void setArea(double area) {
        mArea = area;
    }

    public int getNumberOfPiece() {
        return mNumberOfPiece;
    }

    public void setNumberOfPiece(int numberOfPiece) {
        mNumberOfPiece = numberOfPiece;
    }

    public String getCompleteDescription() {
        return mCompleteDescription;
    }

    public void setCompleteDescription(String completeDescription) {
        mCompleteDescription = completeDescription;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        mPhotoUrl = photoUrl;
    }
/*

    public Address getAddress() {
        return mAddress;
    }

    public void setAddress(Address address) {
        mAddress = address;
    }

    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        mStatus = status;
    }
*/

   /* public Date getDateOfEntry() {
        return mDateOfEntry;
    }

    public void setDateOfEntry(Date dateOfEntry) {
        mDateOfEntry = dateOfEntry;
    }

    public Date getDateOfSale() {
        return mDateOfSale;
    }

    public void setDateOfSale(Date dateOfSale) {
        mDateOfSale = dateOfSale;
    }
*/


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }
}
