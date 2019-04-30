package com.example.findingshops.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.example.findingshops.Data.LocalData;
import com.example.findingshops.Main.SpecificCompany;
import com.example.findingshops.R;

import java.util.List;

public class Authenticdataadapter extends RecyclerView.Adapter<Authenticdataadapter.Myviewholder> {

    private Context mcontext;
    private List<LocalData> localData;
    Dialog mydialog;
    Toolbar toolbar = null;
    AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    public Authenticdataadapter(Context mcontext, List<LocalData> localData) {
        this.mcontext = mcontext;
        this.localData = localData;
    }

    @NonNull
    @Override
    public Authenticdataadapter.Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        view = layoutInflater.inflate(R.layout.cardviewauthneticshop,viewGroup,false);
        return new Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, final int i) {
        myviewholder.tvauthenticname.setText(localData.get(i).getName());
        
        myviewholder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, SpecificCompany.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Companyname",localData.get(i).getName());
//                intent.putExtra("Color",localData.get(i).getColor());

                mcontext.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return localData.size();
    }

    public static class Myviewholder extends RecyclerView.ViewHolder{

        TextView tvauthenticname;
        CardView cardView;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            tvauthenticname = itemView.findViewById(R.id.authenticshop_title);
            cardView = itemView.findViewById(R.id.cardviewauthentic_id);




        }
    }
}
