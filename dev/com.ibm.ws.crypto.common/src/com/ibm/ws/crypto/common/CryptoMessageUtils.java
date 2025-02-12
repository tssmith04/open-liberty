/*******************************************************************************
 * Copyright (c) 2024 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package com.ibm.ws.crypto.common;

import com.ibm.websphere.ras.Tr;
import com.ibm.websphere.ras.TraceComponent;

public class CryptoMessageUtils {

    private static TraceComponent tc = Tr.register(CryptoMessageUtils.class);

    
    public void logUnsecureAlgorithm(String configProperty, String unsecureAlgorithm) {
        Tr.warning(tc, "CRYPTO_UNSECURE", configProperty, unsecureAlgorithm);
    }

    public void logUnsecureAlgorithmReplaced(String configProperty, String unsecureAlgorithm, String secureAlgorithm) {
        Tr.warning(tc, "CRYPTO_UNSECURE_REPLACED", configProperty, unsecureAlgorithm, secureAlgorithm);
    }
}
