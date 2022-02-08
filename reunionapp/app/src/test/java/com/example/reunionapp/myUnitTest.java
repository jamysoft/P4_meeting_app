package com.example.reunionapp;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.reunionapp.DI.DI;
import com.example.reunionapp.Model.Reunion;
import com.example.reunionapp.Service.InterfaceReunionApiService;
import com.example.reunionapp.Service.ReunionGenrerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class myUnitTest {
    private InterfaceReunionApiService service;

    @Before
    public void setup() {

        service = DI.getNewInstanceApiService();
    }
    @Test
    public void addReunionWithSuccess() {
        List<Reunion> reunions = service.getReunion();
       Date date= Calendar.getInstance().getTime();
       Reunion reunionToAdd=new Reunion(13,"Reunion D","Salle E",date
               , Arrays.asList(
               "ja.elghiyati@gmail.com","j.eti@gmail.com")
       );
        service.addReunion(reunionToAdd);
        assertTrue(service.getReunion().contains(reunionToAdd));
    }
    @Test
    public void getReunionWithSuccess() {
        List<Reunion> reunions = service.getReunion();
        List<Reunion> expectedReunions = ReunionGenrerator.DUMMY_REUNIONS;
        assertThat(reunions, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedReunions.toArray()));
    }

    @Test
    public void deleteReunionWithSuccess() {
        Reunion reunionToDelete = service.getReunion().get(1);
        service.deleteReunion(reunionToDelete);
        assertFalse(service.getReunion().contains(reunionToDelete));
    }

    @Test
    public void getReunionByIdWithSuccess()
    {
        Reunion neighbour= service.getReunionById((long)1);
        Reunion expectedNeighbour=ReunionGenrerator.DUMMY_REUNIONS.get(0);
        assertEquals(neighbour,expectedNeighbour);
    }

}