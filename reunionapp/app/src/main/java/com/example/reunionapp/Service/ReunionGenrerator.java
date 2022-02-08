package com.example.reunionapp.Service;

import com.example.reunionapp.Model.Reunion;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public abstract class ReunionGenrerator
{
      static Date date=new Date();

    public static List<Reunion> DUMMY_REUNIONS = Arrays.asList(


            new Reunion(1,"Reunion A","Salle A",date
                    ,Arrays.asList(
                            "jamila.elghiyati@gmail.com","j.elghiyati@gmail.com")
            ),
              new Reunion(2,"Reunion B","Salle B",date
                                  ,Arrays.asList(
                                  "jamila.elghiyati@gmail.com","j.elghiyati@gmail.com")
            ),
                    new Reunion(3,"Reunion C","Salle C",date
                                        ,Arrays.asList(
                                        "jamila.elghiyati@gmail.com","j.elghiyati@gmail.com")
            ),
                    new Reunion(4,"Reunion D","Salle D",date
                                        ,Arrays.asList(
                                        "jamila.elghiyati@gmail.com","j.elghiyati@gmail.com")
            )
             ,new Reunion(5,"Reunion A","Salle A",date
                                 ,Arrays.asList(
                                 "jamila.elghiyati@gmail.com","j.elghiyati@gmail.com")
            ),
                    new Reunion(6,"Reunion B","Salle B",date
                                        ,Arrays.asList(
                                        "jamila.elghiyati@gmail.com","j.elghiyati@gmail.com")
            ),
                    new Reunion(7,"Reunion C","Salle C",date
                                        ,Arrays.asList(
                                        "jamila.elghiyati@gmail.com","j.elghiyati@gmail.com")
            ),
                    new Reunion(8,"Reunion D","Salle D",date
                                        ,Arrays.asList(
                                        "jamila.elghiyati@gmail.com","j.elghiyati@gmail.com")
            )
            , new Reunion(9,"Reunion A","Salle A",date
                    ,Arrays.asList(
                    "jamila.elghiyati@gmail.com","j.elghiyati@gmail.com")
            ),
            new Reunion(10,"Reunion B","Salle B",date
                    ,Arrays.asList(
                    "jamila.elghiyati@gmail.com","j.elghiyati@gmail.com")
            ),
            new Reunion(11,"Reunion C","Salle C",date
                    ,Arrays.asList(
                    "jamila.elghiyati@gmail.com","j.elghiyati@gmail.com")
            ),
            new Reunion(12,"Reunion D","Salle D",date
                    ,Arrays.asList(
                    "jamila.elghiyati@gmail.com","j.elghiyati@gmail.com")
            )
    );

    static List<Reunion> generateReunions() {

        return new ArrayList<>(DUMMY_REUNIONS);
    }
}
