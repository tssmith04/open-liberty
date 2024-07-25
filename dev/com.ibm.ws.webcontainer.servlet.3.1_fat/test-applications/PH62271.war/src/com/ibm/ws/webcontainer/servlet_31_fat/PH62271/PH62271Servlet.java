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
package com.ibm.ws.webcontainer.servlet_31_fat.PH62271;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * This servlet tests whether the absolute or relative path is used
 * within the server to save the file uploaded depending on the
 * allowAbsolutePathForPartWriting parameter.
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

            String ss;
            ss = "--------  Using getPart(\"files\") --------";
            sos1.println(ss);
            sos1.println(" ");

            // // Can be shortened eventually, but may be good for debugging at first
            // if (part != null) {
            //     ss = "Part        = " + part;
            //     sos1.println(ss);
            //     ss = "Content 	= " + part.getContentType();
            //     sos1.println(ss);
            //     ss = "Header  	= " + part.getHeader("files");
            //     sos1.println(ss);
            //     ss = "HeaderNames = " + part.getHeaderNames();
            //     sos1.println(ss);

            //     Collection<String> headercollection = part.getHeaderNames();
            //     for (Iterator<String> It = headercollection.iterator(); It.hasNext();) {

            //         String header = It.next();
            //         ss = "Header  	= " + part.getHeader(header);
            //         sos1.println(ss);
            //         ss = "Headers  	= " + part.getHeaders(header);
            //         sos1.println(ss);
            //     }

            //     ss = "InputStream = " + part.getInputStream();
            //     sos1.println(ss);
            //     ss = "Name        = " + part.getName();
            //     sos1.println(ss);
            //     ss = "Size        = " + part.getSize();
            //     sos1.println(ss);
            //     ss = "FileName    = " + part.getSubmittedFileName();
            //     sos1.println(ss);

            //     sos1.println(" ");
            //     ss = "--------  Printing File Content --------";
            //     sos1.println(ss);

            //     BufferedReader in = new BufferedReader(new InputStreamReader(part.getInputStream()));
            //     String line = null;

            //     while ((line = in.readLine()) != null) {
            //         sos1.println(line);
            //     }

            if (part != null){
                String path = request.getParameterMap().get("location")[0];
                sos1.println("Server Path: " + path);
                String final_path = path + "/uploads/" + part.getSubmittedFileName();
                final_path = final_path.replace('/', File.separator.charAt(0)).replace('\\', File.separator.charAt(0));
                sos1.println("Final Write Path: " + final_path);

                part.write(final_path);
            } else {
                sos1.println("Part was null");
            }
        } catch (Exception e) {
            ServletOutputStream sos1 = response.getOutputStream();
            String ss = "--------  Exception Output --------";
            sos1.println(ss);
            sos1.println(e.getMessage());
        }
    }

}