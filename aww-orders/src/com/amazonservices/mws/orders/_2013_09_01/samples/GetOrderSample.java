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
package com.amazonservices.mws.orders._2013_09_01.samples;

import java.util.ArrayList;
import java.util.List;

import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrders;
import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrdersClient;
import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrdersException;
import com.amazonservices.mws.orders._2013_09_01.model.GetOrderRequest;
import com.amazonservices.mws.orders._2013_09_01.model.GetOrderResponse;
import com.amazonservices.mws.orders._2013_09_01.model.ResponseHeaderMetadata;

/** Sample call for GetOrder. */
public class GetOrderSample {

	private String sellerId = "";
	private String marketplaceId = "";
	private String mwsAuthToken = null;
	private MarketplaceWebServiceOrdersClient client = null;
	private GetOrderRequest request = null;

	public GetOrderSample() {
		initRequest();
	}
	public GetOrderSample(String sellerID, String marketplaceId,
			String mwsAuthToken) {
		this.sellerId = sellerID;
		this.marketplaceId = marketplaceId;
		this.mwsAuthToken = mwsAuthToken;
		initRequest();
	}
	private void initRequest() {
		client = MarketplaceWebServiceOrdersSampleConfig.getClient();
		request = new GetOrderRequest();
		request.setSellerId(sellerId);
		if (mwsAuthToken != null)
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
	public GetOrderResponse invokeGetOrder(MarketplaceWebServiceOrders client,
			GetOrderRequest request) {
		try {
			// Call the service.
			GetOrderResponse response = client.getOrder(request);
			ResponseHeaderMetadata rhmd = response.getResponseHeaderMetadata();
			// We recommend logging every the request id and timestamp of every
			// call.
			System.out.println("Response:");
			System.out.println("RequestId: " + rhmd.getRequestId());
			System.out.println("Timestamp: " + rhmd.getTimestamp());
			String responseXml = response.toJSON();
			System.out.println(responseXml);
			return response;
		} catch (MarketplaceWebServiceOrdersException ex) {
			// Exception properties are important for diagnostics.
			System.out.println("Service Exception:");
			ResponseHeaderMetadata rhmd = ex.getResponseHeaderMetadata();
			if (rhmd != null) {
				System.out.println("RequestId: " + rhmd.getRequestId());
				System.out.println("Timestamp: " + rhmd.getTimestamp());
			}
			System.out.println("Message: " + ex.getMessage());
			System.out.println("StatusCode: " + ex.getStatusCode());
			System.out.println("ErrorCode: " + ex.getErrorCode());
			System.out.println("ErrorType: " + ex.getErrorType());
			throw ex;
		}
	}

	/**
	 * Command line entry point.
	 */
	public static void main(String[] args) {

		// Get a client connection.
		// Make sure you've set the variables in
		// MarketplaceWebServiceOrdersSampleConfig.
		// MarketplaceWebServiceOrdersClient client =
		// MarketplaceWebServiceOrdersSampleConfig.getClient();
		GetOrderSample order = new GetOrderSample();
		System.out.println(order.getOrderNumber("7838341-0494651"));

	}

	public String getOrderNumber(String orderNo){
		
		List<String> amazonOrderId = new ArrayList<String>();
		amazonOrderId.add(orderNo);
		request.setAmazonOrderId(amazonOrderId);

		// Make the call.
		return invokeGetOrder(client, request).toJSON();
	}
}
