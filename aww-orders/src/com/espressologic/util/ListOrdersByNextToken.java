/*******************************************************************************
 * Copyright 2009-2014 Amazon Services. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 *
 * You may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at: http://aws.amazon.com/apache2.0
 * This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the 
 * specific language governing permissions and limitations under the License.
 *******************************************************************************
 * Marketplace Web Service Orders
 * API Version: 2013-09-01
 * Library Version: 2014-09-30
 * Generated: Thu Oct 02 16:23:29 GMT 2014
 */
package com.espressologic.util;

import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrders;
import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrdersClient;
import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrdersException;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersByNextTokenRequest;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersByNextTokenResponse;
import com.amazonservices.mws.orders._2013_09_01.model.ResponseHeaderMetadata;
import com.amazonservices.mws.orders._2013_09_01.samples.MarketplaceWebServiceOrdersSampleConfig;


/** Sample call for ListOrdersByNextToken. */
public class ListOrdersByNextToken {

	private MarketplaceWebServiceOrdersClient client = null;
	private ListOrdersByNextTokenRequest request = null;
	private String sellerId = "";
    
    private String mwsAuthToken = null;
    
   
    public ListOrdersByNextToken(){
    	initRequest();
    }
	
    private void initRequest() {
    	client = MarketplaceWebServiceOrdersSampleConfig.getClient();
        request = new ListOrdersByNextTokenRequest();
    	request.setSellerId(sellerId);
    	if(mwsAuthToken != null)
    		request.setMWSAuthToken(mwsAuthToken);
		
	}

	/**
     * Call the service, log response and exceptions.
     *
     * @param client
     * @param request
     *
     * @return The response.
     */
    public ListOrdersByNextTokenResponse invokeListOrdersByNextToken(
            MarketplaceWebServiceOrders client, 
            ListOrdersByNextTokenRequest request) {
        try {
            // Call the service.
            ListOrdersByNextTokenResponse response = client.listOrdersByNextToken(request);
            ResponseHeaderMetadata rhmd = response.getResponseHeaderMetadata();
            // We recommend logging every the request id and timestamp of every call.
            System.out.println("Response:");
            System.out.println("RequestId: "+rhmd.getRequestId());
            System.out.println("Timestamp: "+rhmd.getTimestamp());
            String responseXml = response.toJSON();
            System.out.println(responseXml);
            return response;
        } catch (MarketplaceWebServiceOrdersException ex) {
            // Exception properties are important for diagnostics.
            System.out.println("Service Exception:");
            ResponseHeaderMetadata rhmd = ex.getResponseHeaderMetadata();
            if(rhmd != null) {
                System.out.println("RequestId: "+rhmd.getRequestId());
                System.out.println("Timestamp: "+rhmd.getTimestamp());
            }
            System.out.println("Message: "+ex.getMessage());
            System.out.println("StatusCode: "+ex.getStatusCode());
            System.out.println("ErrorCode: "+ex.getErrorCode());
            System.out.println("ErrorType: "+ex.getErrorType());
            throw ex;
        }
    }

    /**
     *  Command line entry point.
     */
    public static void main(String[] args) {
    	ListOrdersByNextToken orders = new ListOrdersByNextToken();
        String nextToken = "YqmY8B835RmaJqJYLDm0ZIfVkJJPpovR3bYOcqlvP25UojdU4H46trQzazHyYVyLqBXdLk4iogxpJASl2BeRe0jN54oCK1de4t8B0mC7LABZLzoYdi6YY1YBOIkb7gdUdhqTXITvrVRRqTCeN410nbuZIF9n45mtmcSktMlDTnJa36ByM5ujsZdzVJmHEB5iyRYTCs2sOOR1t2x/XkuNgt8ytOVgN7d/KyNtf5fepe35f7M4XOPz9j6eiP9Y+BT20Ohfs01+ZSLF9uK7SGGtHpyJuFT4H09qAu/zWiTtcy9vmnig52KFYBP6VJKSX0vIk3oDQ91UqbMD2s1QD6a1FUgTdklvzmcsZvsy0W2YjOIJekvH//LaoWOxiEhJ6A57nuTtM4omD8BhcJ1giq9YH6u5DKnuDKkX02T8nqapWuStOgCxg3zewcczaxUFzzz30TGpiVOqOhXnY+giBuS+iJ7ZbA9tWo1ys4cvZkVwqWlePHLAq7hnvK1OyEkQXs58CirdwBUL/X956Z9owJldsQ==";
        
        // Make the call.
        orders.getNextOrderBatch(nextToken);

    }
    public String getNextOrderBatch(String nextToken){
    	request.setNextToken(nextToken);
    	return invokeListOrdersByNextToken(client, request).toJSON();
    	
    }

}
