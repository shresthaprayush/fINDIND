package com.example.findingshops.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.findingshops.Data.ParkingData;
import com.example.findingshops.Main.TransparentActivityParking;
import com.example.findingshops.R;

import java.util.ArrayList;
import java.util.List;

public class ParkingAdapter extends RecyclerView.Adapter<ParkingAdapter.Parkingviewholder> {

    private Context mcontext;
    private List<ParkingData> parkingData;

    public ParkingAdapter(Context mcontext, List<ParkingData> parkingData) {
        this.mcontext = mcontext;
        this.parkingData = parkingData;
    }

    @NonNull
    @Override
    public ParkingAdapter.Parkingviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        view = layoutInflater.inflate(R.layout.card_documentcategories,viewGroup,false);

        return new Parkingviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Parkingviewholder parkingviewholder, final int i) {

        parkingviewholder.parkinglocation.setText(parkingData.get(i).getAddress());
        parkingviewholder.parkingname.setText(parkingData.get(i).getName());
        parkingviewholder.cardViewparking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext,TransparentActivityParking.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("Name",parkingData.get(i).getName());
                intent.putExtra("Address",parkingData.get(i).getAddress());
                intent.putExtra("Nearto",parkingData.get(i).getNearto());
                intent.putExtra("Latitude",parkingData.get(i).getLatitude());
                intent.putExtra("Longitutde",parkingData.get(i).getLongitude());
                intent.putExtra("Type",parkingData.get(i).getType());
                intent.putExtra("Bikeprice",parkingData.get(i).getBike());
                intent.putExtra("Carprice",parkingData.get(i).getCar());
                intent.putExtra("Jeepprice",parkingData.get(i).getJeep());
                mcontext.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return parkingData.size();
    }

    public class Parkingviewholder extends RecyclerView.ViewHolder {

        TextView parkingname,parkinglocation;
        CardView cardViewparking;



        public Parkingviewholder(@NonNull View itemView) {
            super(itemView);

            parkingname = itemView.findViewById(R.id.docscategory_title);
            parkinglocation = itemView.findViewById(R.id.descriptionlocal);
            cardViewparking = itemView.findViewById(R.id.cardviewdocscat_id);
        }
    }

    public void updatelist(ArrayList<ParkingData> newlist){
        parkingData = new ArrayList<>();
        parkingData.addAll(newlist);
        notifyDataSetChanged();
    }


}
