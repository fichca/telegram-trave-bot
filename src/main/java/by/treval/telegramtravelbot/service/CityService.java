package by.treval.telegramtravelbot.service;

import by.treval.telegramtravelbot.entity.City;
import by.treval.telegramtravelbot.entity.Place;

import java.util.List;

public interface CityService {

    boolean createCity(City city);

    boolean updateCityName(City city, String newName);

    boolean deleteCity(City city);

    boolean addPlaceByCityName(Place place, String city);

    boolean deletePlaceByCityName(Place place, String city);

    List<Place> getAllPlaceByCity(String city);

    List<City> getAll();

}
