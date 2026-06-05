// Copyright 2025 UNN-CS

package com.mycompany.app;

import org.openqa.selenium.WebDriver;

public class Task2 {
    
    public static String getIpAddress(WebDriver driver) {
        try {
            driver.get("https://api.ipify.org/?format=json");
            Thread.sleep(1000);
            String src = driver.getPageSource();
            int start = src.indexOf("\"ip\":\"") + 6;
            int end = src.indexOf("\"", start);
            return src.substring(start, end);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
