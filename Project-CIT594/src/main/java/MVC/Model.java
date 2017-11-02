package MVC;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.amazonaws.samples.AmazonItemInformation;
import com.amazonaws.samples.GetXML;

import ebay.EbayItemInformation;
import ebay.EbayXMLGetter;

/**
 * The model class
 * 
 * @author XiangLi
 * 
 */
public class Model {

	/**
	 * The method handle the user Input and run to pass all the information to
	 * the bookset
	 * 
	 * @param brand
	 *            the name of the book
	 * @param author
	 *            the author of the book
	 */
	public void update(String brand, String author) throws MalformedURLException {

		BookSet bookSet = BookSet.getInstance();
		bookSet.initialize();

		GetXML getAmazon = new GetXML();
		getAmazon.sign();
		ArrayList<String> contents = getAmazon.getContent(author, brand);
		String content = contents.get(0);
		AmazonItemInformation amazonInfo = new AmazonItemInformation(content);
		amazonInfo.getInformation();

		HashMap<String, Book> info = BookSet.getInstance().getBooks();
		Set<String> keys = info.keySet();
		for (String isbn : keys) {
			EbayXMLGetter ebay = new EbayXMLGetter();
			String ebaycontent = ebay.content(isbn);
			EbayItemInformation ebayInfo = new EbayItemInformation(isbn, ebaycontent);
			ebayInfo.setNewBookInfo();
			ebayInfo.setOldBookInfo();
		}

	}

}
