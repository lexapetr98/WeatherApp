package com.weather.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherData {
    private List<Daily> daily;

    @Override
    public String toString() {
        return "Quote{" +
                "daily='" + daily.toString();
    }
}

