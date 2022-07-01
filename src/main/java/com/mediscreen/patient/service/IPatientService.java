package com.mediscreen.patient.service;

import com.mediscreen.patient.model.Patient;

import java.util.List;
import java.util.Optional;

public interface IPatientService {

    List<Patient> getAllPatient();

    Optional<Patient> getPatientById(Integer userId);

    Optional<Patient> getPatientByFirstNameAndLastName(String firstName, String lastName);
}
