package com.example.reunionapp.Activity;

import static com.example.reunionapp.Activity.mydialog.mDateF;
import static com.example.reunionapp.Activity.mydialog.mSalleF;
import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompatSideChannelService;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reunionapp.DI.DI;
import com.example.reunionapp.Model.Reunion;
import com.example.reunionapp.R;
import com.example.reunionapp.Service.InterfaceReunionApiService;
import com.example.reunionapp.Service.ReunionApiService;
import com.example.reunionapp.databinding.ActivityAddReunionBinding;
import com.example.reunionapp.databinding.ActivityMainBinding;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements mydialog.MyDialogListener{


    private ActivityMainBinding binding;
    myRecycleViewAdapter adapter;
    static InterfaceReunionApiService mReunionApiService;

    @SuppressLint("UnsafeOptInUsageError")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        mReunionApiService = DI.getReunionApiService();
        List<Reunion> LIST_REUNIONS = mReunionApiService.getReunion();
        Log.e("SIZE_LIST_REUNIONS", "onCreate() MainActivity " + LIST_REUNIONS.size());
        if (LIST_REUNIONS.size() == 0) {
            binding.messageEmptyRecyclerView.setText("AUCUNE REUNION N'EST PREVUE !");
            binding.messageEmptyRecyclerView.setVisibility(View.VISIBLE);
        } else {
            adapter = new myRecycleViewAdapter(mReunionApiService.getReunion());
            binding.myrecycleview.setAdapter(adapter);
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
            mLinearLayoutManager.setStackFromEnd(true);
            mLinearLayoutManager.setReverseLayout(true);
            binding.myrecycleview.setLayoutManager(mLinearLayoutManager);

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.myrecycleview.getContext(),
                    mLinearLayoutManager.getOrientation());
            binding.myrecycleview.addItemDecoration(dividerItemDecoration);

            binding.addfloatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent myIntent = new Intent(MainActivity.this, AddReunion.class);
                    startActivity(myIntent);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
      //  Toast.makeText(MainActivity.this,"Filtrer par date et salle",Toast.LENGTH_LONG).show();
        opendialog();
        return true;
    }

    private void opendialog() {
        mydialog Mydialog=new mydialog();
        Mydialog.show(getSupportFragmentManager(),"MY FILTER DIALOG ");
    }

    private void buttonSelectDate()

    {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int lastSelectedYear = c.get(Calendar.YEAR);
        int lastSelectedMonth = c.get(Calendar.MONTH);
        int lastSelectedDayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        // Date Select Listener.
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {

            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth)
            {
                c.set(Calendar.YEAR,year);
                c.set(Calendar.MONTH,monthOfYear);
                c.set(Calendar.DAY_OF_WEEK,dayOfMonth);
            }
        };

        DatePickerDialog datePickerDialog  = new DatePickerDialog(this,
                dateSetListener, lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth);
        // Show
        datePickerDialog.show();

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void applyTexts(String date_filtre, String salle_filtre) {

        List<Reunion> list= new ArrayList<>();
        if(date_filtre.equals("") && salle_filtre.equals(""))
        {
            binding.myrecycleview.setAdapter(new myRecycleViewAdapter(mReunionApiService.getReunion()));
            binding.badge.setText("");
            binding.badge.setBackground(null);
            mDateF="";
            mSalleF="";
            binding.messageEmptyRecyclerView.setVisibility(View.INVISIBLE);
        }
        else {
            String[] list_salle = salle_filtre.split(",");
            System.out.println("list salle est " + list_salle.toString());
            try {
                list = mReunionApiService.filtreByDateAndRooms(date_filtre, list_salle);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println("size onDateSet " + list.size());
            binding.myrecycleview.setAdapter(new myRecycleViewAdapter(list));
            binding.badge.setText("F");
            binding.badge.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.badge_circle));
            if(list.size()==0)
            {

                binding.messageEmptyRecyclerView.setText("AUCUNE REUNION NE CORRESPOND A VOTRE FILTRE ");
                binding.messageEmptyRecyclerView.setVisibility(View.VISIBLE);
            }
            else
            {
                binding.messageEmptyRecyclerView.setVisibility(View.INVISIBLE);
            }

        }
    }
}