package com.binomed.html.tests.hotsax;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.binomed.html.tests.CommandParseDocument;

@Repository
public class CommandHotSax implements CommandParseDocument {

	private Log LOGGER = LogFactory.getLog(CommandHotSax.class);

	@Override
	public long parseDocument(String httpAdress) {
		LOGGER.info("CommandHotSax : parseDocument");

		long time = System.currentTimeMillis();

		InputStream is = null;
		try {

			// URL url = new URL(httpAdress);
			XMLReader parser = XMLReaderFactory.createXMLReader("hotsax.html.sax.SaxParser");

			// register your contentHandler
			parser.setContentHandler(new HotSaxParser());
			// is = url.openStream();
			is = CommandHotSax.class.getResourceAsStream(httpAdress);
			InputSource source = new InputSource(new InputStreamReader(is));
			parser.parse(source);
			// parser.parse(CommandHotSax.class.getResource(httpAdress).getFile());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return System.currentTimeMillis() - time;
	}

}
