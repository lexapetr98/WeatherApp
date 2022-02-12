package com.weather.weather;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


@SpringBootApplication
public class WeatherApplication {
    String latitude = "54.991375";
    String longitude = "73.371529";
    String myApiKey = "9e9263def52c9cf81997b01327a88a86";
    String URL = String.format("https://api.openweathermap.org/data/2.5/onecall?lat=%s&lon=%s&units=metric&exclude=current,minutely,hourly,alerts&appid=%s",latitude,longitude,myApiKey);

    private static final Logger log = LoggerFactory.getLogger(WeatherApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(WeatherApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
            WeatherData weatherData = restTemplate.getForObject(URL, WeatherData.class);
            System.out.println(dayWithMinimalDifference(weatherData.getDaily()));
            System.out.println(dayWithMaximumSunDay(weatherData.getDaily()));
        };
    }

    public String dayWithMinimalDifference(final List<Daily> dailies){
        int dt = 0;
        double difference = 0;
        for(Daily x: dailies){
            if(x.getTemp().getNight() - x.getFeels_like().getNight() > difference){
                difference = x.getTemp().getNight() - x.getFeels_like().getNight();
                dt = x.getDt();
            }
        }
        LocalDate ld = Instant.ofEpochSecond(dt).atZone(ZoneId.systemDefault()).toLocalDate();
        return "Minimal difference between temperature at date: " + ld + " with difference " + Math.round(difference) + "\u2103";
    }

    public String dayWithMaximumSunDay(final List<Daily> dailies){
        int dt = 0;
        double difference = 0;
        for(int i = 0; i < 5;i++){
            if((dailies.get(i).getSunset() - dailies.get(i).getSunrise()) > difference){
                difference = dailies.get(i).getSunset() - dailies.get(i).getSunrise();
                dt = dailies.get(i).getDt();
            }
        }
        LocalDate ld = Instant.ofEpochSecond(dt).atZone(ZoneId.systemDefault()).toLocalDate();
        return "Maximum sun day at date: " + ld + " with length of sun day " + Math.round(difference/3600) + " hr " + Math.round(difference%3600/60) + " minutes";
    }
}
