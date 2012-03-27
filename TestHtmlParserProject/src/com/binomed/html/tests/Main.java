package com.binomed.html.tests;

import com.binomed.html.tests.hotsax.CommandHotSax;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		CommandParseDocument command = new CommandHotSax();
		command.parseDocument("/com/binomed/html/tests/Binomed.htm");

	}

}
