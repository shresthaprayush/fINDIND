package com.example.findingshops.Adapters;

//To display contents of home  in recycler view


import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findingshops.Data.Home_Data;
import com.example.findingshops.Data.LocalData;
import com.example.findingshops.Main.AuthenticServiceCenters;
import com.example.findingshops.Main.LocalServiceCenters;
import com.example.findingshops.Main.Parking;
import com.example.findingshops.R;
import com.example.findingshops.Soon;
import com.example.findingshops.Workshops;

import java.util.ArrayList;
import java.util.List;


public class RecycleViewHomeAdapter extends RecyclerView.Adapter<RecycleViewHomeAdapter.MyHomeViewHolder> {
//implement methods then proceed to step 3

    //3 Declarration of variable
    private Context mcontext;
    //class as list
    String myerror = "This is error";
    private List<Home_Data> mhlist;

    //constructor


    public RecycleViewHomeAdapter(Context mcontext, List<Home_Data> mhlist) {
        this.mcontext = mcontext;
        this.mhlist = mhlist;
    }

    @Override
    public MyHomeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        LayoutInflater mhinflater = LayoutInflater.from(mcontext);
        view = mhinflater.inflate(R.layout.card_home,viewGroup,false);
        return new MyHomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHomeViewHolder holder, final int position) {

        //getting the data
        holder.tv_home_title.setText(mhlist.get(position).getHtitle());
        holder.tv_home_description.setText(mhlist.get(position).getHdescription());
        holder.home_thumbnail.setImageResource(mhlist.get(position).getHthumbnail());

        if (mhlist.get(position).getHtitle().toUpperCase().equals("PARKING SPOTS")){


            holder.cardviewimage.setImageResource(R.drawable.card);
        }

        else if (mhlist.get(position).getHtitle().toUpperCase().equals("WORKSHOPS")){
            holder.cardviewimage.setImageResource(R.drawable.card2);
        }

        else if (mhlist.get(position).getHtitle().toUpperCase().equals("PETROL PUMPS")){
            holder.cardviewimage.setImageResource(R.drawable.card3);

        }

        else if (mhlist.get(position).getHtitle().toUpperCase().equals("LOCAL")){


            holder.cardviewimage.setImageResource(R.drawable.card);
        }

        else if (mhlist.get(position).getHtitle().toUpperCase().equals("AUTHENTIC")){
            holder.cardviewimage.setImageResource(R.drawable.card2);
        }

        else {

            System.out.println("Error");


        }



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mhlist.get(position).getHtitle().toUpperCase().equals("LOCAL")){



                    Intent intent = new Intent(mcontext, LocalServiceCenters.class);
                    intent.putExtra("CategoryHomeTitle",mhlist.get(position).getHtitle());
                    mcontext.startActivity(intent);

                }

                else  if (mhlist.get(position).getHtitle().toUpperCase().equals("AUTHENTIC")){


                    Intent intent = new Intent(mcontext, AuthenticServiceCenters.class);
                    intent.putExtra("CategoryHomeTitle",mhlist.get(position).getHtitle());
                    mcontext.startActivity(intent);

                }

                else  if (mhlist.get(position).getHtitle().toUpperCase().equals("WORKSHOPS")){

                    Intent intent = new Intent(mcontext, Workshops.class);
                    intent.putExtra("CategoryHomeTitle",mhlist.get(position).getHtitle());
                    mcontext.startActivity(intent);

                }

                else  if (mhlist.get(position).getHtitle().toUpperCase().equals("PARKING SPOTS")){

                    Intent intent = new Intent(mcontext, Parking.class);
                    intent.putExtra("CategoryHomeTitle",mhlist.get(position).getHtitle());
                    mcontext.startActivity(intent);

                }

                else  if (mhlist.get(position).getHtitle().toUpperCase().equals("PETROL PUMPS")){


                    Intent intent = new Intent(mcontext, Soon.class);
                    intent.putExtra("CategoryHomeTitle",mhlist.get(position).getHtitle());
                    mcontext.startActivity(intent);

                }

                else {
                    System.out.println("Error");
                }



            }
        });




//


    }

    @Override
    public int getItemCount() {
        return mhlist.size();
    }


    //step 2 making class
    public static class MyHomeViewHolder extends RecyclerView.ViewHolder{
        TextView tv_home_title,tv_home_description;
        ImageView home_thumbnail,cardviewimage;
        CardView cardView;

        public MyHomeViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_home_title = (TextView) itemView.findViewById(R.id.category_title);
            home_thumbnail = (ImageView) itemView.findViewById(R.id.imageViewcategory);
            tv_home_description = (TextView) itemView.findViewById(R.id.categorydesc);
            cardView = (CardView) itemView.findViewById(R.id.cardviewhome_id);
            cardviewimage = (ImageView) itemView.findViewById(R.id.cardviewimage);



        }
    }

    public void updatelist(List<Home_Data> newlist){
        mhlist = new ArrayList<>();
       mhlist.addAll(newlist);
        notifyDataSetChanged();
    }


}


