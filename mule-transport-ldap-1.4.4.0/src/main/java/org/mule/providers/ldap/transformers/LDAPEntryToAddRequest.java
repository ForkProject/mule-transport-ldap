/*
 * $Id: LDAPEntryToAddRequest.java 60 2007-08-10 17:13:18Z hsaly $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.ldap.transformers;

import org.mule.providers.ldap.util.LDAPUtils;
import org.mule.transformers.AbstractTransformer;
import org.mule.umo.transformer.TransformerException;

import com.novell.ldap.LDAPAddRequest;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPSearchResult;

public class LDAPEntryToAddRequest extends AbstractTransformer
{

    // @Override
    protected Object doTransform(Object src, String encoding)
            throws TransformerException
    {

        if (src == null)
        {
            throw new TransformerException(this, new IllegalArgumentException(
                    "src must not be null"));
        }

        logger.debug(LDAPUtils.dumpLDAPMessage(src));

        if (!(src instanceof LDAPEntry || src instanceof LDAPSearchResult))
        {
            throw new TransformerException(this, new IllegalArgumentException(
                    "wrong type " + src.getClass()
                            + ". Only LDAPEntry/LDAPSearchResult is supported"));
        }

        try
        {

            LDAPEntry entry = null;

            if (src instanceof LDAPSearchResult)
            {

                LDAPSearchResult sr = (LDAPSearchResult) src;
                entry = sr.getEntry();

            }
            else
            {
                entry = (LDAPEntry) src;
            }

            // TODO LDAPCOntrols
            LDAPAddRequest request = new LDAPAddRequest(entry, null);

            // logger.debug("tranf: id: " + request.getMessageID());

            return request;
        }
        catch (LDAPException e)
        {
            throw new TransformerException(this, e);
        }

    }
}
