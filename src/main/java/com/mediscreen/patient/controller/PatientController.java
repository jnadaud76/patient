package com.mediscreen.patient.controller;

import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.IPatientService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class PatientController {

    private IPatientService patientService;

    public PatientController(IPatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/patients")
    public ResponseEntity<List<Patient>> getAllPatient() {
        List<Patient> patients = patientService.getAllPatient();
        if (!patients.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(patientService.getAllPatient());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/patientbyid")
    public ResponseEntity<Patient> getPatientById(@RequestParam Integer userId) {
        Optional<Patient> patientOptional = patientService.getPatientById(userId);
        return patientOptional.map(patient -> ResponseEntity.status(HttpStatus.OK).body(patient))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    @GetMapping("/patient")
    public ResponseEntity<Patient> getPatientByFirstNameAndLastName(@RequestParam String firstName, @RequestParam String lastName) {
        Optional<Patient> patientOptional = patientService.getPatientByFirstNameAndLastName(firstName, lastName);
        return patientOptional.map(patient -> ResponseEntity.status(HttpStatus.OK).body(patient))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }
}
