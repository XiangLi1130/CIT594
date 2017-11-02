package MVC;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Book set. Keep track of all the search result. Singleton.
 * 
 * @author Ruqing
 *
 */
public class BookSet {
	private HashMap<String, Book> books;
	private static BookSet bookSet;
	private Website website;

	/**
	 * Constructor
	 */
	private BookSet() {
		initialize();
	}

	/**
	 * Initialization
	 */
	public void initialize() {
		this.books = new HashMap<String, Book>();
		this.website = null;
	}

	/**
	 * Return the singleton object
	 * 
	 * @return the singleton object
	 */
	public static BookSet getInstance() {
		if (bookSet == null) {
			bookSet = new BookSet();
		}
		return bookSet;
	}

	/**
	 * Return a HashMap of all book searched
	 * 
	 * @return a HashMap of all book searched
	 */
	public HashMap<String, Book> getBooks() {
		return this.books;
	}

	/**
	 * Set the vendor
	 * 
	 * @param website
	 *            vendor
	 */
	public void setVendor(Website website) {
		this.website = website;
	}

	/**
	 * Add one search result.
	 * 
	 * @param ISBN
	 *            ISBN of the book
	 * @param title
	 *            title of the book
	 * @param author
	 *            a ArrayList of authors
	 * @param price
	 *            price of the book on particular web site
	 * @param link
	 *            link to purchase
	 */
	public void add(String ISBN, String title, ArrayList<String> author, Double price, URL link) {
		Book book;
		if (this.books.containsKey(ISBN)) {
			book = this.books.get(ISBN);
		} else {
			book = new Book(ISBN);
			this.books.put(ISBN, book);
			book.setAuthor(author);
			book.setTitle(title);
		}
		book.setPrice(this.website, price);
		book.setLink(this.website, link);
	}

}
