/*******************************************************************************
 * Copyright (c) 2019, 2020 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.ibm.ws.security.acme;

import java.io.File;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.util.List;

import com.ibm.websphere.ras.annotation.Sensitive;
import com.ibm.ws.crypto.certificateutil.DefaultSSLCertificateCreator;
import com.ibm.ws.security.acme.internal.AcmeClient.AcmeAccount;

/**
 * An interface to define the methods that an ACME 2.0 OSGi service will
 * provide.
 */
public interface AcmeProvider {

	/**
	 * Create the default keystore and populate the default alias with a
	 * certificate requested from the ACME CA server.
	 *
	 * @param filePath
	 *            The path to generate the new keystore.
	 * @param password
	 *            The password for the generated keystore and certificate.
	 * @param keyStoreType
	 *            The keystore type.
	 * @param keyStoreProvider
	 *            the keystore provider.
	 * @see DefaultSSLCertificateCreator#createDefaultSSLCertificate(String,
	 *      String, int, String, int, String, List)
	 */
	public File createDefaultSSLCertificate(String filePath, String password, String keyStoreType,
			String keyStoreProvider) throws CertificateException;

	/**
	 * Get the ACME account from the ACME CA server for the configured account
	 * key file.
	 * 
	 * @throws AcmeCaException
	 *             If there is an issue getting the ACME account.
	 * @return The account from the ACME CA server.
	 */
	public AcmeAccount getAccount() throws AcmeCaException;

	/**
	 * Get the HTTP-01 challenge authorization for the specified challenge
	 * token. Both the challenge token and the challenge authorization are
	 * generated by the ACME CA server.
	 * 
	 * @param token
	 *            The HTTP-01 challenge token to get the authorization for.
	 * @return The HTTP-01 challenge authorization.
	 * @throws AcmeCaException
	 *             If there was an error retrieving the HTTP-1 challenge
	 *             authorization.
	 */
	public String getHttp01Authorization(String token) throws AcmeCaException;

	/**
	 * Renew the account key pair. This will force a replace of the current
	 * account key pair that is used to access the current account. This is
	 * useful when the current account key pair may have been compromised.
	 * 
	 * @throws AcmeCaException
	 *             If there was an error refreshing the account key pair.
	 */
	public void renewAccountKeyPair() throws AcmeCaException;

	/**
	 * Renew the certificate. This will force a renew of the certificate by
	 * first requesting a new certificate and then revoking the current
	 * certificate.
	 * 
	 * @throws AcmeCaException
	 *             If there was an error requesting a new certificate or
	 *             revoking the old certificate.
	 */
	public void renewCertificate() throws AcmeCaException;

	/**
	 * Revoke the certificate.
	 * 
	 * @param reason
	 *            The reason the certificate is being revoked. The following
	 *            reason are supported: UNSPECIFIED, KEY_COMPROMISE,
	 *            CA_COMPROMISE, AFFILIATION_CHANGED, SUPERSEDED,
	 *            CESSATION_OF_OPERATIONS, CERTIFICATE_HOLD, REMOVE_FROM_CRL,
	 *            PRIVILEGE_WITHDRAWN and AA_COMPROMISE. If null, the reason
	 *            "UNSPECIFIED" will be used.
	 * @throws IllegalRevocationReasonException
	 *             Thrown if the supplied reason is not one of the supported
	 *             revocation reasons.
	 * @throws AcmeCaException
	 *             If there was an error revoking the certificate.
	 */
	public void revokeCertificate(String reason) throws AcmeCaException;

	/**
	 * Updates the default SSL certificate. It is expected that if the default
	 * certificate is replaced, that both the {@link KeyStore} and the file are
	 * updated with the new certificate.
	 *
	 * @param keyStore
	 *            The {@link KeyStore} that contains the default certificate.
	 * @param keyStoreFile
	 *            The file where the {@link KeyStore} was loaded.
	 * @param password
	 *            The password to the {@link KeyStore}.
	 * @throws CertificateException
	 *             If there was an error updating the certificate.
	 * @see DefaultSSLCertificateCreator#updateDefaultSSLCertificate(KeyStore,
	 *      File, String)
	 */
	public void updateDefaultSSLCertificate(KeyStore keyStore, File keyStoreFile, @Sensitive String password)
			throws CertificateException;

}
