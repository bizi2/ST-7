// Copyright 2025 UNN-CS

package com.mycompany.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class App {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");
        
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--remote-allow-origins=*");
        
        WebDriver driver = new ChromeDriver(opts);
        
        try {
            System.out.println("=== ST-7 Selenium Tasks ===\n");
            
            // ??????? ?1 - ?????????? random.org
            System.out.println("--- Task 1: Password Generator ---");
            driver.get("https://www.random.org/passwords/?num=1&len=12&format=plain&rnd=new");
            Thread.sleep(2000);
            String password = driver.findElement(By.tagName("body")).getText().trim();
            // ??????? HTML ???? ???? ????
            password = password.replaceAll("<[^>]*>", "").trim();
            System.out.println("Generated password: " + password);
            
            // ??????? ?2
            System.out.println("\n--- Task 2: IP Address ---");
            String ip = Task2.getIpAddress(driver);
            System.out.println("Your IP address: " + ip);
            
            // ??????? ?3
            System.out.println("\n--- Task 3: Weather Forecast ---");
            Task3.getWeatherForecast(driver);
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}
