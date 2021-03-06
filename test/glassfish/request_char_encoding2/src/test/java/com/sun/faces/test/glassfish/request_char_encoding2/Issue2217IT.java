/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package com.sun.faces.test.glassfish.request_char_encoding2;

import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/*
 * Because the glassfish-web.xml sets the requestencoding to US-ASCII, the
 * UTF-8 characters will not be processed correctly.
 */
public class Issue2217IT {

    private String webUrl;
    private WebClient webClient;

    @Before
    public void setUp() {
        webUrl = System.getProperty("integration.url");
        webClient = new WebClient();
    }

    @After
    public void tearDown() {
        webClient.close();
    }

    @Test
    public void testJapanese() throws Exception {
        HtmlPage page = webClient.getPage(webUrl);
        
        String str = page.getBody().asText();
        assertTrue(str.contains("Hello, my name is Duke. What's yours?"));

        HtmlTextInput text = (HtmlTextInput) page.getElementById("username");
        text.type("\u65E5");
        
        HtmlSubmitInput button = (HtmlSubmitInput) page.getElementById("submit");
        page = button.click();
        
        str = page.asXml();
        assertFalse(str.contains("\u65E5"));    
    }
        
    @Test
    public void testHebrew() throws Exception {
        HtmlPage page = webClient.getPage(webUrl);
        
        String pageText = page.getBody().asText();
        assertTrue(pageText.contains("Hello, my name is Duke. What's yours?"));

        HtmlTextInput text = (HtmlTextInput) page.getElementById("username");
        text.type("\u05D0");
        
        HtmlSubmitInput button = (HtmlSubmitInput) page.getElementById("submit");
        page = button.click();
        
        pageText = page.getBody().asText();
        assertFalse(pageText.contains("\u05D0"));   
    }
}
