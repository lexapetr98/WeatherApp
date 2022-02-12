package com.weather.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Daily {
    private Temp temp;
    private FeelsLike feels_like;
    private int dt;
    private int sunrise;
    private int sunset;

    @Override
    public String toString() {
        return "dt = " + dt + " sunrise " + sunrise + " sunset " + sunset + " temp." + temp.toString()+ " feels_like." + feels_like.toString();
    }
}