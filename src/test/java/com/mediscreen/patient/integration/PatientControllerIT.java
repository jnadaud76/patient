package com.mediscreen.patient.integration;

import static org.hamcrest.CoreMatchers.is;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mediscreen.patient.dto.PatientFullDto;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.IPatientService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerIT {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

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

    @Test
    void TestUpdatePatient() throws Exception {
        PatientFullDto patientUpdateDto = new PatientFullDto(2, "test2", "test2", LocalDateTime.now().minusYears(30), 'F',
                "77 rue du test", "550-550-550");
        String patientAsString = OBJECT_MAPPER.writeValueAsString(patientUpdateDto);
        mockMvc.perform(put("/api/patient/patient/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientAsString))
                .andExpect(status().isOk());


    }

    @Test
    void TestUpdatePatientWithBadId() throws Exception {
        PatientFullDto patientUpdateDto = new PatientFullDto(28, "test2", "test2", LocalDateTime.now().minusYears(30), 'F',
                "77 rue du test", "550-550-550");
        String patientAsString = OBJECT_MAPPER.writeValueAsString(patientUpdateDto);
        mockMvc.perform(put("/api/patient/patient/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientAsString))
                .andExpect(status().isBadRequest());


    }
}
