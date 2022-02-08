package com.example.reunionapp.Activity;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.reunionapp.DI.DI;
import com.example.reunionapp.R;
import com.example.reunionapp.Service.InterfaceReunionApiService;
import com.example.reunionapp.Service.ReunionApiService;
import com.example.reunionapp.util.DateUtils;

import java.text.ParseException;

public class DetailReunionActivity extends AppCompatActivity {
    public static final String ID_REUNION ="ID_REUNION" ;
    InterfaceReunionApiService mApiService;
    TextView mSujet_reunion_detail;
    TextView mdate_reunion_detail;
    TextView msalle_detail;
    TextView mlistparticipants_detail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_reunion);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSujet_reunion_detail=findViewById(R.id.sujet_reunion_detail);
        mdate_reunion_detail=findViewById(R.id.date_reunion_detail);
        msalle_detail =findViewById(R.id.salle_detail);
        mlistparticipants_detail =findViewById(R.id.listparticipants_detail);


        Long id_item=getIntent().getLongExtra(ID_REUNION,0);
        mApiService = DI.getReunionApiService();
        mApiService.getReunionById(id_item);
        System.out.println(" Reunion recupérer "+mApiService.getReunionById(id_item));
        mSujet_reunion_detail.setText(mApiService.getReunionById(id_item).getSubject());
        String date="";
        String time="";
        try {
            date= DateUtils.convertDateToString(mApiService.getReunionById(id_item).getDate());
            time=DateUtils.GetTimeFromDate(mApiService.getReunionById(id_item).getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mdate_reunion_detail.setText(date+" à "+time);
        msalle_detail.setText(mApiService.getReunionById(id_item).getRoom());
        mlistparticipants_detail.setText(mApiService.getReunionById(id_item).GetListparticipantsToString());

    }
}