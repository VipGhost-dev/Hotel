package com.example.hotel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterHotel extends BaseAdapter {
    private Context mContext;
    List<DataStorage> hotelList;

    public AdapterHotel(Context mContext, List<DataStorage> listProduct) {
        this.mContext = mContext;
        this.hotelList = listProduct;
    }

    @Override
    public int getCount() {
        return hotelList.size();
    }

    @Override
    public Object getItem(int i) {
        return hotelList.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return hotelList.get(i).getID();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        View v = View.inflate(mContext,R.layout.item_layout,null);
        TextView tRoom = v.findViewById(R.id.txtRoom);
        TextView tCountPeoples = v.findViewById(R.id.txtCountPeoples);
        TextView tStatus = v.findViewById(R.id.txtStatus);
        DataStorage dataStorage = hotelList.get(i);
        tRoom.setText(String.valueOf(dataStorage.getRoom()));
        tCountPeoples.setText(String.valueOf(dataStorage.getCount_Peoples()));
        tStatus.setText(dataStorage.getStatus());

        return v;
    }
}
