// Copyright 2025 UNN-CS

package com.mycompany.app;

import org.openqa.selenium.WebDriver;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Task3 {
    
    public static void showWeather(WebDriver drv) throws Exception {
        String link = "https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44&hourly=temperature_2m,rain&timezone=Europe%2FMoscow&forecast_days=1";
        drv.get(link);
        Thread.sleep(2000);
        
        String rawData = drv.getPageSource();
        String cleanData = rawData.replaceAll("<[^>]*>", "").trim();
        
        JSONParser prs = new JSONParser();
        JSONObject rootObj = (JSONObject) prs.parse(cleanData);
        JSONObject hourPart = (JSONObject) rootObj.get("hourly");
        
        JSONArray timeArr = (JSONArray) hourPart.get("time");
        JSONArray tempArr = (JSONArray) hourPart.get("temperature_2m");
        JSONArray rainArr = (JSONArray) hourPart.get("rain");
        
        System.out.println("\nHourly forecast for Nizhny Novgorod:");
        System.out.println("No\tDateTime\t\tTemperature\tRain (mm)");
        System.out.println("------------------------------------------------");
        
        StringBuilder buf = new StringBuilder();
        buf.append("Weather Forecast\n");
        buf.append("================\n\n");
        
        int lim = Math.min(timeArr.size(), 24);
        for (int idx = 0; idx < lim; idx++) {
            String dt = (String) timeArr.get(idx);
            double tmp = ((Number) tempArr.get(idx)).doubleValue();
            double rn = ((Number) rainArr.get(idx)).doubleValue();
            
            System.out.printf("%d\t%s\t%.1f?C\t\t%.2f mm\n", idx + 1, dt, tmp, rn);
            buf.append(idx + 1).append(") ").append(dt).append(" : ").append(tmp).append("?C, rain ").append(rn).append(" mm\n");
        }
        
        Files.createDirectories(Paths.get("result"));
        Files.write(Paths.get("result/forecast.txt"), buf.toString().getBytes());
        System.out.println("\n? Saved to result/forecast.txt");
    }
}
