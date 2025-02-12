/*******************************************************************************
\ * Copyright (c) 2010 IBM Corporation and others.
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
package com.ibm.ws.kernel.internal.classloader;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.security.cert.Certificate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 */
public class JarResourceEntry implements ResourceEntry {

    private final JarResourceHandler handler;
    private final JarEntry jarEntry;

    public JarResourceEntry(JarResourceHandler handler, JarEntry jarEntry) {
        this.handler = handler;
        this.jarEntry = jarEntry;
    }

    @Override
    public ResourceHandler getResourceHandler() {
        return handler;
    }

    @Override
    public Certificate[] getCertificates() {
        return jarEntry.getCertificates();
    }

    @Override
    public byte[] getBytes() throws IOException {
        InputStream in = null;
        try {
            in = handler.getJarFile().getInputStream(jarEntry);
            return JarFileClassLoader.getBytes(in, jarEntry.getSize());
        } finally {
            JarFileClassLoader.close(in);
        }
    }

    @Override
    public URL toURL() {
        /*
         * Regular "jar" url would cause the JarFile to be opened with verification.
         * In order to by-pass that we create our own URLStreamHandler.
         */
        JarFile jarFile = handler.getJarFile();
        String name = jarFile.getName() + "!/" + jarEntry.getName();
        try {
            final JarEntryURLStreamHandler handler = new JarEntryURLStreamHandler(jarFile, jarEntry);
            final URL url = new URL("jarentry", "", -1, name, handler);
            handler.setExpectedURL(url);
            return url;
        } catch (MalformedURLException e) {
            // this is very unexpected
            throw new Error(e);
        }
    }

    private static class JarEntryURLStreamHandler extends URLStreamHandler {

        private final JarFile jarFile;
        private final JarEntry jarEntry;
        private URL expectedURL;

        public JarEntryURLStreamHandler(JarFile jarFile, JarEntry jarEntry) {
            this.jarFile = jarFile;
            this.jarEntry = jarEntry;
        }

        void setExpectedURL(URL expectedURL) {
            this.expectedURL = expectedURL;
        }

        @Override
        protected URLConnection openConnection(URL url) throws IOException {
            if (url == null || url != expectedURL) {
                throw new IllegalStateException();
            }
            return new JarEntryURLConnection(url, jarFile, jarEntry);
        }

    }

    private static class JarEntryURLConnection extends URLConnection {

        private final JarFile jarFile;
        private final JarEntry jarEntry;

        public JarEntryURLConnection(URL url, JarFile jarFile, JarEntry jarEntry) {
            super(url);
            this.jarFile = jarFile;
            this.jarEntry = jarEntry;
        }

        @Override
        public void connect() throws IOException {}

        @Override
        public InputStream getInputStream() throws IOException {
            return jarFile.getInputStream(jarEntry);
        }

        @Override
        public long getLastModified() {
            return jarEntry.getTime();
        }

        @Override
        public int getContentLength() {
            long size = jarEntry.getSize();
            return (size > Integer.MAX_VALUE) ? -1 : (int) size;
        }
    }
}
