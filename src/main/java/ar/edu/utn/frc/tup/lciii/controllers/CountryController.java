package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.exception.CountryNotFoundException;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import ar.edu.utn.frc.tup.lciii.service.parcialService.ParcialService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/countries")
public class CountryController {
    @Autowired
    private ParcialService parcialService;

    @GetMapping()
    public ResponseEntity<List<CountryDTO>> getAllCountries(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code) {
        List<CountryDTO> countries;
        if (name != null) {
            countries = parcialService.getCountriesByName(name);
        } else if (code != null) {
            countries = parcialService.getCountriesByCode(code);
        } else {
            countries = parcialService.getAllCountrys();
        }
        if (countries.isEmpty()) {
            throw new CountryNotFoundException("No countries found");
        }
        return ResponseEntity.ok(countries);
    }

    @GetMapping("/{continent}/continent")
    public ResponseEntity<List<Country>> getAllCountriesPerContinent(@PathVariable String continent) {
        List<Country> countries=this.parcialService.getCountryPerContinent(continent);
        if (countries.isEmpty()) {
            throw new CountryNotFoundException("No countries per continent found");
        }
        return ResponseEntity.ok(countries);
    }
    @GetMapping("/{language}/language")
    public ResponseEntity<List<Country>> getAllCountriesPerIdiom(@PathVariable String language) {
        List<Country> countries=this.parcialService.getCountryPerIdiom(language);
        if (countries.isEmpty()) {
            throw new CountryNotFoundException("No countries per idiom found");
        }
        return ResponseEntity.ok(countries);
    }

}