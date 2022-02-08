package com.example.reunionapp.Service;

import com.example.reunionapp.Model.Reunion;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface InterfaceReunionApiService
{
    List<Reunion> getReunion();
    void addReunion(Reunion reunion);
    void deleteReunion(Reunion reunion);
    List<Reunion> filterbydate(String date)  throws ParseException ;
    List<Reunion> filtreByRooms(String[] salles);
    List<Reunion> filtreByDateAndRooms(String date,
                                       String[] salles) throws ParseException;
     Reunion getReunionById(Long Id);


}


