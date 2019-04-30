package com.example.findingshops;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Transparentactivitylocal extends AppCompatActivity {
    String phone1, phone2;
    Dialog mydialoug;
    Double destinationlongitude,destinationlatitude;


    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transparentactivitylocal);
        TextView close,nametextview;
        ImageButton contact,address,web;

        String Address,name;

        mydialoug = new Dialog(Transparentactivitylocal.this);
        mydialoug.setContentView(R.layout.custompopuplocal);

        Address = getIntent().getExtras().getString("Address");
        phone1 = getIntent().getExtras().getString("Contact");
        name = getIntent().getExtras().getString("Name");
        String Latitude = getIntent().getExtras().getString("Latitude");
        String Longitude = getIntent().getExtras().getString("Longtitude");

        destinationlatitude = Double.parseDouble(Latitude);
        destinationlongitude = Double.parseDouble(Longitude);


        phone2 = "tel:"+phone1;

        close =  mydialoug.findViewById(R.id.txtclose);
        contact = mydialoug.findViewById(R.id.call);
        web = mydialoug.findViewById(R.id.map);
        nametextview = mydialoug.findViewById(R.id.nameofshop1);
        address = mydialoug.findViewById(R.id.location);
        nametextview.setText(name);

        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri gmmIntentUri = Uri.parse("google.navigation:q="+destinationlatitude+","+destinationlongitude + "&zoom=1");
                Intent mapIntent =  new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);



            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                mydialoug.dismiss();
                finish();
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(phone2));
                startActivity(intent);
            }
        });mydialoug.show();





    }
}
