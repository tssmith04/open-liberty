/*******************************************************************************
 * Copyright (c) 2024 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package com.ibm.ws.webcontainer.servlet31.fat.tests;

import static org.junit.Assert.assertTrue;

import java.util.logging.Logger;

import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ibm.websphere.simplicity.ShrinkHelper;
import com.ibm.ws.webcontainer.servlet31.fat.FATSuite;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.UploadFileSpec;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

import componenttest.annotation.Server;
import componenttest.custom.junit.runner.FATRunner;
import componenttest.custom.junit.runner.Mode;
import componenttest.custom.junit.runner.Mode.TestMode;
import componenttest.topology.impl.LibertyServer;

/**
 * Test that the allowAbsoluteFileNameForPartWrite works as intended.
 * When true the filename in part.write method should be treated as an absolute location.
 * When false the filename will be handled as a relative path.
 * The default in servlet-6.0 and earlier is false. The default in servlet-6.1 and later is true.
 * @author Thomas Smith
 */
@RunWith(FATRunner.class)
public class PH62271Test {

    private static final Logger LOG = Logger.getLogger(PH62271Test.class.getName());

    @Rule
    public TestName name = new TestName();

    private static final String PH62271_JAR_NAME = "PH62271";
    private static final String PH62271_APP_NAME = "PH62271";

    protected static final Class<?> c = PH62271.class;

    @Server("servlet31_PH62271")
    public static LibertyServer server;

    @BeforeClass
    public static void setup() throws Exception {
        // Build the jars to add to the war app as a lib
        JavaArchive PH62271Jar = ShrinkHelper.buildJavaArchive(PH62271_JAR_NAME + ".jar",
                                                                     "com.ibm.ws.webcontainer.servlet_31_fat.testservlet31.jar.servlets");
        // Build the war app and add the dependencies
        WebArchive PH62271App = ShrinkHelper.buildDefaultApp(PH62271_APP_NAME + ".war",
                                                                   "com.ibm.ws.webcontainer.servlet_31_fat.testservlet31.war.servlets",
                                                                   "com.ibm.ws.webcontainer.servlet_31_fat.testservlet31.war.listeners");
        PH62271App = (WebArchive) ShrinkHelper.addDirectory(PH62271App, "test-applications/TestServlet31.war/resources");
        PH62271App = TestServlet31App.addAsLibraries(PH62271Jar);

        // Export the application.
        ShrinkHelper.exportDropinAppToServer(server, PH62271App);

        server.startServer(c.getSimpleName() + ".log");
    }

    @AfterClass
    public static void tearDown() throws Exception {
        // Stop the server
        if (server != null && server.isStarted()) {
            server.stopServer();
        }
    }

    @Test
    public void testPH62271 () throws Exception {

    }
}