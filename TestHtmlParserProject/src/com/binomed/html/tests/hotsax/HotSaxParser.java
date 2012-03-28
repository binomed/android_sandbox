package com.binomed.html.tests.hotsax;

import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class HotSaxParser implements ContentHandler {

	private Log LOGGER = LogFactory.getLog(HotSaxParser.class);

	private StringBuilder strBuilder = new StringBuilder();

	private Queue<String> tagCharacters = new LinkedList<String>();

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		strBuilder.append(ch, start, length);
		if (!tagCharacters.isEmpty()) {
			LOGGER.info("Characters pour le tag: " + tagCharacters.poll());
		}
		LOGGER.info("Characters : " + new String(ch, start, length));
	}

	@Override
	public void endDocument() throws SAXException {
		LOGGER.info("END document with HotSaxParser");
		LOGGER.info("==============================");
	}

	@Override
	public void startDocument() throws SAXException {
		LOGGER.info("==============================");
		LOGGER.info("Start document with HotSaxParser");

	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		tagCharacters.add(localName);
		if (StringUtils.equalsIgnoreCase(localName, "div")) {

			if (StringUtils.equals(atts.getValue("id"), "latest_news")) {
				LOGGER.info("==============================");
				LOGGER.info("LASTEST NEWS");
				LOGGER.info("==============================");

			}
			for (int i = 0; i < atts.getLength(); i++) {
				LOGGER.info(atts.getQName(i) + " : " + atts.getValue(i));
			}
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

	}

	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
	}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
		if (!tagCharacters.isEmpty()) {
			LOGGER.info("IgnorableCharacters pour le tag: " + tagCharacters.poll());
		}
		LOGGER.info("IgnorableCharacters : " + new String(ch, start, length));
	}

	@Override
	public void processingInstruction(String target, String data) throws SAXException {
	}

	@Override
	public void setDocumentLocator(Locator locator) {
	}

	@Override
	public void skippedEntity(String name) throws SAXException {

		LOGGER.info("SkippedEntity : " + name);

	}

	@Override
	public void startPrefixMapping(String prefix, String uri) throws SAXException {
	}

}
