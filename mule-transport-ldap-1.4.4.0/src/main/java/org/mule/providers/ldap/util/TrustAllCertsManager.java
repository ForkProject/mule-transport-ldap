/*
 * $Id: TrustAllCertsManager.java 59 2007-08-10 17:00:07Z hsaly $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.ldap.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class TrustAllCertsManager implements X509TrustManager
{

    public static TrustManager[] getTrustAllCertsManager()
    {
        return new TrustManager[]
        {new TrustAllCertsManager()};
    }

    public void checkClientTrusted(X509Certificate[] chain, String authType)
            throws CertificateException
    {

    }

    public void checkServerTrusted(X509Certificate[] chain, String authType)
            throws CertificateException
    {

    }

    public X509Certificate[] getAcceptedIssuers()
    {

        return new X509Certificate[]
        {};
    }

}
