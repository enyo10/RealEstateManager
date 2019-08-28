package com.openclassrooms.realestatemanager.models;

import android.text.format.DateUtils;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.sql.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "userId",onDelete = CASCADE,
        onUpdate = CASCADE ),indices=@Index(value="userId"))
public class RealEstate {

    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String userId;

    private int mPrice;
    private double mArea;
    private int mNumberOfPiece;
    private String mCompleteDescription;
    private String mPhotoUrl;
    private Address mAddress;
    private Status mStatus;

    @TypeConverters( DateUtils.class)
    private Date mDateOfEntry;
    @TypeConverters(DateUtils.class)
    private Date mDateOfSale;



    public RealEstate(){

    }

    public RealEstate( String userId, int price, double area, int numberOfPiece, String completeDescription, String photoUrl, Address address, Status status, Date dateOfEntry, Date dateOfSale) {

        this.userId = userId;
        mPrice = price;
        mArea = area;
        mNumberOfPiece = numberOfPiece;
        mCompleteDescription = completeDescription;
        mPhotoUrl = photoUrl;
        mAddress = address;
        mStatus = status;
        mDateOfEntry = dateOfEntry;
        mDateOfSale = dateOfSale;
    }

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }



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


    public Date getDateOfEntry() {
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
