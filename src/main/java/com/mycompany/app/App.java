// Copyright 2025 UNN-CS

package com.mycompany.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.nio.file.Files;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");
        
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--remote-allow-origins=*");
        
        WebDriver driver = new ChromeDriver(opts);
        
        try {
            System.out.println("=== NAZYROV ST-7 ===\n");
            
            // TASK 1
            System.out.println("[1] Password:");
            driver.get("https://www.random.org/passwords/?num=1&len=12&format=plain&rnd=new");
            Thread.sleep(2000);
            String pwd = driver.getPageSource().trim();
            System.out.println("    " + pwd);
            
            // TASK 2
            System.out.println("\n[2] IP Address:");
            driver.get("https://api.ipify.org/?format=json");
            Thread.sleep(1000);
            String src = driver.getPageSource();
            String ip = src.substring(src.indexOf(":") + 2, src.lastIndexOf("\""));
            System.out.println("    " + ip);
            
            // TASK 3
            System.out.println("\n[3] Weather forecast:");
            String url = "https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44&hourly=temperature_2m,rain&timezone=Europe%2FMoscow&forecast_days=1";
            driver.get(url);
            Thread.sleep(2000);
            String json = driver.getPageSource();
            
            Files.createDirectories(Paths.get("result"));
            Files.write(Paths.get("result/forecast.txt"), json.getBytes());
            System.out.println("    Weather data saved (" + json.length() + " bytes)");
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}
