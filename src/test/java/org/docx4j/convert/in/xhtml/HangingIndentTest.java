/*
 *  This file is part of the docx4j-ImportXHTML library.
 *
 *  Copyright 2011-2013, Plutext Pty Ltd, and contributors.
 *  Portions contributed before 15 July 2013 formed part of docx4j 
 *  and were contributed under ASL v2 (a copy of which is incorporated
 *  herein by reference and applies to those portions). 
 *   
 *  This library as a whole is licensed under the GNU Lesser General 
 *  Public License as published by the Free Software Foundation; 
    version 2.1.
    
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library (see legals/LICENSE); if not, 
    see http://www.gnu.org/licenses/lgpl-2.1.html
    
 */
package org.docx4j.convert.in.xhtml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.CTBookmark;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.junit.Before;
import org.junit.Test;

public class HangingIndentTest {

	private WordprocessingMLPackage wordMLPackage;
	
	@Before
	public void setup() throws InvalidFormatException {
		wordMLPackage = WordprocessingMLPackage.createPackage();
	}

	private List<Object> convert(String xhtml) throws Docx4JException {
		XHTMLImporterImpl XHTMLImporter = new XHTMLImporterImpl(wordMLPackage);	
		return XHTMLImporter.convert(xhtml, "");
	}
	
	@Test public void testHangingIndent() throws Docx4JException, JAXBException {
    	String xhtml= "<p style=\"margin-left:2em;"
    			+ "text-indent:-2em;\">"
    			+ "text</p>";
    	wordMLPackage.getMainDocumentPart().getContent().addAll(
				convert(xhtml));
		List<Object> matches = wordMLPackage.getMainDocumentPart().getJAXBNodesViaXPath("//w:pPr", false);
		PPr ppr = (PPr) matches.get(0);
		assertEquals(ppr.getInd().getLeft().intValue(),480);
		assertEquals(ppr.getInd().getHanging().intValue(),480);
	}

}
