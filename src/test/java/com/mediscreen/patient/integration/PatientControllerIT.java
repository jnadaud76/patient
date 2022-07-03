package com.mediscreen.patient.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.patient.controller.PatientController;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.IPatientService;

import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import javax.print.attribute.standard.Media;

@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerIT {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    void TestGetAllPatient() throws Exception {
       mockMvc.perform(get("/api/patient/patients"))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    void TestGetPatientById(int ints) throws Exception {
          mockMvc.perform(get("/api/patient/patientbyid").queryParam("patientId", String.valueOf(ints)))
                .andExpect(status().isOk());

    }

    @Test
    void TestGetPatientByIdWithBadId() throws Exception {
         mockMvc.perform(get("/api/patient/patientbyid").queryParam("patientId", "5"))
                .andExpect(status().isNotFound());

    }

    @Test
    void TestGetPatientByFirstNameAndLastName() throws Exception {
       mockMvc.perform(get("/api/patient/patient").queryParam("firstName", "John").queryParam("lastName", "Doe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("@.firstName", is("John")))
               .andExpect(jsonPath("@.lastName", is("Doe")));

    }

    @Test
    void TestGetPatientByBadFirstNameAndBadLastName() throws Exception {
        mockMvc.perform(get("/api/patient/patient").queryParam("firstName", "test").queryParam("lastName", "test"))
                .andExpect(status().isNotFound());

    }
}
