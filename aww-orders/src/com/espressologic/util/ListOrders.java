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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import com.amazonservices.mws.client.MwsUtl;
import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrders;
import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrdersClient;
import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrdersException;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersRequest;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersResponse;
import com.amazonservices.mws.orders._2013_09_01.model.ResponseHeaderMetadata;
import com.amazonservices.mws.orders._2013_09_01.samples.MarketplaceWebServiceOrdersSampleConfig;


/** Sample call for ListOrders. */
public class ListOrders {

	private String marketPlaceId = "";
	private MarketplaceWebServiceOrdersClient client;
	private ListOrdersRequest request;
	private  String sellerId = "";
	private String mwsAuthToken = null;
   
   
	public ListOrders(){
		initRequest();
	}
	
    private void initRequest() {
    	 
    	this.client = MarketplaceWebServiceOrdersSampleConfig.getClient();
    	this.request = new ListOrdersRequest();
    	request.setSellerId(sellerId);
    	List<String> marketplaceId = new ArrayList<String>();
        marketplaceId.add(marketPlaceId);
        request.setMarketplaceId(marketplaceId);
    	if(mwsAuthToken != null)
    		request.setMWSAuthToken(mwsAuthToken);
    	
    	 List<String> orderStatus = new ArrayList<String>();
         orderStatus.add("Shipped");
         request.setOrderStatus(orderStatus);
         
        
         List<String> fulfillmentChannel = new ArrayList<String>();
         fulfillmentChannel.add("AFN");
         request.setFulfillmentChannel(fulfillmentChannel);
         List<String> paymentMethod = new ArrayList<String>();
         paymentMethod.add("Other");
         paymentMethod.add("CVS");
         request.setPaymentMethod(paymentMethod);
         Integer maxResultsPerPage = 1;
         request.setMaxResultsPerPage(maxResultsPerPage);
	}

	/**
     * Call the service, log response and exceptions.
     *
     * @param client
     * @param request
     *
     * @return The response.
     */
    public ListOrdersResponse invokeListOrders(
            MarketplaceWebServiceOrders client, 
            ListOrdersRequest request) {
        try {
            // Call the service.
            ListOrdersResponse response = client.listOrders(request);
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
        ListOrders orders = new ListOrders();
        // Make the call.
        orders.getOrdersByDate(new Date());

    }

    public String getOrdersByDate(Date date){
    	
    	XMLGregorianCalendar createdAfter = MwsUtl.getDTF().newXMLGregorianCalendar();
        createdAfter.setDay(1);
        createdAfter.setYear(2015);
        createdAfter.setMonth(1);
        request.setCreatedAfter(createdAfter);
        
        XMLGregorianCalendar createdBefore = MwsUtl.getDTF().newXMLGregorianCalendar();
        //request.setCreatedBefore(createdBefore);
        XMLGregorianCalendar lastUpdatedAfter = MwsUtl.getDTF().newXMLGregorianCalendar();
        //request.setLastUpdatedAfter(lastUpdatedAfter);
        XMLGregorianCalendar lastUpdatedBefore = MwsUtl.getDTF().newXMLGregorianCalendar();
        //request.setLastUpdatedBefore(lastUpdatedBefore);
        
        ListOrdersResponse response = invokeListOrders(client, request);
        /*
        String nextToken = response.getListOrdersResult().getNextToken();
        System.out.println("Next Token "+nextToken);
        
        int size = response.getListOrdersResult().getOrders().size();
        ListOrderItems details = new ListOrderItems();
        String amazonOrderId;
        for(int i=0; i < size ; i++){
        	System.out.println(response.getListOrdersResult().getOrders().get(i).getOrderTotal().getAmount());
        	amazonOrderId= response.getListOrdersResult().getOrders().get(i).getAmazonOrderId();
			details.getOrderDetails(amazonOrderId);
        }
        try{
            ListOrdersByNextToken orders = new ListOrdersByNextToken();
            orders.getNextOrderBatch(nextToken);
        } catch(Exception ex){ ex.printStackTrace(); }
        */
    	return response.toJSON();
    }
}
