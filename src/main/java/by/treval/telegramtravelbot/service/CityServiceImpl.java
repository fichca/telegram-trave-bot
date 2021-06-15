package by.treval.telegramtravelbot.service;

import by.treval.telegramtravelbot.entity.City;
import by.treval.telegramtravelbot.entity.Place;
import by.treval.telegramtravelbot.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;


    @Override
    public boolean createCity(City city) {
        if (!cityRepository.existsByName(city.getName())) {
            try {
                cityRepository.save(city);
            } catch (ValidationException e) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean updateCityName(City city, String newName) {
        String cityName = city.getName();
        if (cityRepository.existsByName(cityName)){
            City byName = cityRepository.getByName(cityName);
            byName.setName(newName);
            cityRepository.save(byName);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteCity(City city) {
        String cityName = city.getName();
        if (cityRepository.existsByName(cityName)){
            City byName = cityRepository.getByName(cityName);
            cityRepository.delete(byName);
            return true;
        }
        return false;
    }


    @Override
    public boolean addPlaceByCityName(Place place, String city) {
        if (cityRepository.existsByName(city)){
            City byName = cityRepository.getByName(city);
            List<Place> places = byName.getPlaces();
            for (Place place1 : places) {
                if (place1.getName().equals(place.getName())){
                    return false;
                }
            }
            places.add(place);
            cityRepository.save(byName);
            return true;
        }
        return false;
    }

    @Override
    public boolean deletePlaceByCityName(Place place, String city) {
        if (cityRepository.existsByName(city)){
            City byName = cityRepository.getByName(city);
            List<Place> places = byName.getPlaces();
            for (Place place1 : places) {
                if (place1.getName().equals(place.getName())){
                    places.remove(place1);
                    cityRepository.save(byName);
                }
            }
        }
        return false;
    }

    @Override
    public List<Place> getAllPlaceByCity(String city) {
        if (cityRepository.existsByName(city)){
            City byName = cityRepository.getByName(city);
            return byName.getPlaces();
        }
        return new ArrayList<>();
    }

    @Override
    public List<City> getAll() {
        return cityRepository.findAll();
    }
}
