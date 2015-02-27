/******************************************************************************* 
 *  Copyright 2009 Amazon Services.
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 *  
 *  You may not use this file except in compliance with the License. 
 *  You may obtain a copy of the License at: http://aws.amazon.com/apache2.0
 *  This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
 *  CONDITIONS OF ANY KIND, either express or implied. See the License for the 
 *  specific language governing permissions and limitations under the License.
 * ***************************************************************************** 
 *
 *  Marketplace Web Service Java Library
 *  API Version: 2009-01-01
 *  Generated: Wed Feb 18 13:28:48 PST 2009 
 * 
 */

package com.amazonaws.mws.samples;

import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.amazonaws.mws.*;
import com.amazonaws.mws.model.*;
import com.amazonaws.mws.mock.MarketplaceWebServiceMock;

/**
 *
 * Get Report Samples
 *
 *
 */
public class GetReportSample {

	private String accessKeyId = "<Your Access Key ID>";
	private String secretAccessKey = "<Your Secret Access Key>";

	private String appName = "<Your Application or Company Name>";
	private String appVersion = "<Your Application Version or Build Number or Release Date>";
	private MarketplaceWebService service = null;
	private String merchantId = "<Your Merchant ID>";
	private String sellerDevAuthToken = "<Merchant Developer MWS Auth Token>";
	MarketplaceWebServiceConfig config = new MarketplaceWebServiceConfig();
	
	
	public GetReportSample(){
		this.initProperties();
	}
	/**
	 * Just add a few required parameters, and try the service Get Report
	 * functionality
	 *
	 * @param args
	 *            unused
	 */
	public static void main(String... args) {

		GetReportSample report = new GetReportSample();
		String reportId = "24047856323";
		report.getReports(reportId);
		

	}

	private void initProperties() {
		config.setServiceURL("https://mws.amazonservices.com");
		Properties prop = new Properties();
		FileInputStream is;
		try {
			is = new FileInputStream("Amazon.properties");

			prop.load(is);// ("./Amazon.properties");
			accessKeyId = prop.getProperty("accessKeyId");
			secretAccessKey = prop.getProperty("secretAccessKey");
			appName = prop.getProperty("appName");
			appVersion = prop.getProperty("appVersion");
			merchantId = prop.getProperty("sellerID");
			sellerDevAuthToken = prop.getProperty("marketPlaceID");
			service = new MarketplaceWebServiceClient(accessKeyId,
					secretAccessKey, appName, appVersion, config);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void getReports(String reportId){
		GetReportRequest request = new GetReportRequest();
		request.setMerchant(merchantId);
		request.setMWSAuthToken(sellerDevAuthToken);
		//
		request.setReportId(reportId);

		
		OutputStream report;
		try {
			report = new FileOutputStream("report"+reportId+".xml");

			request.setReportOutputStream(report);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		invokeGetReport(service, request);
	}
	/**
	 * Get Report request sample The GetReport operation returns the contents of
	 * a report. Reports can potentially be very large (>100MB) which is why we
	 * only return one report at a time, and in a streaming fashion.
	 * 
	 * @param service
	 *            instance of MarketplaceWebService service
	 * @param request
	 *            Action to invoke
	 */
	public static void invokeGetReport(MarketplaceWebService service,
			GetReportRequest request) {
		try {

			GetReportResponse response = service.getReport(request);
			
			System.out.println("GetReport Action Response");
			System.out
					.println("=============================================================================");
			System.out.println();

			System.out.print("    GetReportResponse");
			System.out.println();
			System.out.print("    GetReportResult");
			System.out.println();
			System.out.print("            MD5Checksum");
			System.out.println();
			System.out.print("                "
					+ response.getGetReportResult().getMD5Checksum());
			System.out.println();
			if (response.isSetResponseMetadata()) {
				System.out.print("        ResponseMetadata");
				System.out.println();
				ResponseMetadata responseMetadata = response
						.getResponseMetadata();
				if (responseMetadata.isSetRequestId()) {
					System.out.print("            RequestId");
					System.out.println();
					System.out.print("                "
							+ responseMetadata.getRequestId());
					System.out.println();
				}
			}
			System.out.println();

			System.out.println("Report");
			System.out
					.println("=============================================================================");
			System.out.println();
			System.out.println(request.getReportOutputStream().toString());
			System.out.println();

			System.out.println(response.getResponseHeaderMetadata());
			System.out.println();
			System.out.println(response.toJSON());

		} catch (MarketplaceWebServiceException ex) {

			System.out.println("Caught Exception: " + ex.getMessage());
			System.out.println("Response Status Code: " + ex.getStatusCode());
			System.out.println("Error Code: " + ex.getErrorCode());
			System.out.println("Error Type: " + ex.getErrorType());
			System.out.println("Request ID: " + ex.getRequestId());
			System.out.print("XML: " + ex.getXML());
			System.out.println("ResponseHeaderMetadata: "
					+ ex.getResponseHeaderMetadata());
		}
	}

}
