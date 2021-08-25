package com.pearson.weather.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.pearson.weather.constant.WeatherUrlConstants;
import com.pearson.weather.entity.City;
import com.pearson.weather.handler.WeatherManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.*;


/**
 * All  end points are exposed here.
 */
@Slf4j
@Configuration
public class WeatherRouterController {

  @Autowired
  private WeatherManager weatherManager;

  /**
   * Create bean for router function.
   *
   * @return
   */
  @Bean
  public RouterFunction<ServerResponse> initWeatherRoutes() {
    return route(GET(WeatherUrlConstants.UrlConstants.FETCH_CITY.getValue()),
        request -> ServerResponse.ok()
            .body(weatherManager.getCity(request.pathVariable("cityName"),
                request.pathVariable("countryName")), City.class))

        .and(route(GET(WeatherUrlConstants.UrlConstants.GET_CITIES.getValue()),
            request -> ServerResponse.ok()
                .body(weatherManager.getCities(), City.class)))

        .and(route(POST(WeatherUrlConstants.UrlConstants.ADD_CITY.getValue()),
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
            }))

        .and(route(DELETE(WeatherUrlConstants.UrlConstants.DELETE_CITY.getValue()),
            request -> ServerResponse.ok()
                .body(weatherManager.deleteCity(request.pathVariable("cityName")),
                    String.class)));
  }

}