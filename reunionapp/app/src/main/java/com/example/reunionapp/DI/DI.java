package com.example.reunionapp.DI;

import com.example.reunionapp.Service.InterfaceReunionApiService;
import com.example.reunionapp.Service.ReunionApiService;

/**
 * Dependency injector to get instance of services
 */
public class DI {

    private static InterfaceReunionApiService service = new ReunionApiService();

    public static InterfaceReunionApiService getReunionApiService() {
        return service;
    }

    public static InterfaceReunionApiService getNewInstanceApiService() {
        return new ReunionApiService();
    }
}
