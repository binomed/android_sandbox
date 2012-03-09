package com.binomed.html.tests.hotsax;

import org.apache.commons.lang.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class HotSaxParser implements ContentHandler {

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {

	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("END document with HotSaxParser");
		System.out.println("==============================");
	}

	@Override
	public void startDocument() throws SAXException {
		System.out.println("==============================");
		System.out.println("Start document with HotSaxParser");

	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		if (StringUtils.equalsIgnoreCase(localName, "div")) {
			for (int i = 0; i < atts.getLength(); i++) {
				System.out.println(atts.getLocalName(i) + " : " + atts.getValue(i));
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
	}

	@Override
	public void processingInstruction(String target, String data) throws SAXException {
	}

	@Override
	public void setDocumentLocator(Locator locator) {
	}

	@Override
	public void skippedEntity(String name) throws SAXException {
	}

	@Override
	public void startPrefixMapping(String prefix, String uri) throws SAXException {
	}

}
