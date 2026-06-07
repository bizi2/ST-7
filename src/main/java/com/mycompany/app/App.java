// Copyright 2025 UNN-CS

package com.mycompany.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class App {
    
    public static void main(String[] args) throws Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");
        
        ChromeOptions opt = new ChromeOptions();
        opt.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
        opt.addArguments("--remote-allow-origins=*");
        
        WebDriver br = new ChromeDriver(opt);
        
        try {
            System.out.println("=== ST-7 ===\n");
            
            // 1
            System.out.println("[1] Password:");
            br.get("https://www.calculator.net/password-generator.html");
            
            WebDriverWait wt = new WebDriverWait(br, 15);
            WebElement resBlock = wt.until(ExpectedConditions.presenceOfElementLocated(By.id("resultid")));
            wt.until(d -> !resBlock.getText().trim().isEmpty());
            
            String passCode;
            try {
                passCode = resBlock.findElement(By.tagName("b")).getText().trim();
            } catch (Exception e) {
                passCode = resBlock.getText().trim();
            }
            System.out.println("    " + passCode);
            
            // 2
            System.out.println("\n[2] IP:");
            String ipAddr = Task2.extractIP(br);
            System.out.println("    " + ipAddr);
            
            // 3
            System.out.println("\n[3] Weather:");
            Task3.showWeather(br);
            
        } finally {
            br.quit();
        }
    }
}
