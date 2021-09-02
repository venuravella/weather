package com.pearson.weather.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.pearson.weather.constant.WeatherUrlConstants.UrlConstants;
import com.pearson.weather.entity.City;
import com.pearson.weather.handler.WeatherManager;
import com.pearson.weather.repository.CityRepository;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;


/**
 * All weather realted end points are exposed here.
 */
@Api(tags = "Weather API")
@Slf4j
@Configuration
public class WeatherRoutes {

  @Autowired
  private WeatherManager weatherManager;

  /**
   * Create bean for router function.
   *
   * @return
   */
  @Bean
  public RouterFunction<ServerResponse> initRoutes() {
    return route().GET(UrlConstants.FETCH_CITY.getValue(),
        request -> ServerResponse.ok()
            .body(weatherManager.getCity(request.pathVariable("cityName"),
                request.pathVariable("countryName")), City.class),
        ops -> ops.operationId("Fetch specific city")
            .beanClass(CityRepository.class)
            .beanMethod("getCityByID"))

        .GET(UrlConstants.GET_CITIES.getValue(),
            request -> ServerResponse.ok()
                .body(weatherManager.getCities(), City.class),
            ops -> ops.operationId("Fetch all city")
                .beanClass(CityRepository.class)
                .beanMethod("getAllCity"))

        .POST(UrlConstants.ADD_CITY.getValue(),
            request -> {
              try {
                return ServerResponse.ok()
                    .body(weatherManager.addCity(request.pathVariable("cityName"),
                        request.pathVariable("countryName"),
                        new ObjectMapper().readValue(request.pathVariable("city"),
                            City.class)), City.class);
              } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
              }
            },
            ops -> ops.operationId("Add city").beanClass(CityRepository.class).beanMethod("save"))

        .DELETE(UrlConstants.DELETE_CITY.getValue(),
            request -> ServerResponse.ok()
                .body(weatherManager.deleteCity(request.pathVariable("cityName")),
                    String.class),
            ops -> ops.operationId("Delete city")
                .beanClass(CityRepository.class)
                .beanMethod("deleteCityByID"))

        .build();

  }

}