/*******************************************************************************
 * Copyright (c) 2024 IBM Corporation and others.
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
package tests;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.ibm.tx.jta.ut.util.LastingXAResourceImpl;
import com.ibm.websphere.simplicity.log.Log;

import componenttest.annotation.Server;
import componenttest.custom.junit.runner.FATRunner;
import componenttest.topology.impl.LibertyServer;

@RunWith(FATRunner.class)
public class DupXidTest extends DupXidTestBase {

    @Server("recovery1")
    public static LibertyServer s1;

    @Server("recovery2")
    public static LibertyServer s2;

    @BeforeClass
    public static void setup() throws Exception {
        Log.info(DupXidTest.class, "subBefore", "");
        // Clean up XA resource files
        s1.deleteFileFromLibertyInstallRoot("/usr/shared/" + LastingXAResourceImpl.STATE_FILE_ROOT);
        setup(s1, s2, APP_NAME + "/DupXidServlet");
    }
}