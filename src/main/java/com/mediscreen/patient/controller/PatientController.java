package com.mediscreen.patient.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mediscreen.patient.dto.PatientFullDto;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.IPatientService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("API for patient CRUD operations.")
@CrossOrigin("http://localhost:4200")
@RequestMapping(value = "api/patient")
@RestController
public class PatientController {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

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

    @ApiOperation(value = "Update one patient.")
    @PutMapping(value="/patientupdate")
    public ResponseEntity<PatientFullDto> updatePatient (@Valid @RequestBody PatientFullDto patientUpdateDto) {
        Patient patient = patientService.updatePatient(OBJECT_MAPPER.convertValue(patientUpdateDto, Patient.class));
        if(patient!=null) {
            LOGGER.info("Patient successfully update - code : {}", HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK).body(patientUpdateDto);
        } else {
            LOGGER.error("Patient can't be update because don't exist : {}", HttpStatus.BAD_REQUEST);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @ApiOperation(value = "Create one patient.")
    @PostMapping(value="/addpatient")
    public ResponseEntity<PatientFullDto> createPatient (@Valid @RequestBody PatientFullDto patientFullDto) {
       if(!patientService.getPatientByFirstNameAndLastName(patientFullDto.getFirstName(),
                patientFullDto.getLastName()).isPresent()) {
            patientService.savePatient(OBJECT_MAPPER.convertValue(patientFullDto, Patient.class));
            LOGGER.info("Patient successfully create - code : {}", HttpStatus.CREATED);
            return ResponseEntity.status(HttpStatus.CREATED).body(patientFullDto);
        } else {
            LOGGER.error("Patient can't be create, already exist - code : {}", HttpStatus.BAD_REQUEST);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
