package com.openclassrooms.realestatemanager.management.views;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.RealEstate;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RealEstateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.real_estate_place)
    TextView mPlaceTextView;
    @BindView(R.id.real_estate_imageView)
    ImageView imageView;
    @BindView(R.id.real_estate_price) TextView mPriceTextView;
    @BindView(R.id.real_estate_type)TextView mTypeTextView;


    // FOR DATA
    private WeakReference<RealEstateAdapter.Listener> callbackWeakRef;

    public RealEstateViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithRealEstate(RealEstate realEstate, RealEstateAdapter.Listener callback){
        this.callbackWeakRef = new WeakReference<RealEstateAdapter.Listener>(callback);
        //mPlaceTextView.setText(realEstate.);
       /* this.textView.setText(item.getText());
        this.imageButton.setOnClickListener(this);

        if (item.getSelected()){
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            textView.setPaintFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }*/
    }

    @Override
    public void onClick(View view) {
        RealEstateAdapter.Listener callback = callbackWeakRef.get();
       // if (callback != null) callback.onClickDeleteButton(getAdapterPosition());
    }
}
