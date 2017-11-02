package com.amazonaws.samples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author XiangLi the class get the XML code by given the information such as
 *         index,keyword
 *
 */
public class GetXML {

	/**
	 * Your AWS Access Key ID, as taken from the AWS Your Account page.
	 */
	private static final String AWS_ACCESS_KEY_ID = "AKIAIMFNI6DQTQ3XMLTQ";

	/**
	 * Your AWS Secret Key corresponding to the above ID, as taken from the AWS
	 * Your Account page.
	 */
	private static final String AWS_SECRET_KEY = "GXabBc3gVMvbUWUIJ+afGNsmAhyWfnfbjy+HYjV6";

	/**
	 * Use the end-point according to the region.
	 */
	private static final String ENDPOINT = "webservices.amazon.com";

	public String requestUrl;
	public Map<String, String> params;
	public SignedRequestsHelper helper;

	/**
	 * the constructor of the class initial the instance variables
	 */
	public GetXML() {
		this.requestUrl = null;
		this.params = new HashMap<String, String>();
	}

	/**
	 * the class sign for the API in order to get information from it
	 */
	public void sign() {
		try {
			this.helper = SignedRequestsHelper.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		this.params.put("Service", "AWSECommerceService");
		this.params.put("Operation", "ItemSearch");
		this.params.put("AWSAccessKeyId", "AKIAIMFNI6DQTQ3XMLTQ");
		this.params.put("AssociateTag", "https://affiliate-program.amazon.com");
	}

	/**
	 * the method return the ArrayList contains the information filtered by
	 * certain factors
	 * 
	 * @param searchIndex
	 *            the category of the item
	 * @param brand
	 *            the brand of the item
	 * @param keyword
	 *            the keyword of the item
	 * @return ArrayList contains the information of the item in a XML form
	 */
	public ArrayList<String> getContent(String author, String keyword) {
		params.put("SearchIndex", "Books");
		if (!author.equals("")) {
			params.put("Author", author);
		}
		if (!keyword.equals("")) {
			params.put("Keywords", keyword);
		}
		params.put("ResponseGroup", "ItemAttributes,Reviews");
		requestUrl = helper.sign(params);
		// System.out.println(requestUrl);
		URLGetter get = new URLGetter(requestUrl);
		ArrayList<String> contents = get.getContents();
		return contents;

	}

	/**
	 * @return the url link as the String type
	 */
	public String getLink() {
		return this.requestUrl;
	}

}
