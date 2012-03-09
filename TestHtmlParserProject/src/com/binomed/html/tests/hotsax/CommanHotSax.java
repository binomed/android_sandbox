package com.binomed.html.tests.hotsax;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.binomed.html.tests.CommandParseDocument;

public class CommanHotSax implements CommandParseDocument {

	@Override
	public long parseDocument(String httpAdress) {
		long time = System.currentTimeMillis();

		InputStream is = null;
		try {
			URL url = new URL(httpAdress);
			XMLReader parser = XMLReaderFactory.createXMLReader("hotsax.html.sax.SaxParser");

			// register your contentHandler
			parser.setContentHandler(new HotSaxParser());
			is = url.openStream();
			InputSource source = new InputSource(is);
			parser.parse(source);
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
