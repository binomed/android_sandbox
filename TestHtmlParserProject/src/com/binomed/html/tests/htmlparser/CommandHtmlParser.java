package com.binomed.html.tests.htmlparser;

import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlparser.Attribute;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.Text;
import org.htmlparser.util.NodeIterator;
import org.springframework.stereotype.Repository;

import com.binomed.html.tests.api.CommandParseDocument;

@Repository
public class CommandHtmlParser implements CommandParseDocument {

	private Log LOGGER = LogFactory.getLog(CommandHtmlParser.class);

	@Override
	public long parseDocument(String httpAdress) {
		long time = System.currentTimeMillis();
		LOGGER.info("CommandHtmlParser : parseDocument");
		try {
			Parser parser = new Parser(CommandHtmlParser.class.getResource(httpAdress).getPath());
			Node node = null;
			Tag tag = null;
			Text text = null;
			for (NodeIterator i = parser.elements(); i.hasMoreNodes();) {
				node = i.nextNode();
				if (node instanceof Tag) {
					tag = (Tag) node;
					if (StringUtils.equals(tag.getTagName(), "div")) {

						for (Attribute attr : (Vector<Attribute>) tag.getAttributesEx()) {
							LOGGER.info(attr.getName() + " : " + attr.getValue() + " : " + attr.getRawValue());
						}

					}
				} else if (node instanceof Text) {
					text = (Text) node;
					LOGGER.info("Characters : " + text.getText());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return System.currentTimeMillis() - time;
	}
}
