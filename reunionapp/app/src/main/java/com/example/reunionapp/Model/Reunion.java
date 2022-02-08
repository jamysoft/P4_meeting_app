package com.example.reunionapp.Model;

import com.example.reunionapp.util.DateUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class Reunion
{
    private long id;
    private String subject;
    private String room;
    private Date date;//jour et heure de la reunion dd/MM/yyyy HH:mm
    private List<String> listparticipants;

    public Reunion(long id,String subject, String room, Date date,List<String> listparticipants) {

        this.id=id;
        this.subject = subject;
        this.room = room;
        this.date = date;
        this.listparticipants = listparticipants;
    }
    public Reunion() {

    }

    public String getSubject() {
        return subject;
    }

    public String getRoom() {
        return room;
    }

    public Date getDate() {
        return date;
    }

    public List<String> getListparticipants() {
        return listparticipants;
    }

    public long getId() {
        return id;
    }


    @Override
    public String toString()   {

        String dateString= "";
        String time="";
        try {
            dateString = DateUtils.convertDateToString(date);
            time=DateUtils.GetTimeFromDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //  return subject+"-"+hour+"-"+dateString+" "+place;

        return subject+"-"+dateString+" à "+time+"-"+room;
    }
    public String GetListparticipantsToString() {
        String res="";

       for(int i=0;i<listparticipants.size();i++)
        {
            if(i==0)
            {
                res+=listparticipants.get(i);
            }
            else
            {
                res=res+","+listparticipants.get(i);
            }

        }
       return res;
    }

}
