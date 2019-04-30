package com.example.findingshops.Main;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findingshops.R;
import com.example.findingshops.Transparentactivity;

public class TransparentActivityParking extends AppCompatActivity {
    String name,lat,longg,type,bikeprice,carprice,jeepprice,nearto,address,types;
    Dialog mydialoug,mydialougprice,mydialouglocaiton;
    Double destinationlongitude,destinationlatitude;

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transparent_parking);

        TextView close,txtname,close2,txtaddresstwo,txtnearto,close3,txtnameofparking,txtbikeprice,txtcarprice,txtjeepprice;
        ImageButton btnprice,btnlocation,btnmap;

        mydialoug = new Dialog(TransparentActivityParking.this);
        mydialoug.setContentView(R.layout.custompopupparking);
        mydialoug.show();

        mydialouglocaiton = new Dialog(TransparentActivityParking.this);
        mydialouglocaiton.setContentView(R.layout.popupaddress);

        mydialougprice = new Dialog(TransparentActivityParking.this);
        mydialougprice.setContentView(R.layout.popupprice);

        name = getIntent().getExtras().getString("Name");
        lat = getIntent().getExtras().getString("Latitude");
        longg = getIntent().getExtras().getString("Longitutde");
        address = getIntent().getExtras().getString("Address");
        type = getIntent().getExtras().getString("Type");
        bikeprice = getIntent().getExtras().getString("Bikeprice");
        carprice = getIntent().getExtras().getString("Carprice");
        jeepprice = getIntent().getExtras().getString("Jeepprice");
        nearto = getIntent().getExtras().getString("Nearto");

        destinationlatitude = Double.parseDouble(lat);
        destinationlongitude = Double.parseDouble(longg);



        close = mydialoug.findViewById(R.id.txtcloseparking);
        close3 = mydialougprice.findViewById(R.id.txtcloseprice);
        close2 = mydialouglocaiton.findViewById(R.id.txtclosetwo);
        txtaddresstwo = mydialouglocaiton.findViewById(R.id.addresssecond);
        txtnearto = mydialouglocaiton.findViewById(R.id.nearaddress);
        txtcarprice = mydialougprice.findViewById(R.id.carprice);
        txtbikeprice = mydialougprice.findViewById(R.id.bikeprice);
        txtjeepprice = mydialougprice.findViewById(R.id.jeepprice);
        txtnameofparking = mydialougprice.findViewById(R.id.nameofparking);
        btnlocation = mydialoug.findViewById(R.id.locationparking);
        btnmap = mydialoug.findViewById(R.id.mapparking);
        btnprice = mydialoug.findViewById(R.id.price);
        txtname = mydialoug.findViewById(R.id.nameofparking);

        txtname.setText(name);


        txtaddresstwo.setText(address);
        txtnearto.setText(nearto);

        types = "("+type+")";




        if (type.toLowerCase().equals("paid")){

            txtnameofparking.setText(name+types);
            txtjeepprice.setText(jeepprice);
            txtcarprice.setText(carprice);
            txtbikeprice.setText(bikeprice);

            btnprice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.startAnimation(buttonClick);
                    mydialougprice.show();

                }
            });
        }

        else {
            Toast.makeText(TransparentActivityParking.this,"It is a free Parking",Toast.LENGTH_LONG).show();
        }

        close2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                mydialouglocaiton.dismiss();
                finish();
            }
        });

        close3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                mydialougprice.dismiss();

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

        btnlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                mydialouglocaiton.show();


            }
        });


        btnmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);

                Uri gmmIntentUri = Uri.parse("google.navigation:q="+destinationlatitude+","+destinationlongitude + "&zoom=1");
                Intent mapIntent =  new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });









    }
}
