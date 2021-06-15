package by.treval.telegramtravelbot.repository;

import by.treval.telegramtravelbot.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
    City findCityByName(String name);
    boolean existsByName(String name);
    City getByName(String name);

    @Query(value = "select name from City")
    List<String> getAllCitiesName();

}
