package by.treval.telegramtravelbot.controller;

import by.treval.telegramtravelbot.entity.City;
import by.treval.telegramtravelbot.entity.Place;
import by.treval.telegramtravelbot.service.CityService;
import by.treval.telegramtravelbot.service.CityServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping(path = "/")
public class CityController {


    private final CityService cityService;

    public CityController(CityServiceImpl cityService) {
        this.cityService = cityService;
    }


    @PostMapping("city/add")
    public ResponseEntity<?> addCity( @Valid @RequestBody City city, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (cityService.createCity(city)) {
            return  new ResponseEntity<>(HttpStatus.CREATED);
        }
        return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("city/update/{newName}")
    public ResponseEntity<?> updateCity(@Valid @RequestBody City city,@NotBlank @PathVariable String newName, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (cityService.updateCityName(city, newName)) {
            return  new ResponseEntity<>(HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("city/delete")
    public ResponseEntity<?> deleteCity(@Valid @RequestBody City city, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (cityService.deleteCity(city)) {
            return  new ResponseEntity<>(HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("place/add/{city}")
    public ResponseEntity<?> addPlace(@Valid @RequestBody Place place, BindingResult bindingResult, @PathVariable String city){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (cityService.addPlaceByCityName(place, city)) {
            return  new ResponseEntity<>(HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("place/delete/{city}")
    public ResponseEntity<?> deletePlace(@Valid @RequestBody Place place, @PathVariable String city, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (cityService.deletePlaceByCityName(place, city)) {
            return  new ResponseEntity<>(HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("place/{city}")
    public ResponseEntity<?> getAllCites(@PathVariable String city){
        List<Place> allPlaceByCity = cityService.getAllPlaceByCity(city);
        if (allPlaceByCity.isEmpty()){
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            return  new ResponseEntity<>(allPlaceByCity, HttpStatus.OK);
        }
    }

    @GetMapping("city")
    public ResponseEntity<?> getAllCites(){
        List<City> all = cityService.getAll();
        return  new ResponseEntity<>(all, HttpStatus.OK);
    }
}
