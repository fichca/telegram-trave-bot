package by.treval.telegramtravelbot.service;


import by.treval.telegramtravelbot.comparator.PlaceComparator;
import by.treval.telegramtravelbot.entity.City;
import by.treval.telegramtravelbot.entity.Place;
import by.treval.telegramtravelbot.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static by.treval.telegramtravelbot.constant.ConstantsBotMessage.*;


@Service
public class BotServiceImpl implements BotService {

    @Autowired
    private CityRepository cityRepository;

    private final List<String> SYSTEM_COMMAND = new ArrayList<>();
    private final List<String> SYSTEM_COMMAND_WITH_PARAMETER = new ArrayList<>();

    {
        SYSTEM_COMMAND.add(COMMAND_START);
        SYSTEM_COMMAND.add(COMMAND_HELP);
        SYSTEM_COMMAND.add(COMMAND_ALL_CITIES);
        SYSTEM_COMMAND_WITH_PARAMETER.add(COMMAND_TOP);
        SYSTEM_COMMAND_WITH_PARAMETER.add(COMMAND_BAD);
        SYSTEM_COMMAND_WITH_PARAMETER.add(COMMAND_RATING);
    }

    @Override
    public String getAnswer(String text) {
        if (text == null) {
            return ERROR_NO_CITY;
        } else if (text.trim().isEmpty()) {
            return ERROR_NO_CITY;
        }
        String[] splitText = text.split("\\s");
        String command = splitText[0];
        if (SYSTEM_COMMAND.contains(command)) {
            return getAnswerByCommand(command);
        } else if (SYSTEM_COMMAND_WITH_PARAMETER.contains(command)) {
            StringBuilder stringBuilder = new StringBuilder(text);
            String substring = stringBuilder.substring(command.length());
            return getAnswerByCommandWithParameter(command, substring.trim());
        } else {
            return getAnswerByCityName(text);
        }
    }

    private String getAnswerByCityName(String city) {
        if (cityRepository.existsByName(city)) {
            City cityByName = cityRepository.findCityByName(city);
            List<Place> places = cityByName.getPlaces();
            String placesString = getPlacesString(places);
            return city + placesString;
        } else {
            return ERROR_CITY_NOT_FOUND;
        }
    }

    private String getAnswerByCommand(String command) {
        switch (command) {
            case COMMAND_START:
                return STAT_MESSAGE;
            case COMMAND_HELP:
                return HELP_MESSAGE;
            case COMMAND_ALL_CITIES:
                List<String> allCitiesName = cityRepository.getAllCitiesName();
                return getCitiesString(allCitiesName);
            default:
                return ERROR;
        }
    }


    private String getAnswerByCommandWithParameter(String command, String city) {
        switch (command) {
            case COMMAND_TOP:
                return getListPlaceByRating(city, 6, 7, 8, 9, 10);
            case COMMAND_BAD:
                return getListPlaceByRating(city, 1, 2, 3, 5);
            case COMMAND_RATING:
                return getListPlaceByRating(city, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
            default:
                return ERROR;
        }
    }

    private String getListPlaceByRating(String city, Integer... rating) {
        if (cityRepository.existsByName(city)) {
            City cityByName = cityRepository.findCityByName(city);
            List<Place> allPlaces = cityByName.getPlaces();
            List<Place> subListPlaceByRating = getSubListPlaceByRating(allPlaces, rating);
            subListPlaceByRating.sort(new PlaceComparator());
            String placesString = getPlacesString(subListPlaceByRating);
            return city + placesString;
        } else {
            return ERROR_CITY_NOT_FOUND;
        }
    }

    private List<Place> getSubListPlaceByRating(List<Place> places,Integer... rating){
        List<Integer> ratingList = Arrays.asList(rating);
        List<Place> placeListByRating = new ArrayList<>();
        for (Place place : places) {
            Integer rating1 = place.getRating();
            if (ratingList.contains(rating1)){
                placeListByRating.add(place);
            }
        }
        return placeListByRating;
    }

    private String getPlacesString(List<Place> places) {
        if (places.isEmpty()) {
            return ERROR_PLACES_NOT_FOUND;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Place place : places) {
            stringBuilder
                    .append("\n ✔ name place: ")
                    .append(place.getName())
                    .append("\n         \uD83C\uDFD9description: ")
                    .append(place.getDescription())
                    .append("\n         \uD83D\uDCC8rating: ")
                    .append(place.getRating());

        }
        return stringBuilder.toString();
    }

    public String getCitiesString(List<String> citiesName){
        if (citiesName.isEmpty()){
            return ERROR_CITIES_NOT_FOUND;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String cityName : citiesName) {
            stringBuilder.append("\n \uD83C\uDFD8").append(cityName);
        }
        return stringBuilder.toString();
    }
}
