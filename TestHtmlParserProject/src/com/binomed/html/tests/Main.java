package com.binomed.html.tests;

import com.binomed.html.tests.hotsax.CommanHotSax;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		CommandParseDocument command = new CommanHotSax();
		command.parseDocument("http://blog.binomed.fr");

	}

}
