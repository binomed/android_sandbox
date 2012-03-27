package com.binomed.html.tests.htmlparser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.binomed.html.tests.CommandParseDocument;

@Repository
public class CommandHtmlParser implements CommandParseDocument {

	private Log LOGGER = LogFactory.getLog(CommandHtmlParser.class);

	@Override
	public long parseDocument(String httpAdress) {
		LOGGER.info("CommandHtmlParser : parseDocument");
		// TODO Auto-generated method stub
		return 0;
	}

}
