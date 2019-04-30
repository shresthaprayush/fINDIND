package com.example.findingshops;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class Transparentactivity extends AppCompatActivity {
    Dialog mydialog,secondialog;
    String longitutde,latitude;
    private double myLongitutde,myLatitude,Shoplongitutde,Shoplatitude;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;

    String baseurl = "http://logisparktech.com/showroom/images/";
    private FusedLocationProviderClient fusedLocationClient;
    private AlphaAnimation animation = new AlphaAnimation(1F, 0.8F);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transparentactivity);

        TextView txtclose,txtaddress,txtclosesecond,txtaddress2,txtnearto;
        ImageButton imgcall,imgageaddress,imageweb;
        ImageView imageViewlogo;


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mydialog = new Dialog(Transparentactivity.this);
        mydialog.setContentView(R.layout.custompopupauthentic);

        secondialog = new Dialog(Transparentactivity.this);
        secondialog.setContentView(R.layout.popupaddress);

        txtclose = (TextView) mydialog.findViewById(R.id.txtclose);
        txtclosesecond = (TextView)secondialog.findViewById(R.id.txtclosetwo);
        txtaddress2 = (TextView)secondialog.findViewById(R.id.addresssecond);

        txtaddress = (TextView) mydialog.findViewById(R.id.nameofshop);
        imageViewlogo = (ImageView) mydialog.findViewById(R.id.companylogo);
        imgcall = (ImageButton) mydialog.findViewById(R.id.call);
        imageweb = (ImageButton) mydialog.findViewById(R.id.web);
        imgageaddress = (ImageButton) mydialog.findViewById(R.id.location);

       String address = getIntent().getExtras().getString("Address");
       final String name = getIntent().getExtras().getString("Name");

       String[] finalname = name.split(" ");

       txtaddress.setText(address);

       txtaddress2.setText(address);
       Picasso.get().load("https://logisparktech.com/showroom/images/"+finalname[0]+".png").into(imageViewlogo);


        final String phone;
        final String phone2 = getIntent().getExtras().getString("Contact");
        phone = "tel:"+phone2;

        final String location = "geo:"+latitude+","+longitutde;


        imageweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                fetchlocation();


                latitude = getIntent().getExtras().getString("Latitude");
                longitutde = getIntent().getExtras().getString("Longtitude");
                final Double latitudedouble = Double.parseDouble(latitude);
                final Double longitutdedouble = Double.parseDouble(longitutde);

                Uri gmmIntentUri = Uri.parse("google.navigation:q="+latitudedouble+","+longitutdedouble + "&zoom=1");
                Intent mapIntent =  new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);



            }
        });

        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                mydialog.dismiss();
                finish();
            }
        });

        txtclosesecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                secondialog.dismiss();

            }
        });

        imgcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(phone));
                startActivity(intent);
            }
        });

        imgageaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                secondialog.show();


            }
        });
        mydialog.show();






    }


    private void fetchlocation() {

        if (ContextCompat.checkSelfPermission(Transparentactivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(Transparentactivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(Transparentactivity.this)
                        .setTitle("Important Permission Required")
                        .setMessage("You have to give this permission to access this feature")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ActivityCompat.requestPermissions(Transparentactivity.this,
                                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);


                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(Transparentactivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                myLatitude = location.getLatitude();
                                myLongitutde = location.getLongitude();

                                Toast.makeText(Transparentactivity.this,"Lat"+myLatitude+"Long"+myLongitutde,Toast.LENGTH_SHORT).show();


                            }
                        }
                    });

        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION){
            if(grantResults.length >0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){

            }
            else {

            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



}
