package com.pearson.weather.constant;


public interface WeatherUrlConstants {

  String slash = "/";
  String addCity = "add/{cityName}/{countryName}/{city}";
  String deleteCity = "delete/{cityName}";
  String getAllCity = "showAll";
  String fetchCity = "show/{cityName}/{countryName}";

  public enum UrlConstants {
    ADD_CITY(slash + addCity),
    DELETE_CITY(slash + deleteCity),
    GET_CITIES(slash + getAllCity),
    FETCH_CITY(slash + fetchCity);

    private final String value;

    UrlConstants(String val) {
      this.value = val;
    }

    public String getValue() {
      return value;
    }
  }

}
