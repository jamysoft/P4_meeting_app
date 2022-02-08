package com.example.reunionapp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class DateUtils
{
    static public Date convertStringToDate(String sDate,String pattern) throws ParseException {
     // SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyyhh:mm");
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
      //  SimpleDateFormat formatter=new SimpleDateFormat( "dd-MM-yyyy' à'HH'h'mm");
     //  System.out.println(formatter.format(sDate));
        return formatter.parse(sDate);
    }
    static public String convertDateToString(Date date) throws ParseException {
        //AFFICHER LA DATE EN RESPECTANT UN PATERN
       SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
     //   SimpleDateFormat formatter=new SimpleDateFormat( "dd-MM-yyyy' à 'HH:mm");
       // System.out.println(formatter.format(date));
       return formatter.format(date);
    }
    static public String GetTimeFromDate(Date date) throws ParseException
    {
        SimpleDateFormat  formater = new SimpleDateFormat("HH:mm");
       // System.out.println(formater.format(date));
        return formater.format(date);
    }


}
