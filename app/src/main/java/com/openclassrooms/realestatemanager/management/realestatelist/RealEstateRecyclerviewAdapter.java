package com.openclassrooms.realestatemanager.management.realestatelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ListviewItemBinding;
import com.openclassrooms.realestatemanager.models.RealEstate;

import java.util.List;

public class RealEstateRecyclerviewAdapter extends RecyclerView.Adapter<RealEstateRecyclerviewAdapter.MyViewHolder>implements ListItemClickedListener {

    private List<RealEstate> dataModelList;
    private Context context;

    public RealEstateRecyclerviewAdapter(List<RealEstate>list, Context context){
        this.dataModelList=list;
        this.context=context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ListviewItemBinding binding= DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.listview_item, parent, false);


        return new MyViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onItemClicked(RealEstate realEstate) {
        // Do something with the selected realEstate.

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ListviewItemBinding itemRowBinding;

        MyViewHolder(ListviewItemBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        void bind(Object obj) {
            itemRowBinding.setVariable(com.openclassrooms.realestatemanager.BR.realEstate , obj);
            itemRowBinding.executePendingBindings();
        }
    }
}
