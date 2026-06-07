// Copyright 2025 UNN-CS

package com.mycompany.app;

import org.openqa.selenium.WebDriver;

public class Task2 {
    
    public static String extractIP(WebDriver drv) throws Exception {
        drv.get("https://api.ipify.org/?format=json");
        Thread.sleep(1000);
        String srcCode = drv.getPageSource();
        int posA = srcCode.indexOf("\"ip\":\"") + 6;
        int posB = srcCode.indexOf("\"", posA);
        return srcCode.substring(posA, posB);
    }
}
