package com.w2a.DataDrivenFramework.rough;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestProperties{
	
	public static void main(String[] args) throws IOException {
		
		Properties config = new Properties();
		Properties OR = new Properties();
		FileInputStream file  = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/Properties/Config.properties");
		FileInputStream orFile = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/Properties/OR.properties");
		config.load(file);
		OR.load(orFile);
		System.out.println(OR.getProperty("bmlBtn"));
		System.out.println(config.getProperty("browser"));
	}
	
}