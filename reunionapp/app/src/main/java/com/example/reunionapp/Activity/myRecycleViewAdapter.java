package com.example.reunionapp.Activity;

import static com.example.reunionapp.Activity.DetailReunionActivity.ID_REUNION;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reunionapp.Model.Reunion;
import com.example.reunionapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class myRecycleViewAdapter extends RecyclerView.Adapter <myRecycleViewAdapter.myViewHolder>{


    private List<Reunion> mReunion;

    public myRecycleViewAdapter(List<Reunion> mReunion) {
        this.mReunion = mReunion;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        Reunion reunion = mReunion.get(position);

        //AFFICHER LA DATE EN RESPECTANT UN PATERN
       // SimpleDateFormat simpleDateFormat=new SimpleDateFormat( "dd-MM-yyyy' à 'HH:mm");
       //  SimpleDateFormat simpleDateFormat=new SimpleDateFormat( "dd-MM-yyyy");
       // String dateString = simpleDateFormat.format(reunion.getDate());
        //holder.mAboutReunion.setText(reunion.getSubject()+"-"+reunion.getHour()+"-"+dateString+" "+reunion.getPlace());
        holder.mAboutReunion.setText(reunion.toString());
        holder.mParticipantsReunion.setText(reunion.GetListparticipantsToString());
        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReunion.remove(reunion);
                System.out.println(reunion.toString()+"to delete"+mReunion.size());
                notifyDataSetChanged();
              // notifyItemChanged(position);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent=new Intent(v.getContext(),DetailReunionActivity.class);
                myIntent.putExtra(ID_REUNION,reunion.getId());
                System.out.println(reunion.getId());
                v.getContext().startActivity(myIntent);
            }
        });
        GradientDrawable drawable = (GradientDrawable) holder.mIconReunion.getBackground();
        //get drawable from image button
        switch (reunion.getRoom()) {
            case "Salle A":
                //set color
                //can use Color.parseColor(color) if color is a string
                drawable.setColor(Color.parseColor("#FF485D"));
                holder.mIconReunion.setBackground(drawable);
                break;

            case "Salle B":
                drawable.setColor(Color.parseColor("#4866FF"));
                holder.mIconReunion.setBackground(drawable);
                break;

            case "Salle C":
                drawable.setColor(Color.parseColor("#48FF8B"));
                holder.mIconReunion.setBackground(drawable);
                break;

            case "Salle D":
                drawable.setColor(Color.parseColor("#FFC248"));
                holder.mIconReunion.setBackground(drawable);
                break;

            case "Salle E":
                drawable.setColor(Color.parseColor("#FF487C"));
                holder.mIconReunion.setBackground(drawable);
                break;

            case "Salle F":
                drawable.setColor(Color.parseColor("#FF487C"));
                holder.mIconReunion.setBackground(drawable);
                break;

            case "Salle G":
                drawable.setColor(Color.parseColor("#FF7C48"));
                holder.mIconReunion.setBackground(drawable);
                break;
            case "Salle H":
                drawable.setColor(Color.parseColor("#6A48FF"));
                holder.mIconReunion.setBackground(drawable);
                break;
            case "Salle I":
                drawable.setColor(Color.parseColor("#ED48FF"));
                holder.mIconReunion.setBackground(drawable);
                break;
            case "Salle J":
                drawable.setColor(Color.parseColor("#FF4882"));
                holder.mIconReunion.setBackground(drawable);
                break;

        }

    }

    @Override
    public int getItemCount() {
        return mReunion.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder
    {

        public TextView mIconReunion;
        public TextView mAboutReunion;
        public TextView mParticipantsReunion;
        public ImageButton mDeleteButton;

        public myViewHolder(View itemView)
        {
            super(itemView);
            mIconReunion=itemView.findViewById(R.id.icone_reunion);
            mAboutReunion=itemView.findViewById(R.id.about_reunion);
            mParticipantsReunion=itemView.findViewById(R.id.participants);
            mDeleteButton=itemView.findViewById(R.id.button_delete);

        }
    }

}
