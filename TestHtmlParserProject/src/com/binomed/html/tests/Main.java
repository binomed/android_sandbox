package com.binomed.html.tests;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.binomed.html.tests.api.IHtmlParserAPI;

public class Main {

	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/applicationContext.xml");

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			IHtmlParserAPI api = applicationContext.getBean(IHtmlParserAPI.class);
			api.runTest();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
