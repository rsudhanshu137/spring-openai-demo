package com.javatechie.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RealTimeDataTool {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${weather.api.key}")
    private String weatherApiKey;

    @Tool(description = "Get current weather for a given city")
    public String getWeatherInfo(@ToolParam(description = "City name like Patna,Delhi") String city){
        System.out.println("Tool calling :-- Fetching weather info for " + city);
        String url = "https://api.weatherapi.com/v1/current.json?key={key}&q={city}";
        return restTemplate.getForObject(url, String.class, weatherApiKey, city);
    }

    @Tool(description = "Get timezone for a given city")
    public String getTimeZoneInfo(@ToolParam(description = "City name like Patna,Delhi") String city){
        System.out.println("Tool calling :-- Fetching timezone info for " + city);
        String url = "https://api.weatherapi.com/v1/timezone.json?key={key}&q={city}";
        return restTemplate.getForObject(url, String.class, weatherApiKey, city);
    }
}
