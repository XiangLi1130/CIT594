package com.amazonaws.samples;

import java.net.URL;
import java.util.ArrayList;

import MVC.BookSet;
import MVC.Website;

/**
 * @author XiangLi the class pass get the information of the items from xml and
 *         pass the information to bookset
 */
public class AmazonItemInformation {

	String xmlContent;

	/**
	 * the constructor
	 * 
	 * @param xmlContent
	 *            the content in XML form
	 */
	public AmazonItemInformation(String xmlContent) {
		this.xmlContent = xmlContent;

	}

	private String[] individualInfo() {
		return this.xmlContent.split("<Item>");
	}

	/**
	 * the method return the String between the tag and its closest close tag
	 * 
	 * @param tag
	 * @return
	 */
	private String getInside(String content, String tag, String closetag) {

		String sub1 = content.split(tag)[1].trim();
		String sub2 = sub1.split(closetag)[0].trim();
		return sub2;

	}

	/**
	 * the method returns the ArrayList of Strings between the tag and its
	 * closest close tag if there are more than one same tag
	 * 
	 * @param content
	 * @param tag
	 * @param closetag
	 * @return the ArrayList of Strings between the tag and its closest close
	 *         tag
	 */
	private ArrayList<String> getInsides(String content, String tag, String closetag) {
		String[] subs = content.split(tag);
		ArrayList<String> inside = new ArrayList<String>();
		for (int i = 1; i < subs.length; i++) {
			String author = subs[i].split(closetag)[0].trim();
			inside.add(author);
		}
		return inside;

	}

	/**
	 * the method get the information of the searching result of the books and
	 * pass the information to bookset
	 */
	public void getInformation() {
		try {
			Website web = Website.AMAZON;
			BookSet bookset = BookSet.getInstance();
			bookset.setVendor(web);
			String[] info = individualInfo();
			for (int i = 1; i < info.length; i++) {
				if (info[i].contains("<ListPrice>") && info[i].contains("<ISBN>") && info[i].contains("<DetailPageURL>")
						&& info[i].contains("<Author>") && info[i].contains("<Title>")) {
					String lin = getInside(info[i], "<DetailPageURL>", "</DetailPageURL>");
					URL link = new URL(lin);
					ArrayList<String> author = getInsides(info[i], "<Author>", "</Author>");
					String ISBN = getInside(info[i], "<ISBN>", "</ISBN>");
					String listprice = getInside(info[i], "<ListPrice>", "</ListPrice>");
					Double price = Double
							.parseDouble(getInside(listprice, "<FormattedPrice>", "</FormattedPrice>").substring(1));
					String title = getInside(info[i], "<Title>", "</Title>");
					bookset.add(ISBN, title, author, price, link);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
