package com.openclassrooms.realestatemanager.management.addrealestate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.AddRealViewItemBinding;
import com.openclassrooms.realestatemanager.models.RealEstateImage;

import java.util.List;

public class AddImageRecyclerViewAdapter extends RecyclerView.Adapter<AddImageRecyclerViewAdapter.AddImageViewHolder> implements CustomClickListener  {

    private List<RealEstateImage> dataModelList;
    private Context context;


    public AddImageRecyclerViewAdapter(List<RealEstateImage> list, Context ctx) {
        this.dataModelList = list;
        this.context = ctx;
    }


    @NonNull
    @Override
    public AddImageRecyclerViewAdapter.AddImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       AddRealViewItemBinding binding= DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.add_real_view_item, parent, false);

        return new AddImageViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull AddImageRecyclerViewAdapter.AddImageViewHolder holder, int position) {
        RealEstateImage dataModel = dataModelList.get(position);
        holder.itemRowBinding.setRealImage(dataModel);
        holder.bind(dataModel);
        holder.itemRowBinding.setItemClickListener(this);

    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    @Override
    public void cardClicked(RealEstateImage f) {

            dataModelList.remove(f);
            this.notifyDataSetChanged();
    }


     class AddImageViewHolder extends RecyclerView.ViewHolder {
         AddRealViewItemBinding itemRowBinding;

         AddImageViewHolder(AddRealViewItemBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

         void bind(Object obj) {
            itemRowBinding.setVariable(com.openclassrooms.realestatemanager.BR.realImage , obj);
            itemRowBinding.executePendingBindings();
        }
    }
}
