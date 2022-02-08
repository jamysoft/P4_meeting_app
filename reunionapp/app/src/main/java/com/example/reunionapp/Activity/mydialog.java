package com.example.reunionapp.Activity;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.reunionapp.Model.Reunion;
import com.example.reunionapp.R;
import com.example.reunionapp.util.DateUtils;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class mydialog extends AppCompatDialogFragment
{
     TextInputLayout mDateFiltre;
     static String mDateF;
     static String mSalleF;
    TextInputLayout mSalleFiltre;
  MyDialogListener listener;
   ImageView mCancelDateFiltre;
    ImageView mcancel_salle_filtre;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener=(MyDialogListener) context;

    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_filtre,null);

        builder.setView(view)
                .setTitle("Filtrer les reunions ")
                .setIcon(R.drawable.ic_baseline_filter_alt_24)
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // dialog.dismiss();
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String date_filtre=mDateFiltre.getEditText().getText().toString();
                        String salle_filtre=mSalleFiltre.getEditText().getText().toString();
                        listener.applyTexts(date_filtre,salle_filtre);

                    }
                })
        .setNeutralButton("DISACTIVER TOUS LES FILTRES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.applyTexts("","");

            }
        })
        ;

        mDateFiltre=view.findViewById(R.id.date_filtre);
        mSalleFiltre=view.findViewById(R.id.salle_filtre);
        mCancelDateFiltre=view.findViewById(R.id.cancel_date_filtre);
        mcancel_salle_filtre=view.findViewById(R.id.cancel_salle_filtre);

        mDateFiltre.getEditText().setText(mDateF);
        mSalleFiltre.getEditText().setText(mSalleF);

        if(mDateFiltre.getEditText().getText().length()>0) {
            mCancelDateFiltre.setVisibility(View.VISIBLE);
            mCancelDateFiltre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDateFiltre.getEditText().setText("");
                    mDateF = "";
                }
            });
        }
        if(mSalleFiltre.getEditText().getText().length()>0) {
            mcancel_salle_filtre.setVisibility(View.VISIBLE);
            mcancel_salle_filtre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSalleFiltre.getEditText().setText("");
                    mSalleF="";
                }
            });
        }

        mDateFiltre.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelectDate();
            }
        });

        mSalleFiltre.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateMultipleChoicelistSalleAlertDialog(mSalleFiltre.getEditText().getText().toString());
                //mSalleFiltre.setText("Salle A");
            }
        });

        return builder.create();


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
                c.set(Calendar.YEAR,year);//year
                c.set(Calendar.MONTH,monthOfYear);//MONTH
                c.set(Calendar.DAY_OF_MONTH,dayOfMonth);//day

                try {
                    mDateFiltre.getEditText().setText(DateUtils.convertDateToString(c.getTime()));
                    mDateF=DateUtils.convertDateToString(c.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //  List<Reunion> list= mReunionApiService.filtreByDateAndRoom(year,monthOfYear,dayOfMonth,"Salle A");
              //  System.out.println("size onDateSet "+list.size());
              //  mRecyclerView.setAdapter(new myRecycleViewAdapter(list));
                //    System.out.println(new Date(1626557205000L));
                //  System.out.println(c.getTime().getTime());
            }
        };

        DatePickerDialog datePickerDialog  = new DatePickerDialog(getContext(),
                dateSetListener, lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth);
        // Show
        datePickerDialog.show();

    }

    public void CreateMultipleChoicelistSalleAlertDialog(String salles)
    {
        // listItems est la liste de tous mes item : items array,
        Resources resources=getResources();
        String[]  listItems = resources.getStringArray(R.array.room);

        // checkedItems: boolean array
        boolean[]  checkedItems = new boolean[listItems.length];
        //   mUserItems: tableau des positions
        ArrayList<Integer> mUserItems = new ArrayList<>();
        System.out.println(salles) ;
        for(int i=0;i<salles.split(",").length;i++)
        {

            for(int j=0;j<listItems.length;j++)
            {
                System.out.println(listItems[j].equals(salles.split(",")[i]));
                if(listItems[j].equals(salles.split(",")[i]))
                {
                    mUserItems.add(j);
                    checkedItems[j]=true;
                }
            }
        }


        //to define alertdialog.builder
        // We give a title for the dialog and make sure to add dialog.setCancelable(false)
        // to prevent user from closing the dialog when clicking in any area outside the dialog

        androidx.appcompat.app.AlertDialog.Builder mBuilder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        mBuilder.setTitle(R.string.dialog_salle_title);
        mBuilder.setIcon(R.drawable.salle);
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


        //Calldialog.setPositiveButton and give it a label,
        // inside this button is where we are going to add the checked items
        // to the list and make sure that those are item not duplicated
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
                mSalleFiltre.getEditText().setText(item);
                mSalleF=item;
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
                    mSalleFiltre.getEditText().setText("");
                }
            }
        });
        //Call the dialog.show() to show the dialog
        androidx.appcompat.app.AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }
    public interface MyDialogListener {
        void applyTexts(String date_filtre,String salle_filtre);
    }

}
