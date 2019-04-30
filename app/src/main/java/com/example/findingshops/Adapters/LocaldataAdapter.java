package com.example.findingshops.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.example.findingshops.Data.LocalData;
import com.example.findingshops.R;
import com.example.findingshops.Transparentactivitylocal;

import java.util.ArrayList;
import java.util.List;

public class LocaldataAdapter extends RecyclerView.Adapter<LocaldataAdapter.Myviewholder> {

    private Context mcontext;
     List<LocalData> localData;
     Dialog mydialog;
    Toolbar toolbar = null;
    AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);


    public LocaldataAdapter(Context mcontext, List<LocalData> localData) {
        this.mcontext = mcontext;
        this.localData = localData;
    }

    @NonNull
    @Override
    public LocaldataAdapter.Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        view = layoutInflater.inflate(R.layout.card_documentcategories,viewGroup,false);
        return new Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocaldataAdapter.Myviewholder myviewholder, final int i) {

        myviewholder.tv_localname.setText(localData.get(i).getName());
        mydialog = new Dialog(mcontext);

        myviewholder.tv_localdescription.setText(localData.get(i).getAddress());

        myviewholder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(mcontext, Transparentactivitylocal.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("Name",localData.get(i).getName());
            intent.putExtra("Address",localData.get(i).getAddress());
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

    public static class Myviewholder extends RecyclerView.ViewHolder {

        TextView tv_localname , tv_localdescription;
        CardView cardView;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardviewdocscat_id);

            tv_localname = itemView.findViewById(R.id.docscategory_title);
            tv_localdescription = itemView.findViewById(R.id.descriptionlocal);

        }
    }


    public void updatelist(ArrayList<LocalData> newlist){
        localData = new ArrayList<>();
        localData.addAll(newlist);
        notifyDataSetChanged();
    }


}
