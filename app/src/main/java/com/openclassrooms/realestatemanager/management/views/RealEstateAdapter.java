package com.openclassrooms.realestatemanager.management.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.RealEstate;

import java.util.ArrayList;
import java.util.List;

public class RealEstateAdapter  extends RecyclerView.Adapter<RealEstateViewHolder> {

    // CALLBACK
    public interface Listener { void onClickDeleteButton(int position); }
    private final Listener callback;

    // FOR DATA
    private List<RealEstate> mRealEstateList;

    // CONSTRUCTOR
    public RealEstateAdapter(Listener callback) {
        this.mRealEstateList = new ArrayList<>();
        this.callback = callback;
    }

    @Override
    public RealEstateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.listview_item, parent, false);

        return new RealEstateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RealEstateViewHolder viewHolder, int position) {
        viewHolder.updateWithRealEstate(this.mRealEstateList.get(position), this.callback);
    }

    @Override
    public int getItemCount() {
        return this.mRealEstateList.size();
    }

    public RealEstate getItem(int position){
        return this.mRealEstateList.get(position);
    }

    public void updateData(List<RealEstate> items){
        this.mRealEstateList = items;
        this.notifyDataSetChanged();
    }
}