package ar.edu.utn.frc.tup.lciii.service.parcialService;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ParcialService {
    List<CountryDTO> getAllCountrys( );

    List<CountryDTO> getCountriesByName(String name);
    List<CountryDTO> getCountriesByCode(String code);

    List<Country>getCountryPerContinent(String continent);
    List<Country>getCountryPerIdiom(String idiom);

}
