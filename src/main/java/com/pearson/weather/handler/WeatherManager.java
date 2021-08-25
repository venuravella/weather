package com.pearson.weather.handler;

import com.pearson.weather.entity.City;
import org.springframework.beans.factory.annotation.Autowired;
import com.pearson.weather.service.CityService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class WeatherManager {

    @Autowired
    private CityService cityService;

    public Mono<City> getCity(String cityName, String countryName) {
        return cityService.getByCity(cityName, countryName);
    }

    public Flux<City> getCities() {
        return cityService.getAllCity();
    }

    public Mono<City> addCity(String cityName, String countryName, City city) {
        return cityService.saveWeather(cityName, countryName, city);
    }

    public Mono<String> deleteCity(String name) {
        return cityService.deleteCity(name);
    }
}
