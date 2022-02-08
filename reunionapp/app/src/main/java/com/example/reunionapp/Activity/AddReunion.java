package com.example.reunionapp.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.reunionapp.DI.DI;
import com.example.reunionapp.Model.Reunion;
import com.example.reunionapp.R;
import com.example.reunionapp.Service.InterfaceReunionApiService;
import com.example.reunionapp.util.DateUtils;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddReunion extends AppCompatActivity {

   // ActivityAddReunionBinding binding;
   private TextInputLayout  mSubject;
    private TextInputLayout   mSalle;
   private TextInputLayout mDatereunion;
  private TextInputLayout mHourreunion;
    private TextInputLayout mListParticipants;
  private Button mAddButton;
  AutoCompleteTextView autoCompleteTextView;

    InterfaceReunionApiService mApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reunion);
        mApiService=DI.getReunionApiService();

        //binding
     autoCompleteTextView = findViewById(R.id.salleautoCompleteTextView);
      mSubject=findViewById(R.id.subject);
        mDatereunion = findViewById(R.id.date_reunion);
       mHourreunion = findViewById(R.id.hour);
        mListParticipants = findViewById(R.id.listparticipants);
        mAddButton = findViewById(R.id.add_button);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        int r=R.layout.list_item;
        //ArrayList<String> items=new ArrayList<String>();
        Resources resources=getResources();
        String[] items = resources.getStringArray(R.array.room);
        ArrayAdapter adapter= new ArrayAdapter(this,r,items);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item_salle=parent.getItemAtPosition(position).toString();
               // Toast.makeText(getApplicationContext(),"item selected is"+item_salle,Toast.LENGTH_LONG).show();

            }
        });

        mDatereunion.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelectDate();
            }
        });
        mHourreunion.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelectHeure();
            }
        });

        mListParticipants.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateMultipleChoicelistmailAlertDialog(mListParticipants.getEditText().getText().toString());
            }
        });

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ADD REUNION", "onClick() called with: = mSubject " +mSubject.getEditText().getText().toString()+" Datereunion"+ mDatereunion.getEditText().getText().toString()+" HEURE "+
                        mHourreunion.getEditText().getText().toString()+" LISTE PARTICIPANT "+
                        mListParticipants.getEditText().getText().toString() );

                Date date= new Date();

             try
                {
                    date = DateUtils.convertStringToDate(mDatereunion.getEditText().getText().toString()+mHourreunion.getEditText().getText().toString(),"dd-MM-yyyyhh:mm");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

               // System.out.println("Date saisi "+date.toString());

                List<String> participant = Arrays.asList(mListParticipants.getEditText().getText().toString().split(" ,"));

                 Reunion Reunion =new Reunion(20,mSubject.getEditText().getText().toString(),autoCompleteTextView.getText().toString(),date,participant);
              //  Log.e("ADD REUNION", "onClick() called with: = Reunion " +Reunion.toString());
                mApiService.addReunion(Reunion);
                finish();
            }
        });

    }

    public void buttonSelectDate() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int lastSelectedYear = c.get(Calendar.YEAR);
        int lastSelectedMonth = c.get(Calendar.MONTH);
        int lastSelectedDayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        // Date Select Listener.
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear);
                c.set(Calendar.DAY_OF_WEEK, dayOfMonth);
                System.out.println(c.getTime());
               mDatereunion.getEditText().setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                dateSetListener, lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth);
        // Show
        datePickerDialog.show();
    }

    private void buttonSelectHeure()

    {
        // Get Current Time
                Calendar myCalendar = Calendar.getInstance();
                int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
                int minute = myCalendar.get(Calendar.MINUTE);
            // time picker dialog
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(AddReunion.this, new TimePickerDialog.OnTimeSetListener()
                    {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
                        {
                           mHourreunion.getEditText().setText( selectedHour + ":" + selectedMinute);
                        }
                    }, hour, minute, true); //Yes 24-hour time mode
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
    }
    public void CreateMultipleChoicelistmailAlertDialog(String participants)
    {

        //  mUserItems : tableau des positions
        ArrayList<Integer> mUserItems = new ArrayList<>();
        // listItems est la liste de tous mes item : items array,
        String[] listItems = getResources().getStringArray(R.array.mails);
        // checkedItems : boolean array
        boolean[] checkedItems;
        checkedItems = new boolean[listItems.length];
        System.out.println(participants) ;
        for(int i=0;i<participants.split(",").length;i++)
        {
            System.out.println(" Participant"+participants.split(",")[i]);
            for(int j=0;j<listItems.length;j++)
            {
                System.out.println(listItems[j].equals(participants.split(",")[i]));
                if(listItems[j].equals(participants.split(",")[i]))
                {
                    mUserItems.add(j);
                    checkedItems[j]=true;
                }
            }
        }

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddReunion.this);
        mBuilder.setTitle(R.string.dialog_title);
        mBuilder.setIcon(R.drawable.metting_img);
        mBuilder.setCancelable(false);
        mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                if (isChecked) {
                    if (!mUserItems.contains(position)) {
                        mUserItems.add(position);
                    }

                } else if (!mUserItems.contains(position)) {
                    mUserItems.add(position);
                }
            }
        });

        mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                String item = "";
                for (int i = 0; i < mUserItems.size(); i++) {
                    item = item + listItems[mUserItems.get(i)];
                    if (i != mUserItems.size() - 1) {
                        item = item + ",";
                    }
                }
                mListParticipants.getEditText().setText(item);
            }
        });

        // Call dialog.setNegativeButton and give it a label, inside this button
        // we are going to dismiss the dialog by calling dialog.dismiss()

        mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        //        // Call dialog.setNeutralButton and give it a label,
        //        inside this button we are going to reset/uncheck items in android alertdialog

        mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                for (int i = 0; i < checkedItems.length; i++) {
                    checkedItems[i] = false;
                    mUserItems.clear();
                    mListParticipants.getEditText().setText("");
                }
            }
        });
        //Call the dialog.show() to show the dialog
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }
}