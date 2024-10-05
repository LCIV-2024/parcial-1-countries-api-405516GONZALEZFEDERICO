package ar.edu.utn.frc.tup.lciii.controllers;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.edu.utn.frc.tup.lciii.controllers.CountryController;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.exception.CountryNotFoundException;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.service.parcialService.ParcialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
@WebMvcTest(CountryController.class)
class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParcialService parcialService;

    private List<CountryDTO> sampleCountries;

    @BeforeEach
    void setUp() {
        sampleCountries = Arrays.asList(
                new CountryDTO("South Georgia", "SGS"),
                new CountryDTO("Grenada", "GRD"),
                new CountryDTO("Switzerland", "CHE"),
                new CountryDTO("Sierra Leone", "SLE"),
                new CountryDTO("Hungary", "HUN")
        );
    }

    @Test
    void getAllCountries_ShouldReturnAllCountries() throws Exception {
        when(parcialService.getAllCountrys()).thenReturn(sampleCountries);

        mockMvc.perform(get("/api/countries"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("South Georgia"))
                .andExpect(jsonPath("$[0].code").value("SGS"))
                .andExpect(jsonPath("$[4].name").value("Hungary"))
                .andExpect(jsonPath("$[4].code").value("HUN"));
    }

    @Test
    void getCountriesByName_ShouldReturnMatchingCountries() throws Exception {
        List<CountryDTO> matchingCountries = Collections.singletonList(new CountryDTO("Switzerland", "CHE"));
        when(parcialService.getCountriesByName("Switzerland")).thenReturn(matchingCountries);

        mockMvc.perform(get("/api/countries").param("name", "Switzerland"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Switzerland"))
                .andExpect(jsonPath("$[0].code").value("CHE"));
    }


    @Test
    void getCountriesByCode_ShouldReturnMatchingCountries() throws Exception {
        List<CountryDTO> matchingCountries = Collections.singletonList(new CountryDTO("Grenada", "GRD"));
        when(parcialService.getCountriesByCode("GRD")).thenReturn(matchingCountries);

        mockMvc.perform(get("/api/countries").param("code", "GRD"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Grenada"))
                .andExpect(jsonPath("$[0].code").value("GRD"));
    }

    @Test
    void getAllCountries_WhenNoCountriesFound_ShouldReturnNotFoundWithErrorDetails() throws Exception {
        when(parcialService.getAllCountrys()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/countries"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("No countries found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }


    @Test
    void getAllCountriesPerContinent_ShouldReturnMatchingCountries() throws Exception {
        Country switzerland = new Country();
        switzerland.setName("Switzerland");
        switzerland.setCode("CHE");

        Country hungary = new Country();
        hungary.setName("Hungary");
        hungary.setCode("HUN");

        List<Country> europeanCountries = Arrays.asList(switzerland, hungary);

        when(parcialService.getCountryPerContinent("Europe")).thenReturn(europeanCountries);

        mockMvc.perform(get("/api/countries/Europe/continent"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Switzerland"))
                .andExpect(jsonPath("$[1].name").value("Hungary"));
    }

    @Test
    void getAllCountriesPerIdiom_WhenNoCountriesFound_ShouldReturnNotFoundWithErrorDetails() throws Exception {
        when(parcialService.getCountryPerIdiom("Klingon")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/countries/Klingon/language"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("No countries per idiom found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }


}