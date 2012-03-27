package com.binomed.html.tests.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.binomed.html.tests.CommandParseDocument;
import com.binomed.html.tests.api.IHtmlParserAPI;

@Repository
public class HtmlParserAPI implements IHtmlParserAPI {

	@Autowired
	private CommandParseDocument[] commandsParsers;

	@Override
	public void runTest() {
		for (CommandParseDocument commandParser : commandsParsers) {
			commandParser.parseDocument("/Binomed.htm");
		}

	}

}
