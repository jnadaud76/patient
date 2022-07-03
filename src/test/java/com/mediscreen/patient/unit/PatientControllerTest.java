package com.mediscreen.patient.unit;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.patient.controller.PatientController;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.IPatientService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest(controllers = PatientController.class)
class PatientControllerTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPatientService patientService;

    @Test
    void TestGetAllPatient() throws Exception {
        List<Patient> patients = new ArrayList<>();
        Patient patient = new Patient(1, "test", "test", LocalDateTime.now().minusYears(20), 'M',
                "12 rue du test", "555-555-555");
        patients.add(patient);
        when(patientService.getAllPatient()).thenReturn(patients);
        mockMvc.perform(get("/api/patient/patients"))
                .andExpect(status().isOk());
    }

    @Test
    void TestGetPatientById() throws Exception {
        Patient patient = new Patient(1, "test", "test", LocalDateTime.now().minusYears(20), 'M',
              "12 rue du test", "555-555-555");
       when(patientService.getPatientById(1)).thenReturn(Optional.of(patient));
        mockMvc.perform(get("/api/patient/patientbyid").queryParam("patientId", "1"))
                .andExpect(status().isOk());

    }

    @Test
    void TestGetPatientByIdWithBadId() throws Exception {
        when(patientService.getPatientById(1)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/patient/patientbyid").queryParam("patientId", "1"))
                .andExpect(status().isNotFound());

    }

    @Test
    void TestGetPatientByFirstNameAndLastName() throws Exception {
        Patient patient = new Patient(1, "test", "test", LocalDateTime.now().minusYears(20), 'M',
                "12 rue du test", "555-555-555");
        when(patientService.getPatientByFirstNameAndLastName("test","test")).thenReturn(Optional.of(patient));
        mockMvc.perform(get("/api/patient/patient").queryParam("firstName", "test").queryParam("lastName", "test"))
                .andExpect(status().isOk());

    }

    @Test
    void TestGetPatientByBadFirstNameAndBadLastName() throws Exception {
        when(patientService.getPatientByFirstNameAndLastName("test1","test1")).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/patient/patient").queryParam("firstName", "test1").queryParam("lastName", "test1"))
                .andExpect(status().isNotFound());

    }
}
