/*
 * $Id: LdapResponseCorrelationAggregator.java 61 2007-08-10 17:55:50Z hsaly $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.ldap.routers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.mule.impl.MuleMessage;
import org.mule.providers.ldap.util.LDAPUtils;
import org.mule.routing.inbound.EventGroup;
import org.mule.routing.response.ResponseCorrelationAggregator;
import org.mule.umo.UMOEvent;
import org.mule.umo.UMOMessage;
import org.mule.umo.routing.RoutingException;
import org.mule.umo.transformer.TransformerException;

import com.novell.ldap.LDAPMessage;
import com.novell.ldap.LDAPResponse;

public class LdapResponseCorrelationAggregator extends
        ResponseCorrelationAggregator
{

    // @Override
    protected boolean shouldAggregateEvents(EventGroup events)
    {

        synchronized (events)
        {

            Iterator it = events.iterator();
            while (it.hasNext())
            {
                UMOEvent event = (UMOEvent) it.next();

                try
                {
                    logger.debug(">--- aggregator should ---> "
                            + LDAPUtils.dumpLDAPMessage(event
                                    .getTransformedMessage()));

                    if (event.getTransformedMessage() instanceof LDAPResponse)
                    {
                        // last message is a LDapResponse
                        logger
                                .debug(">--- aggregator should ---> shouldAggregateEvents return true");
                        return true;
                    }
                }
                catch (TransformerException e)
                {
                    throw new RuntimeException(e);
                }
            }

            return false;
        }
    }

    // @Override
    protected UMOMessage aggregateEvents(EventGroup events)
            throws RoutingException
    {

        logger.debug(">--- aggregator enter aggregateEvents ---> ");

        LDAPMessage msg = null;
        UMOEvent event = null;
        List results = new ArrayList();

        try
        {
            for (Iterator iterator = events.iterator(); iterator.hasNext();)
            {
                event = (UMOEvent) iterator.next();
                msg = (LDAPMessage) event.getTransformedMessage();

                results.add(msg);

            }
        }
        catch (TransformerException e)
        {
            logger.error(e);

            if (event != null)
            {
                throw new RoutingException(event.getMessage(), event
                        .getEndpoint());
            }

            throw new RuntimeException(e);
        }

        if (event != null)
        {

            logger.debug(">--- aggregator leave  aggregateEvents with "
                    + results.size() + " msgs ---> ");
            return new MuleMessage(results, event.getMessage());
        }

        throw new RuntimeException("event is null");
    }

}
