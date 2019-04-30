package com.example.findingshops.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findingshops.Data.LocalData;
import com.example.findingshops.R;
import com.example.findingshops.Transparentactivity;

import java.util.ArrayList;
import java.util.List;

public class Specificdataadapter extends RecyclerView.Adapter<Specificdataadapter.Specificviewholder> {

    private Context mcontext;
    private List<LocalData> localData;
    Dialog mydialog;


    public Specificdataadapter(Context mcontext, List<LocalData> localData) {
        this.mcontext = mcontext;
        this.localData = localData;
    }

    @NonNull
    @Override
    public Specificviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {

        final View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        view = layoutInflater.inflate(R.layout.cardspecificcompany,viewGroup,false);

         return new Specificviewholder(view);



    }

    @Override
    public void onBindViewHolder(@NonNull final Specificviewholder specificviewholder, final int i) {



        specificviewholder.textviewaddress.setText(localData.get(i).getAddress());


        specificviewholder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mcontext,Transparentactivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("Address",localData.get(i).getAddress());
                intent.putExtra("Name",localData.get(i).getName());
                intent.putExtra("Contact",localData.get(i).getContact());
                intent.putExtra("Longtitude",localData.get(i).getLongitude());
                intent.putExtra("Latitude",localData.get(i).getLatitude());
                mcontext.startActivity(intent);






            }
        });


    }

    @Override
    public int getItemCount() {
        return localData.size();
    }

    public static class Specificviewholder extends RecyclerView.ViewHolder {
        private TextView textviewaddress;
        private CardView cardView;
        public Specificviewholder(@NonNull View itemView) {
            super(itemView);
            textviewaddress = itemView.findViewById(R.id.specific_address);
            cardView = itemView.findViewById(R.id.cardspecific_id);

        }
    }

    public void updatelist(ArrayList<LocalData> newlist){
        localData = new ArrayList<>();
        localData.addAll(newlist);
        notifyDataSetChanged();
    }


}
