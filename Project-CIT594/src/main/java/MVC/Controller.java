package MVC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import javax.swing.JTable;

/**
 * Controller
 * 
 * @author Ruqing
 *
 */
public class Controller {
	Model model;
	View view;
	String keyword;
	String author;
	String sortOrder;
	HashMap<String, Book> books;

	/**
	 * Constructor
	 * 
	 * @param model
	 *            model
	 * @param view
	 *            view
	 */
	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
		this.view.addSearchButtonListener(new SearchButtonListener());
	}

	/**
	 * Button listener When search button is clicked, 1 call model to update the
	 * BookSet 2 show the result in view
	 *
	 */
	class SearchButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Controller.this.keyword = Controller.this.view.getKeywordInput();
			Controller.this.author = Controller.this.view.getAuthorInput();
			Controller.this.sortOrder = Controller.this.view.getSortOrder();

			try {
				Controller.this.model.update(Controller.this.keyword, Controller.this.author);
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}

			BookSet bookSet = BookSet.getInstance();
			Controller.this.books = bookSet.getBooks();

			Controller.this.view.clear();
			if (!Controller.this.books.isEmpty()) {
				Controller.this.view.display(sort(books));
			} else {
				Controller.this.view.display();
			}
		}

	}

	/**
	 * Sort the search result, either by lowest price or average price
	 * 
	 * @param books
	 *            a HashMap of search result
	 * @return sorted book set
	 */
	ArrayList<Book> sort(HashMap<String, Book> books) {
		Comparator<Book> lowestPriceComparator = new Comparator<Book>() {
			public int compare(Book a, Book b) {
				double d = a.getLowestPrice().doubleValue() - b.getLowestPrice().doubleValue();
				if (d > 0) {
					return 1;
				} else if (d < 0) {
					return -1;
				}
				return 0;
			}
		};
		Comparator<Book> averagePriceComparator = new Comparator<Book>() {
			public int compare(Book a, Book b) {
				double d = a.getAveragePrice().doubleValue() - b.getAveragePrice().doubleValue();
				if (d > 0) {
					return 1;
				} else if (d < 0) {
					return -1;
				}
				return 0;
			}
		};

		ArrayList<Book> sortedBooks = new ArrayList<Book>();
		sortedBooks.addAll(books.values());
		if (this.sortOrder.equals("lowest")) {
			Collections.sort(sortedBooks, lowestPriceComparator);
		} else if (this.sortOrder.equals("average")) {
			Collections.sort(sortedBooks, averagePriceComparator);
		}

		return sortedBooks;
	}
}
