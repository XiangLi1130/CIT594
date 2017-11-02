package MVC;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Book class, keeping track of book's name, author, price and link.
 * 
 * @author Ruqing
 *
 */
public class Book {
	private String ISBN;
	private String title;
	private ArrayList<String> author;
	private HashMap<Website, Double> price;
	private HashMap<Website, URL> link;

	/**
	 * Constructor
	 * 
	 * @param iSBN
	 *            isbn
	 */
	public Book(String iSBN) {
		super();
		this.ISBN = iSBN;
		this.author = new ArrayList<String>();
		this.price = new HashMap<Website, Double>();
		this.link = new HashMap<Website, URL>();
	}

	/**
	 * Return title of the book
	 * 
	 * @return title of the book
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Set the title of the book
	 * 
	 * @param title
	 *            title of the book
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Return authors
	 * 
	 * @return authors
	 */
	public ArrayList<String> getAuthor() {
		return this.author;
	}

	/**
	 * Set the authors of the book
	 * 
	 * @param author
	 *            author
	 */
	public void setAuthor(ArrayList<String> author) {
		this.author = author;
	}

	/**
	 * Return a HashMap of web site and price
	 * 
	 * @return a HashMap of web site and price
	 */
	public HashMap<Website, Double> getPrice() {
		return this.price;
	}

	/**
	 * Add (website,price) pair
	 * 
	 * @param website
	 *            web site
	 * @param price
	 *            price
	 */
	public void setPrice(Website website, Double price) {
		this.price.put(website, price);
	}

	/**
	 * Return a HashMap of web site and its URL
	 * 
	 * @return a HashMap of web site and its URL
	 */
	public HashMap<Website, URL> getLink() {
		return this.link;
	}

	/**
	 * Add (website,link) pair
	 * 
	 * @param website
	 *            web site
	 * @param link
	 *            link
	 */
	public void setLink(Website website, URL link) {
		this.link.put(website, link);
	}

	/**
	 * Return ISBN
	 * 
	 * @return ISBN
	 */
	public String getISBN() {
		return this.ISBN;
	}

	/**
	 * Return the lowest price among different web sites
	 * 
	 * @return the lowest price among different web sites
	 */
	public Double getLowestPrice() {
		ArrayList<Double> list = new ArrayList<Double>();
		list.addAll(this.price.values());
		Collections.sort(list);
		return list.get(0);
	}

	/**
	 * Return the average price among all web sites
	 * 
	 * @return the average price among all web sites
	 */
	public Double getAveragePrice() {
		double d = 0;
		for (Double p : this.price.values()) {
			d += p.doubleValue();
		}
		return Double.valueOf(d / this.price.size());
	}

}
