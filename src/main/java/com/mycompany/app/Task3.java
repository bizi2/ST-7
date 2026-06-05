// Copyright 2025 UNN-CS

package com.mycompany.app;

import org.openqa.selenium.WebDriver;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Task3 {
    
    public static void getWeatherForecast(WebDriver driver) {
        try {
            String url = "https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44&hourly=temperature_2m,rain&timezone=Europe%2FMoscow&forecast_days=1";
            driver.get(url);
            Thread.sleep(2000);
            
            String json = driver.getPageSource();
            System.out.println("Raw JSON length: " + json.length());
            
            // ??????? ??????? ??? ??????????
            if (json.contains("\"hourly\"")) {
                // ????????? times
                int ti = json.indexOf("\"time\":[") + 8;
                int te = json.indexOf("]", ti);
                String timesPart = json.substring(ti, te);
                String[] times = timesPart.replace("\"", "").split(",");
                
                // ????????? ???????????
                int tmpi = json.indexOf("\"temperature_2m\":[") + 18;
                int tmpe = json.indexOf("]", tmpi);
                String tempsPart = json.substring(tmpi, tmpe);
                String[] temps = tempsPart.split(",");
                
                // ????????? ??????
                int ri = json.indexOf("\"rain\":[") + 8;
                int re = json.indexOf("]", ri);
                String rainsPart = json.substring(ri, re);
                String[] rains = rainsPart.split(",");
                
                System.out.println("\nHourly forecast for Nizhny Novgorod:");
                System.out.println("No\tDateTime\t\tTemperature\tRain (mm)");
                System.out.println("------------------------------------------------");
                
                StringBuilder sb = new StringBuilder();
                sb.append("Weather Forecast for Nizhny Novgorod\n");
                sb.append("==================================\n");
                sb.append("No\tDateTime\t\tTemperature\tRain (mm)\n");
                
                int count = Math.min(times.length, 24);
                for (int i = 0; i < count; i++) {
                    String time = times[i].trim();
                    String temp = temps[i].trim();
                    String rain = rains[i].trim();
                    
                    System.out.printf("%d\t%s\t%s?C\t\t%s mm\n", i + 1, time, temp, rain);
                    sb.append(i + 1).append("\t").append(time).append("\t").append(temp).append("?C\t\t").append(rain).append(" mm\n");
                }
                
                Files.createDirectories(Paths.get("result"));
                Files.write(Paths.get("result/forecast.txt"), sb.toString().getBytes());
                System.out.println("\n? Forecast saved to result/forecast.txt");
            } else {
                System.out.println("No hourly data found in response");
            }
        } catch (Exception e) {
            System.err.println("Weather error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
