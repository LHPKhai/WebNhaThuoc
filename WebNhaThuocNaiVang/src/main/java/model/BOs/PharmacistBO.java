/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.BOs;

import model.entities.Pharmacist;

/**
 *
 * @author hello
 */
public class PharmacistBO {
    public void prescribe(Pharmacist pharmacist) {
        System.out.println("Prescription created by pharmacist: " + pharmacist.getUsername());
    }

    public void consult(Pharmacist pharmacist) {
        System.out.println("Consultation completed by pharmacist: " + pharmacist.getUsername());
    }

    public void monitorPatient(Pharmacist pharmacist) {
        System.out.println("Patient monitored by pharmacist: " + pharmacist.getUsername());
    }
}
