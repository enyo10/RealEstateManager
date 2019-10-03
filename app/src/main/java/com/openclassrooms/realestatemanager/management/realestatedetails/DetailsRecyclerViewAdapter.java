package com.openclassrooms.realestatemanager.management.realestatedetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentDetailsRecyclerViewItemBinding;
import com.openclassrooms.realestatemanager.models.RealEstateImage;

import java.util.List;

public class DetailsRecyclerViewAdapter extends RecyclerView.Adapter<DetailsRecyclerViewAdapter.ImageViewHolder> {

    private Context mContext;
    private List<RealEstateImage> mImagesList;

    public DetailsRecyclerViewAdapter(List<RealEstateImage>uris,Context context){
        this.mImagesList=uris;
        this.mContext=context;

    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

     FragmentDetailsRecyclerViewItemBinding binding=  DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.fragment_details_recycler_view_item, parent, false);

        return new ImageViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return this.mImagesList.size();
    }


    class ImageViewHolder extends RecyclerView.ViewHolder {
        FragmentDetailsRecyclerViewItemBinding itemRowBinding;


        public ImageViewHolder(@NonNull FragmentDetailsRecyclerViewItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemRowBinding=itemBinding;
        }

        void bind(Object obj) {
            itemRowBinding.setVariable(com.openclassrooms.realestatemanager.BR.RealEstateImage , obj);
            itemRowBinding.executePendingBindings();
        }
    }
}
