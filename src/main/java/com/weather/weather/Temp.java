package com.weather.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Temp {
    private double night;

    @Override
    public String toString() {
        return "night = " + night;
    }


}
