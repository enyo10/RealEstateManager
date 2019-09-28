package com.openclassrooms.realestatemanager.management.addrealestate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.AddRealViewItemBinding;
import com.openclassrooms.realestatemanager.models.RealEstateImage;

import java.util.List;

public class AddImageRecyclerViewAdapter extends RecyclerView.Adapter<AddImageRecyclerViewAdapter.ViewHolder> implements CustomClickListener  {

    private List<RealEstateImage> dataModelList;
    private Context context;

    public AddImageRecyclerViewAdapter(List<RealEstateImage> list, Context ctx) {
        this.dataModelList = list;
        this.context = ctx;
    }


    @NonNull
    @Override
    public AddImageRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       AddRealViewItemBinding binding= DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.add_real_view_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AddImageRecyclerViewAdapter.ViewHolder holder, int position) {
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

            Toast.makeText(context, "You clicked " + f.getImageName(),
                    Toast.LENGTH_LONG).show();
            dataModelList.remove(f);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public AddRealViewItemBinding itemRowBinding;

        public ViewHolder(AddRealViewItemBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(com.openclassrooms.realestatemanager.BR.realImage , obj);
            itemRowBinding.executePendingBindings();
        }
    }
}