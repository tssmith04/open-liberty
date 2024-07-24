/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.ibm.ws.webcontainer.servlet_31_fat.testservlet31.jar.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * This is just a simple Hello World servlet that we can use to drive requests to so that we can
 * initalize applications. This should be shared with other application so we don't have a ton
 * of Hello World servlets around.
 */
@WebServlet("/PH62271Servlet")
@MultipartConfig(fileSizeThreshold = 50, location = "", maxFileSize = 5000, maxRequestSize = 1000)
public class PH62271Servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */

    public PH62271Servlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ServletOutputStream sos1 = response.getOutputStream();
            Part part = request.getPart("files");

            // Can be shortened eventually, but may be good for debugging at first
            if (part != null) {
                ss = "Part        = " + part;
                sos1.println(ss);
                ss = "Content 	= " + part.getContentType();
                sos1.println(ss);
                ss = "Header  	= " + part.getHeader("files");
                sos1.println(ss);
                ss = "HeaderNames = " + part.getHeaderNames();
                sos1.println(ss);

                Collection<String> headercollection = part.getHeaderNames();
                for (Iterator<String> It = headercollection.iterator(); It.hasNext();) {

                    String header = It.next();
                    ss = "Header  	= " + part.getHeader(header);
                    sos1.println(ss);
                    ss = "Headers  	= " + part.getHeaders(header);
                    sos1.println(ss);
                }

                ss = "InputStream = " + part.getInputStream();
                sos1.println(ss);
                ss = "Name        = " + part.getName();
                sos1.println(ss);
                ss = "Size        = " + part.getSize();
                sos1.println(ss);

                sos1.println(" ");
                ss = "--------  Printing File Content --------";
                sos1.println(ss);

                BufferedReader in = new BufferedReader(new InputStreamReader(part.getInputStream()));
                String line = null;

                while ((line = in.readLine()) != null) {
                    sos1.println(line);
                }

                part.write("/server/file/path/goes/here");

            }
        } catch (Exception e) {
            ServletOutputStream sos1 = response.getOutputStream();
            String ss = "--------  Exception Output --------";
            sos1.println(ss);
            sos1.println(e.getMessage());
        }
    }

}