package com.espressologic.util;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

public class SignatureUtil {

	private static final String CHARACTER_ENCODING = "UTF-8";
	final String ALGORITHM = "HmacSHA256";
	private String serviceUrl = "https://mws.amazonservices.com/"; //US
	private String version = "2009-01-01";
		
	private String secretKey = "";
	private String accessKey = "";
	private String sellerID = "";
	private String action = "GetFeedSubmissionList";
	private String msAuthToken = null;// "com.amazon.authtoken123123123132";
	private String submittedDT = "2015-02-21T12:00:00Z";//pass as formatted strings from client
	private String timestamp = "2015-02-21T16:00:00Z";
	
	public static void main(String[] args) throws Exception {
		// Change this secret key to yours
		SignatureUtil s = new SignatureUtil();
		 System.out.println(s.createURLwithSignature(s.secretKey, s.accessKey, s.sellerID,s.msAuthToken, s.action,s.submittedDT,s.timestamp));
		 System.out.println(s.createURLwithSignature());
		 String UPC = "790011020059";
		 System.out.println(s.findASINFromUPC(UPC));

	}
	public SignatureUtil(){
		
	}
	public SignatureUtil(String secretKey, String accessKey,
			String sellerID, String msAuthToken, String action,String submittedDT,String timestamp){
		this.secretKey = secretKey;
		this.accessKey = accessKey;
		this.sellerID = sellerID;
		this.action = action;
		this.msAuthToken = msAuthToken;
		this.submittedDT = submittedDT;
		this.timestamp = timestamp;
	}
	/**
	 * 
	 * @return
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @throws URISyntaxException
	 */
	public String createURLwithSignature() throws InvalidKeyException, SignatureException, NoSuchAlgorithmException, UnsupportedEncodingException, URISyntaxException{
		return createURLwithSignature(secretKey, accessKey,	sellerID, msAuthToken, action,submittedDT,timestamp);
	}
	public String findASINFromUPC(String UPC) throws InvalidKeyException, SignatureException, NoSuchAlgorithmException, UnsupportedEncodingException, URISyntaxException{
		this.serviceUrl =  null;
		//url += "Service=AWSECommerceService";
		String url = "";
		url += "Operation=ItemLookup";
		url += "&ResponseGroup=Large";
		url += "&SearchIndex=All";
		url += "&IdType=UPC";
		url += "&ItemId="+UPC;
		String response =  createURLwithSignature(secretKey, accessKey,	sellerID, msAuthToken, url ,submittedDT,timestamp);
		response  = response.replace("action=", "");
		response ="POST\n"+ "webservices.amazon.com/onca/xml/" + response;
		return response;
	}
	/*
	 * public method to create a URL with the signed signature and URI encode for each parameter value
	 * @param secretKey
	 * @param accessKey
	 * @param sellerID
	 * @param msAuthToken
	 * @param action
	 * @param submittedDT
	 * @param timestamp
	 * @return
	 * @throws SignatureException
	 * @throws URISyntaxException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws UnsupportedEncodingException
	 */
	public String createURLwithSignature(String secretKey, String accessKey,
			String sellerID, String msAuthToken, String action,String submittedDT,String timestamp)
			throws SignatureException, URISyntaxException,
			NoSuchAlgorithmException, InvalidKeyException,
			UnsupportedEncodingException {

		// Create set of parameters needed and store in a map
		HashMap<String, String> parameters = new HashMap<String, String>();
		// Add required parameters. Change these as needed.
		parameters.put("AWSAccessKeyId", urlEncode(accessKey));
		if(action.indexOf("&") == 0)
			parameters.put("Action", urlEncode(action));
		else
			parameters.put("Action",action);
		if(msAuthToken != null)
			parameters.put("MWSAuthToken", urlEncode(msAuthToken));//optional till 3/15
		parameters.put("SellerId", urlEncode(sellerID));
		parameters.put("SignatureMethod", urlEncode(ALGORITHM));
		parameters.put("SignatureVersion", urlEncode("2"));
		parameters.put("SubmittedFromDate", urlEncode(submittedDT));
		parameters.put("Timestamp", urlEncode(timestamp));
		parameters.put("Version", urlEncode(version));
		// Format the parameters as they will appear in final format
		// (without the signature parameter)
		String formattedParameters = calculateStringToSignV2(parameters,
				serviceUrl);
		String signature = sign(formattedParameters, secretKey);
		// Add signature to the parameters and display final results
		parameters.put("Signature", urlEncode(signature));
		return calculateStringToSignV2(parameters, serviceUrl);
	}
	/*
	 * If Signature Version is 2, string to sign is based on following:
	 * 
	 * 1. The HTTP Request Method followed by an ASCII newline (%0A)
	 * 
	 * 2. The HTTP Host header in the form of lowercase host, followed by an
	 * ASCII newline. Amazon Marketplace Web Service (Amazon MWS) Developer
	 * Guide 19 Amazon Marketplace Web Service (Amazon MWS) Developer Guide 19
	 * 3. The URL encoded HTTP absolute path component of the URI (up to but not
	 * including the query string parameters); if this is empty use a forward
	 * '/'. This parameter is followed by an ASCII newline.
	 * 
	 * 4. The concatenation of all query string components (names and values) as
	 * UTF-8 characters which are URL encoded as per RFC 3986 (hex characters
	 * MUST be uppercase), sorted using lexicographic byte ordering. Parameter
	 * names are separated from their values by the '=' character (ASCII
	 * character 61), even if the value is empty. Pairs of parameter and values
	 * are separated by the '&' character (ASCII code 38).
	 */
	private String calculateStringToSignV2(
			Map<String, String> parameters, String serviceUrl)
			throws SignatureException, URISyntaxException {
		// Sort the parameters alphabetically by storing
		// in TreeMap structure
		Map<String, String> sorted = new TreeMap<String, String>();
		sorted.putAll(parameters);
		// Set endpoint value
		StringBuilder data = new StringBuilder();
		if(serviceUrl != null){
			URI endpoint = new URI(serviceUrl.toLowerCase());
			// Create flattened (String) representation
			
			data.append("POST\n");
			data.append(endpoint.getHost());
			data.append("/");
		}
		data.append("\n");
		Iterator<Entry<String, String>> pairs = sorted.entrySet().iterator();
		while (pairs.hasNext()) {
			Map.Entry<String, String> pair = pairs.next();
			if (pair.getValue() != null) {
				data.append(pair.getKey() + "=" + pair.getValue());
			} else {
				data.append(pair.getKey() + "=");
			}
			// Delimit parameters with ampersand (&)
			if (pairs.hasNext()) {
				data.append("&");
			}
		}
		return data.toString();
	}
	/**
	 * Sign the text with the given secret key and convert to base64
	 * 
	 * @param data
	 * @param secretKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws IllegalStateException
	 * @throws UnsupportedEncodingException
	 */
	public String sign(String data, String secretKey)
			throws NoSuchAlgorithmException, InvalidKeyException,
			IllegalStateException, UnsupportedEncodingException {
		Mac mac = Mac.getInstance(ALGORITHM);
		mac.init(new SecretKeySpec(secretKey.getBytes(CHARACTER_ENCODING),
				ALGORITHM));
		byte[] signature = mac.doFinal(data.getBytes(CHARACTER_ENCODING));
		String signatureBase64 = new String(Base64.encodeBase64(signature),
				CHARACTER_ENCODING);

		return new String(signatureBase64);
	}
	/**
	 * 
	 * @param rawValue
	 * @return
	 */
	private String urlEncode(String rawValue) {
		String value = (rawValue == null) ? "" : rawValue;
		String encoded = null;
		try {
			encoded = URLEncoder.encode(value, CHARACTER_ENCODING)
					.replace("+", "%20").replace("*", "%2A")
					.replace("%7E", "~");
		} catch (UnsupportedEncodingException e) {
			System.err.println("Unknown encoding: " + CHARACTER_ENCODING);
			e.printStackTrace();
		}
		return encoded;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	public String getSellerID() {
		return sellerID;
	}
	public void setSellerID(String sellerID) {
		this.sellerID = sellerID;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getMsAuthToken() {
		return msAuthToken;
	}
	public void setMsAuthToken(String msAuthToken) {
		this.msAuthToken = msAuthToken;
	}
	public String getSubmittedDT() {
		return submittedDT;
	}
	public void setSubmittedDT(String submittedDT) {
		this.submittedDT = submittedDT;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getServiceUrl() {
		return serviceUrl;
	}
	public String getVersion() {
		return version;
	}
	
}