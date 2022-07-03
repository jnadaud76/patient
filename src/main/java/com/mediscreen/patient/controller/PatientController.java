package com.mediscreen.patient.controller;

import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.IPatientService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("API for patient CRUD operations.")
@CrossOrigin("http://localhost:4200")
@RequestMapping(value = "api/patient")
@RestController
public class PatientController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientController.class);

    private final IPatientService patientService;

    public PatientController(IPatientService patientService) {
        this.patientService = patientService;
    }

    @ApiOperation(value = "Retrieve all patient.")
    @GetMapping("/patients")
    public ResponseEntity<List<Patient>> getAllPatient() {
        List<Patient> patients = patientService.getAllPatient();
        if (!patients.isEmpty()) {
            LOGGER.info("Persons successfully found - code : {}", HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(patientService.getAllPatient());
        } else {
            LOGGER.error("Persons not found - code : {}", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ApiOperation(value = "Retrieve one patient by id.")
    @GetMapping("/patientbyid")
    public ResponseEntity<Patient> getPatientById(@RequestParam Integer patientId) {
        Optional<Patient> patientOptional = patientService.getPatientById(patientId);
        if (patientOptional.isPresent()) {
            LOGGER.info("Person successfully found - code : {}", HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK).body(patientOptional.get());
        } else {
            LOGGER.error("Person not found - code : {}", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ApiOperation(value = "Retrieve one patient by firstName and lastName.")
    @GetMapping("/patient")
    public ResponseEntity<Patient> getPatientByFirstNameAndLastName(@RequestParam String firstName, @RequestParam String lastName) {
        Optional<Patient> patientOptional = patientService.getPatientByFirstNameAndLastName(firstName, lastName);
        if (patientOptional.isPresent()) {
            LOGGER.info("Person successfully found - code : {}", HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK).body(patientOptional.get());
        } else {
            LOGGER.error("Person not found - code : {}", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
}
