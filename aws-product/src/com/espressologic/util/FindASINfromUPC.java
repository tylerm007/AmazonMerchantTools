package com.espressologic.util;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.Response;

import com.amazonservices.mws.products.MarketplaceWebServiceProductsClient;
import com.amazonservices.mws.products.model.ASINListType;
import com.amazonservices.mws.products.model.GetMatchingProductForIdRequest;
import com.amazonservices.mws.products.model.GetMatchingProductForIdResponse;
import com.amazonservices.mws.products.model.GetMatchingProductRequest;
import com.amazonservices.mws.products.model.GetMatchingProductResponse;
import com.amazonservices.mws.products.model.IdListType;
import com.amazonservices.mws.products.samples.GetMatchingProductForIdSample;
import com.amazonservices.mws.products.samples.GetMatchingProductSample;
import com.amazonservices.mws.products.samples.MarketplaceWebServiceProductsSampleConfig;

public class FindASINfromUPC {

	private String sellerId = null;// "";
	private String marketplaceId = null;// "";
	private String mwsAuthToken = null;
	private GetMatchingProductForIdRequest request = null;
	private MarketplaceWebServiceProductsClient client = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Create a request.
		// GetMatchingProductRequest request = new GetMatchingProductRequest();
		FindASINfromUPC upcFinder = new FindASINfromUPC("",
				"", null);
		upcFinder.addUPC("790011020059");
		// Make the call.
		System.out.println(upcFinder.getResponse());
	}
	public FindASINfromUPC() {
		initRequest();
	}

	public FindASINfromUPC(String sellerID, String marketplaceId,
			String mwsAuthToken) {
		this.sellerId = sellerID;
		this.marketplaceId = marketplaceId;
		this.mwsAuthToken = mwsAuthToken;
		initRequest();
	}
	private void initRequest() {
		request = new GetMatchingProductForIdRequest();
		request.setSellerId(this.sellerId);
		if (this.mwsAuthToken != null)
			request.setMWSAuthToken(this.mwsAuthToken);

		request.setMarketplaceId(this.marketplaceId);
		this.client = MarketplaceWebServiceProductsSampleConfig.getClient();

	}
	public void addUPC(String upc) {
		// ASINListType asinList = new ASINListType();
		String idType = "UPC";
		request.setIdType(idType);
		IdListType idList = new IdListType();
		List<String> listUPC = new ArrayList<String>();
		listUPC.add(upc);
		idList.setId(listUPC);
		request.setIdList(idList);
	}

	public String getResponse() {
		GetMatchingProductForIdResponse resposne = GetMatchingProductForIdSample
				.invokeGetMatchingProductForId(client, this.request);
		// System.out.println(resposne.toJSON());
		// .GetMatchingProductResult.Product.Identifiers.MarketplaceASIN.ASIN);
		return resposne.toJSON();

	}

	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getMarketplaceId() {
		return marketplaceId;
	}
	public void setMarketplaceId(String marketplaceId) {
		this.marketplaceId = marketplaceId;
	}
	public String getMwsAuthToken() {
		return mwsAuthToken;
	}
	public void setMwsAuthToken(String mwsAuthToken) {
		this.mwsAuthToken = mwsAuthToken;
	}

}
