package com.pearson.weather.constant;


public interface WeatherUrlConstants {

  String addCity = "add/{cityName}/{countryName}/{city}";
  String deleteCity = "delete/{cityName}";
  String getAllCity = "showAll";
  String fetchCity = "show/{cityName}/{countryName}";

  public enum UrlConstants {
    ADD_CITY(addCity),
    DELETE_CITY(deleteCity),
    GET_CITIES(getAllCity),
    FETCH_CITY(fetchCity);

    private final String value;

    UrlConstants(String val) {
      this.value = val;
    }

    public String getValue() {
      return value;
    }
  }

}
