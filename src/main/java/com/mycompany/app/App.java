// Copyright 2025 UNN-CS
// Author: Nazyrov A.A.
// My unique implementation of Selenium tasks

package com.mycompany.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileWriter;
import java.io.PrintWriter;

public class App {
    
    private static WebDriver web;
    
    public static void main(String[] args) throws Exception {
        // Setup
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions cfg = new ChromeOptions();
        cfg.addArguments("--remote-allow-origins=*");
        web = new ChromeDriver(cfg);
        
        try {
            printBanner();
            
            // ===== TASK 1: Get random password =====
            String myPassword = getRandomWord();
            System.out.println("[TASK 1] Generated password: " + myPassword);
            
            // ===== TASK 2: Detect my IP =====
            String myIp = getMyIP();
            System.out.println("[TASK 2] My IP address: " + myIp);
            
            // ===== TASK 3: Weather report =====
            saveWeatherReport();
            
        } finally {
            web.quit();
        }
    }
    
    private static void printBanner() {
        System.out.println("??????????????????????????????????????");
        System.out.println("?     NAZYROV WEB AUTOMATION         ?");
        System.out.println("?     ST-7 - Unique Solution         ?");
        System.out.println("??????????????????????????????????????\n");
    }
    
    private static String getRandomWord() throws Exception {
        web.get("https://www.random.org/strings/?num=1&len=10&digits=on&upperalpha=on&loweralpha=on&unique=on&format=plain&rnd=new");
        Thread.sleep(1500);
        return web.findElement(By.tagName("body")).getText().trim();
    }
    
    private static String getMyIP() throws Exception {
        web.get("https://api.my-ip.io/ip.json");
        Thread.sleep(1000);
        String raw = web.getPageSource();
        // My own parsing method
        int startAt = raw.indexOf(":") + 2;
        int endAt = raw.indexOf("\"", startAt);
        return raw.substring(startAt, endAt);
    }
    
    private static void saveWeatherReport() throws Exception {
        String city = "Nizhny+Novgorod";
        web.get("https://wttr.in/" + city + "?format=j1");
        Thread.sleep(2000);
        
        String data = web.getPageSource();
        
        // My custom parser
        String temp = extractValue(data, "temp_C");
        String humid = extractValue(data, "humidity");
        String wind = extractValue(data, "windspeedKmph");
        String desc = extractDescription(data);
        
        System.out.println("\n[TASK 3] Weather in Nizhny Novgorod:");
        System.out.println("  ???????????????????????????");
        System.out.println("  ? Temperature: " + temp + "?C        ?");
        System.out.println("  ? Humidity:    " + humid + "%         ?");
        System.out.println("  ? Wind speed:  " + wind + " km/h    ?");
        System.out.println("  ? Conditions:  " + desc + "   ?");
        System.out.println("  ???????????????????????????");
        
        // Save to file
        Files.createDirectories(Paths.get("result"));
        try (PrintWriter out = new PrintWriter(new FileWriter("result/forecast.txt"))) {
            out.println("===== WEATHER FORECAST =====");
            out.println("Location: Nizhny Novgorod");
            out.println("Temperature: " + temp + "?C");
            out.println("Humidity: " + humid + "%");
            out.println("Wind: " + wind + " km/h");
            out.println("Conditions: " + desc);
        }
        System.out.println("\n[OK] Saved to result/forecast.txt");
    }
    
    private static String extractValue(String json, String key) {
        String search = "\"" + key + "\":\"";
        int pos = json.indexOf(search);
        if (pos < 0) return "?";
        pos += search.length();
        int end = json.indexOf("\"", pos);
        return json.substring(pos, end);
    }
    
    private static String extractDescription(String json) {
        String search = "\"weatherDesc\":[{\"value\":\"";
        int pos = json.indexOf(search);
        if (pos < 0) return "unknown";
        pos += search.length();
        int end = json.indexOf("\"", pos);
        return json.substring(pos, end);
    }
}
