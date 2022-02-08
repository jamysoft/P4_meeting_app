package com.example.reunionapp.Service;

import android.widget.Toast;

import com.example.reunionapp.Model.Reunion;
import com.example.reunionapp.util.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReunionApiService implements  InterfaceReunionApiService {
    private List<Reunion> reunions = ReunionGenrerator.generateReunions();
    public ReunionApiService() {
        super();
    }

    @Override
    public List<Reunion> getReunion() {
        return reunions;
    }

    @Override
    public void addReunion(Reunion reunion) {
        reunions.add(reunion);
    }

    @Override
    public void deleteReunion(Reunion reunion) {
        reunions.remove(reunion);
    }


    @Override
    public List<Reunion> filterbydate(String date) throws ParseException {

        List<Reunion> resultat = new ArrayList<>();
        Date date_filtre = DateUtils.convertStringToDate(date, "dd-MM-yyyy");
        Calendar call1 = Calendar.getInstance();
        call1.setTime(date_filtre);
        int year = call1.get(Calendar.YEAR);
        int monthOfYear = call1.get(Calendar.MONTH);
        int dayOfMonth = call1.get(Calendar.DAY_OF_MONTH);

        System.out.println("CE qui est recupérer YEAR " + year + " monthOfYear " + monthOfYear + " dayOfMonth " + dayOfMonth);

        Calendar call = Calendar.getInstance();
        for (int i = 0; i < reunions.size(); i++) {
            call.setTime(reunions.get(i).getDate());
            if (call.get(Calendar.YEAR) == year && call.get(Calendar.MONTH) == monthOfYear && call.get(Calendar.DAY_OF_MONTH) == dayOfMonth) {
                resultat.add(reunions.get(i));
                System.out.println("filter par date" + i);
            }
        }
        return resultat;
    }

    @Override
    public List<Reunion> filtreByRooms(String[] salles)
    {

        List<Reunion> resultat = new ArrayList<>();
        System.out.println("nombre de Salle ="+salles.length);

        for(int j=0;j<salles.length;j++) {
            System.out.println("la salle  " +j+" "+ salles[j]);
        }

        for(int j=0;j<salles.length;j++)
        {
            for ( int i=0;i<reunions.size();i++)
            {
                String item_salle=reunions.get(i).getRoom();

                    System.out.println("JE COMPARE "+salles[j]+ " AVEC "+reunions.get(i).getRoom());
                    if(reunions.get(i).getRoom().equals(salles[j]))
                    {
                        System.out.println("add="+ reunions.get(i).toString());
                        resultat.add(reunions.get(i));
                    }
            }
        }
        System.out.println("RESULTAT="+ resultat.size());
        return resultat;

    }

    @Override
    public List<Reunion> filtreByDateAndRooms(String date,
                                             String[] salles) throws ParseException {

        List<Reunion> resultat=new ArrayList<>();
        String salleString="";
        for(int j=0;j<salles.length;j++) {
            salleString=salleString+salles[j];
        }
        System.out.println("salle  RECUPERER"+salleString);
        System.out.println("DATE RECUPERER "+date.length()+" ET SALLE RECUPERER "+salles.length);

        //CAS FILTRE BY DATE ET BY SALLE
        if(date.length()!=0 && salleString!="") {
            System.out.println("filter par salle et par date ");
            Date date_filtre = DateUtils.convertStringToDate(date, "dd-MM-yyyy");
            Calendar call1 = Calendar.getInstance();
            call1.setTime(date_filtre);
            int year = call1.get(Calendar.YEAR);
            int monthOfYear = call1.get(Calendar.MONTH);
            int dayOfMonth = call1.get(Calendar.DAY_OF_MONTH);

            System.out.println("CE qui est recupérer YEAR " + year + " monthOfYear " + monthOfYear + " dayOfMonth " + dayOfMonth);


            Calendar call = Calendar.getInstance();
            for(int j=0;j<salles.length;j++) {
                for (int i = 0; i < reunions.size(); i++) {
                    call.setTime(reunions.get(i).getDate());
                    if (reunions.get(i).getRoom().equals(salles[j]) && call.get(Calendar.YEAR) == year && call.get(Calendar.MONTH) == monthOfYear && call.get(Calendar.DAY_OF_MONTH) == dayOfMonth) {
                        resultat.add(reunions.get(i));
                        System.out.println("filter par date" + i);
                    }
                }
            }
        }
        else if(date.length()==0 )
            {
                System.out.println("filter par salle seulement");
                resultat=filtreByRooms(salles);
            }
        else
           {
                System.out.println("filter par date seulement" );
                resultat=filterbydate(date);
            }
        return resultat;
    }

    @Override
    public  Reunion getReunionById(Long Id)
    {
        Reunion r=new Reunion();
        for (Reunion item : reunions)
        {
            if (item.getId()==Id) {
                r=item;
                break;
            }
        }
        return r;
    }
}
