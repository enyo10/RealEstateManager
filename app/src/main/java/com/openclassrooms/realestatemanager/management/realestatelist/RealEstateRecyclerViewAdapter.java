package com.openclassrooms.realestatemanager.management.realestatelist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentErstateListviewItemBinding;
import com.openclassrooms.realestatemanager.management.activities.RealEstateMainActivity;
import com.openclassrooms.realestatemanager.models.RealEstate;
import com.openclassrooms.realestatemanager.utils.DataConverter;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.viewmodel.RealEstateViewModel;

import java.util.ArrayList;
import java.util.List;

public class RealEstateRecyclerViewAdapter extends RecyclerView.Adapter<RealEstateRecyclerViewAdapter.MyViewHolder>implements EstateListItemClickedListener {

    private static final String TAG = RealEstateRecyclerViewAdapter.class.getName();


    private List<RealEstate> dataModelList;
    private Context context;
    private RealEstateViewModel mViewModel;
    private RealEstateMainActivity mRealEstateMainActivity;


    public RealEstateRecyclerViewAdapter( Context context,RealEstateViewModel viewModel){
        this.dataModelList = new ArrayList<>();
        this.context = context;
        mRealEstateMainActivity=(RealEstateMainActivity) context;
        this.mViewModel= mRealEstateMainActivity.mRealEstateViewModel;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragmentErstateListviewItemBinding

         binding= DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.fragment_erstate_listview_item, parent, false);


        return new MyViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        RealEstate dataModel = dataModelList.get(position);
        Log.d(TAG, " real:  "+dataModel.toString());
        holder.itemRowBinding.setRealEstate(dataModel);
        holder.itemRowBinding.setDataConverter(new DataConverter());
        holder.itemRowBinding.setSold(dataModel.isSold());
        if(dataModel.getImages()!=null)
            if(Utils.jsonStringToRealEstateImageList(dataModel.getImages()).size()!=0)
        holder.itemRowBinding.setRealImage(Utils.jsonStringToRealEstateImageList(dataModel.getImages()).get(0));

        holder.bind(dataModel);
      //  holder.bind(Utils.jsonStringToRealEstateImageList(dataModel.getImages()).get(0));
        holder.itemRowBinding.setEstateClickListener(this);

    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    @Override
    public void onEstateClicked(RealEstate realEstate) {

        this.mViewModel.getSelectedRealEstate().postValue(realEstate);
        this.mRealEstateMainActivity.showDetailsFragment();
        Toast.makeText(context, "You clicked " + realEstate.getType(),
                Toast.LENGTH_LONG).show();
        Log.d(TAG, " You have selected : "+realEstate.toString());


    }

    public void updateWithData(List<RealEstate> realEstates) {
        this.dataModelList = realEstates;
        this.notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        FragmentErstateListviewItemBinding itemRowBinding;

        MyViewHolder(FragmentErstateListviewItemBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        void bind(Object obj) {
            itemRowBinding.setVariable(com.openclassrooms.realestatemanager.BR.realEstate , obj);
            itemRowBinding.executePendingBindings();
        }
    }
}
