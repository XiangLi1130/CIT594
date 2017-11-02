package ebay;


import java.net.URL;
import java.util.ArrayList;

import MVC.BookSet;
import MVC.Website;

/**
 * @author XiangLi the class get the information of the searching items from the
 *         given string contains the information in XML form
 *
 */
public class EbayItemInformation {

	/** the ISBN of the book */
	String ISBN;

	/** the content of the searching result */
	String xmlcontent;

	/** separate the information to each index on item */
	String[] separateInfo;

	/** the state of the book, true for brand new, false for second hand */
	boolean[] state;

	/**
	 * the constructor of the class
	 * 
	 * @param xmlcontent
	 *            the information of the searching items in XML form
	 */
	public EbayItemInformation(String ISBN, String xmlcontent) {

		this.ISBN = ISBN;

		this.xmlcontent = xmlcontent;

		this.separateInfo = this.xmlcontent.split("<item>");

		this.state = getStateOfBook();

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
	 * the method return Array which contains the information of the state of
	 * the book brand new/second hand
	 * 
	 * @return the state of the book
	 */
	public boolean[] getStateOfBook() {
		boolean[] state = new boolean[this.separateInfo.length - 1];

		for (int i = 1; i < this.separateInfo.length; i++) {
			if (this.separateInfo[i].contains("Brand New")) {
				state[i - 1] = true;
			} else {
				state[i - 1] = false;
			}
		}
		return state;
	}

	/**
	 * the method get the information for the brand new books and add these to
	 * bookset
	 */
	public void setNewBookInfo() {
		try {
			Website web = Website.EBAYBRANDNEW;
			BookSet bookset = BookSet.getInstance();
			bookset.setVendor(web);
			double price = 10000;
			String link = "no brand new item found";
			ArrayList<String> author = new ArrayList<String>();
			String title = "";

			if (this.separateInfo.length > 1) {
				int j = 1;
				for (int i = 1; i < this.separateInfo.length; i++) {
					if (this.separateInfo[i].contains("<currentPrice currencyId=\"USD\">") && state[i - 1] == true) {
						price = Double.parseDouble(getInside(this.separateInfo[i], "<currentPrice currencyId=\"USD\">",
								"</currentPrice>"));
						link = getInside(this.separateInfo[i], "<viewItemURL>", "</viewItemURL>");
						j = i;
						break;
					}
				}

				for (int i = j; i < this.separateInfo.length; i++) {
					if (state[i - 1] == true) {
						if (this.separateInfo[i].contains("<currentPrice currencyId=\"USD\">")) {
							Double price1 = Double.parseDouble(getInside(this.separateInfo[i],
									"<currentPrice currencyId=\"USD\">", "</currentPrice>"));

							if (price1 < price) {
								price = price1;
								String link1 = getInside(this.separateInfo[i], "<viewItemURL>", "</viewItemURL>");
								link = link1;
							}
						}
					}
				}

			}
			if (!(link.equals("no brand new item found")) && price != 10000) {
				URL lin = new URL(link);
				bookset.add(this.ISBN, title, author, price, lin);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * the method get the information for second hand books and store these to
	 * the bookset
	 */
	public void setOldBookInfo() {
		try {
			Website web = Website.EBAYSECONDHAND;
			BookSet bookset = BookSet.getInstance();
			bookset.setVendor(web);
			double price = 10000;
			String link = "no second hand item found";
			ArrayList<String> author = new ArrayList<String>();
			String title = "";

			if (this.separateInfo.length > 1) {
				int j = 1;
				for (int i = 1; i < this.separateInfo.length; i++) {
					if (this.separateInfo[i].contains("<currentPrice currencyId=\"USD\">") && state[i - 1] == false) {
						price = Double.parseDouble(getInside(this.separateInfo[i], "<currentPrice currencyId=\"USD\">",
								"</currentPrice>"));
						link = getInside(this.separateInfo[i], "<viewItemURL>", "</viewItemURL>");
						j = i;
						break;
					}
				}

				for (int i = j; i < this.separateInfo.length; i++) {
					if (state[i - 1] == false) {
						if (this.separateInfo[i].contains("<currentPrice currencyId=\"USD\">")) {
							Double price1 = Double.parseDouble(getInside(this.separateInfo[i],
									"<currentPrice currencyId=\"USD\">", "</currentPrice>"));

							if (price1 < price) {
								price = price1;
								String link1 = getInside(this.separateInfo[i], "<viewItemURL>", "</viewItemURL>");
								link = link1;
							}
						}
					}
				}

			}
			if (!(link.equals("no second hand item found")) && price != 10000) {
				URL lin = new URL(link);
				bookset.add(this.ISBN, title, author, price, lin);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
