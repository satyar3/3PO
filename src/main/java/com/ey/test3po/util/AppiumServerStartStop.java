package com.ey.test3po.util;


import java.io.File;

import com.ey.test3po.testbase.TestBase;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class AppiumServerStartStop extends TestBase{

    static String Appium_Node_Path="C:\\Program Files\\nodejs\\node.exe";
    static String Appium_JS_Path="C:\\Program Files (x86)\\Appium\\resources\\app\\node_modules\\appium\\lib\\appium.js";
    static AppiumDriverLocalService service;
    static String service_url;

    public static void appiumStart() throws Exception{
        service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder().
                usingPort(Integer.parseInt(prop.getProperty("port"))).usingDriverExecutable(new File(Appium_Node_Path)).
                withAppiumJS(new File(Appium_JS_Path)));
       
        service.start();
        Thread.sleep(5000);
        service_url = service.getUrl().toString();
        
    }

    public static void appiumStop() throws Exception{
        service.stop();

    }
}