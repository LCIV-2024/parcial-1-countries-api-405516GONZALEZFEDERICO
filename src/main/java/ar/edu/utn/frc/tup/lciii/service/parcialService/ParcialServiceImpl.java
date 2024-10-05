package ar.edu.utn.frc.tup.lciii.service.parcialService;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class ParcialServiceImpl implements ParcialService {

    @Autowired
    private CountryService countryService;


    //lo mapeo asi por que no me reconoce el meotodo
    //  private CountryDTO mapToDTO(Country country) {
    //  return new CountryDTO(country.getCode(), country.getName());
    //   }

    @Override
    public List<CountryDTO> getAllCountrys() {
        List<Country>countries =this.countryService.getAllCountries();
        List<CountryDTO>countryDTOS=new ArrayList<>();
        for (Country country : countries) {
            CountryDTO dto = new CountryDTO();
            dto.setName(country.getName());
            dto.setCode(country.getCode());
            countryDTOS.add(dto);
        }
        return countryDTOS;
    }

    @Override
    public List<CountryDTO> getCountriesByName(String name) {
        List<Country>countries =this.countryService.getAllCountries();
        List<CountryDTO>countryDTOS=new ArrayList<>();

        for (Country country : countries) {
            if (country.getName().equals(name)){
                CountryDTO dto = new CountryDTO();
                dto.setName(country.getName());
                dto.setCode(country.getCode());
                countryDTOS.add(dto);
            }
        }
        return countryDTOS;
    }

    @Override
    public List<CountryDTO> getCountriesByCode(String code) {
        List<Country>countries =this.countryService.getAllCountries();
        List<CountryDTO>countryDTOS=new ArrayList<>();

        for (Country country : countries) {
            if (country.getName().equals(code.toUpperCase())){
                CountryDTO dto = new CountryDTO();
                dto.setName(country.getName());
                dto.setCode(country.getCode());
                countryDTOS.add(dto);
            }
        }
        return countryDTOS;
    }

    @Override
    public List<Country> getCountryPerContinent(String continent) {
        List<Country> allCountries = this.countryService.getAllCountries();
        List<Country> countriesInContinent = new ArrayList<>();
        for (Country country : allCountries) {
            if (country.getRegion().equals(continent)) {
                countriesInContinent.add(country);
            }
        }
        return countriesInContinent;
    }

    @Override
    public List<Country> getCountryPerIdiom(String idiom) {
        List<Country> allCountries = this.countryService.getAllCountries();
        List<Country> countriesPerIdiom = new ArrayList<>();
        for (Country country : allCountries) {
            Map<String, String> languages = country.getLanguages();
            if (languages != null && languages.containsValue(idiom)) {
                countriesPerIdiom.add(country);
            }
        }
        return countriesPerIdiom;
    }


}
