/*******************************************************************************
 * Copyright (c) 2018 IBM Corporation and others.
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
package com.ibm.ws.install;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import com.ibm.ws.install.internal.InstallLicenseImpl;
import com.ibm.ws.repository.common.enums.LicenseType;
import com.ibm.ws.repository.exceptions.RepositoryException;
import com.ibm.ws.repository.resources.AttachmentResource;

import test.common.SharedOutputManager;

/**
 *
 */
public class InstallLicenseTest {
    @Rule
    public static SharedOutputManager outputMgr = SharedOutputManager.getInstance().trace("*=all");

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        outputMgr.captureStreams();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        outputMgr.restoreStreams();
    }

    @Test
    public void testInstallLicenseImpl() throws RepositoryException {
        InstallLicenseImpl ili = new InstallLicenseImpl("type", (LicenseType) null, (AttachmentResource) null, (AttachmentResource) null, (AttachmentResource) null);
        assertEquals("InstallLicenseImpl.getId()", "type", ili.getId());
        assertEquals("InstallLicenseImpl.getType()", "", ili.getType());
        assertEquals("InstallLicenseImpl.getName()", "", ili.getName());
        assertEquals("InstallLicenseImpl.getProgramName()", "", ili.getProgramName());
        assertEquals("InstallLicenseImpl.getInformation()", "", ili.getInformation());
        assertEquals("InstallLicenseImpl.getAgreement()", "", ili.getAgreement());
        assertEquals("InstallLicenseImpl.getFeatures().size()", 0, ili.getFeatures().size());
        ili.addFeature("f1");
        assertEquals("InstallLicenseImpl.getFeatures().size()", 1, ili.getFeatures().size());

        ili = new InstallLicenseImpl("type", LicenseType.UNSPECIFIED, (AttachmentResource) null, (AttachmentResource) null, (AttachmentResource) null);
        assertEquals("InstallLicenseImpl.getType() returns UNSPECIFIED", "UNSPECIFIED", ili.getType());

        ili = new InstallLicenseImpl("type", LicenseType.ILAN, (AttachmentResource) null, (AttachmentResource) null, (AttachmentResource) null);
        assertEquals("InstallLicenseImpl.getType() returns UNSPECIFIED", "ILAN", ili.getType());
    }

    @Test
    public void testInstallLicenseImplUnspecifiedLicenseType() throws RepositoryException {
        InstallLicenseImpl ili = new InstallLicenseImpl("http://www.ibm.com/licenses/wlp-featureterms-v1", null, null);
        assertEquals("InstallLicenseImpl.getType()", "UNSPECIFIED", ili.getType());
        ili = new InstallLicenseImpl("http://www.ibm.com/licenses/WLP-featureterms-v1", null, null);
        assertEquals("InstallLicenseImpl.getType()", "UNSPECIFIED", ili.getType());
    }

}
