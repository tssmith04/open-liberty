/*******************************************************************************
 * Copyright (c) 2020 IBM Corporation and others.
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
package io.openliberty.opentracing.internal.test;

import com.ibm.websphere.simplicity.log.Log;

/**
 * Consolidated logging utility.  Isolates access to the
 * Simplicity logger, {@link Log}.
 */
public class FATLogging {
    public static void info(
        Class<?> sourceClass, String sourceMethodName,
        String text) {

        Log.info(sourceClass, sourceMethodName, text);
    }

    public static void info(
        Class<?> sourceClass, String sourceMethodName,
        String text, Object value) {

        Log.info(
            sourceClass, sourceMethodName,
            text + " [ " + value + " ]");
    }

    public static void info(
        Class<?> sourceClass, String sourceMethodName,
        String prefix,
        String text, Object value) {

        Log.info(
            sourceClass, sourceMethodName,
            prefix + ": " + text + " [ " + value + " ]");
    }

    public static void info(
        Class<?> sourceClass, String sourceMethodName,
        String text1, Object value1,
        String text2, Object value2) {

        Log.info(
            sourceClass, sourceMethodName,
            text1 + " [ " + value1 + " ] " + text2 + " [ " + value2 + " ]");
    }

    public static void info(
        Class<?> sourceClass, String sourceMethodName,
        String prefix,
        String text1, Object value1,
        String text2, Object value2) {

        Log.info(
            sourceClass, sourceMethodName,
            prefix + ": " + text1 + " [ " + value1 + " ] " + text2 + " [ " + value2 + " ]");
    }
}
