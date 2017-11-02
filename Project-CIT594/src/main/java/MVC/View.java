package MVC;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

/**
 * View class
 * 
 * @author Ruqing
 *
 */
public class View extends JPanel {
	private static final int FRAME_WIDTH = 1000;
	private static final int FRAME_HEIGHT = 1000;

	private Model model;

	JFrame frame;
	private JButton searchButton;
	private JTextField authorInput;
	private JTextField keywordInput;
	private JRadioButton lowestPriceSortButton;
	private JRadioButton averagePriceSortButton;
	private JScrollPane bookScrollPane;
	private JPanel bookPanel;

	/**
	 * Draw frame
	 */
	public View() {
		this.frame = new JFrame();
		this.frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setTitle("PriceMatch");
		this.frame.setResizable(false);
		this.frame.getContentPane().setLayout(new FlowLayout());

		JPanel titlePanel = createTitlePanel();
		JPanel searchConditionPanel = createSearchConditionPanel();
		JPanel sortOrderButtonPanel = createSortOrderButtonPanel();
		JPanel searchButtonPanel = createSearchButtonPanel();
		JPanel resultHeaderPanel = createResultHeaderPanel();

		this.bookPanel = new JPanel();
		this.bookPanel.setPreferredSize(new Dimension(FRAME_WIDTH, 880));

		this.bookScrollPane = new JScrollPane(this.bookPanel);

		this.frame.add(titlePanel);
		this.frame.add(searchConditionPanel);
		this.frame.add(sortOrderButtonPanel);
		this.frame.add(searchButtonPanel);
		this.frame.add(resultHeaderPanel);
		this.frame.add(this.bookScrollPane);

		this.frame.setVisible(true);

	}

	/**
	 * Add listener to search button
	 * 
	 * @param listener
	 *            listener
	 */
	public void addSearchButtonListener(ActionListener listener) {
		this.searchButton.addActionListener(listener);
	}

	/**
	 * Return book title keyword
	 * 
	 * @return keyword
	 */
	public String getKeywordInput() {
		return this.keywordInput.getText();
	}

	/**
	 * Return author keyword
	 * 
	 * @return author
	 */
	public String getAuthorInput() {
		return this.authorInput.getText();
	}

	/**
	 * Return specified sort order
	 * 
	 * @return specified sort order
	 */
	public String getSortOrder() {
		if (this.lowestPriceSortButton.isSelected()) {
			return "lowest";
		} else {
			return "average";
		}
	}

	/**
	 * Display search result
	 * 
	 * @param sortedBooks
	 *            search result
	 */
	public void display(ArrayList<Book> sortedBooks) {
		ArrayList<JPanel> bookInfos = createBookInfoPanel(sortedBooks);

		this.bookPanel = new JPanel();
		this.bookPanel.setPreferredSize(new Dimension(FRAME_WIDTH, 880));
		for (JPanel bookInfo : bookInfos) {
			this.bookPanel.add(bookInfo);
		}
		this.bookScrollPane.setPreferredSize(new Dimension(FRAME_WIDTH, 880));
		this.bookScrollPane = new JScrollPane(this.bookPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.frame.add(this.bookScrollPane);
		this.frame.setVisible(true);
	}

	/**
	 * Display "no result".
	 */
	public void display() {
		JLabel noResultLabel = new JLabel("No result.");
		this.bookPanel = new JPanel();
		this.bookPanel.setPreferredSize(new Dimension(FRAME_WIDTH, 880));
		this.bookPanel.add(noResultLabel);
		this.bookScrollPane = new JScrollPane(this.bookPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.frame.add(this.bookScrollPane);
		this.frame.setVisible(true);
	}

	/**
	 * Clear result panel
	 */
	public void clear() {
		this.frame.remove(this.bookScrollPane);
	}

	/**
	 * Return a arrayList of bookInfo
	 * 
	 * @param sortedBooks
	 *            search result
	 * @return a arrayList of bookInfo
	 */
	private ArrayList<JPanel> createBookInfoPanel(ArrayList<Book> sortedBooks) {
		int LABEL_W = 195;
		int LABEL_H = 50;
		ArrayList<JPanel> bookInfos = new ArrayList<JPanel>();

		for (int i = 0; i < Math.min(sortedBooks.size(), 9); i++) {
			Book book = sortedBooks.get(i);

			StringBuilder authorString = new StringBuilder();
			authorString.append("<html>");
			for (String a : book.getAuthor()) {
				authorString.append(a + "; ");
			}
			authorString.append("</html>");

			ArrayList<Website> websites = new ArrayList<Website>();
			websites.addAll(book.getPrice().keySet());

			StringBuilder webString = new StringBuilder();
			webString.append("<html>");
			StringBuilder priceString = new StringBuilder();
			priceString.append("<html>");
			JPanel purchaseLink = new JPanel(new GridLayout(book.getLink().size(), 1));
			for (Website w : websites) {
				webString.append(w.websiteName() + "<br>");
				priceString.append(book.getPrice().get(w) + "<br>");
				purchaseLink.add(new LinkLabel("Click to buy", book.getLink().get(w)));

			}
			JLabel author = new JLabel(authorString.toString().length() > 65 ? authorString.toString().substring(0, 65)
					: authorString.toString());
			String bookTitleString = book.getTitle().length() > 65 ? book.getTitle().substring(0, 65) : book.getTitle();
			JLabel title = new JLabel("<html>" + bookTitleString + "</html>");
			JLabel website = new JLabel(webString.toString());
			JLabel price = new JLabel(priceString.toString());

			title.setPreferredSize(new Dimension(LABEL_W, LABEL_H));
			author.setPreferredSize(new Dimension(LABEL_W, LABEL_H));
			website.setPreferredSize(new Dimension(LABEL_W, LABEL_H));
			price.setPreferredSize(new Dimension(LABEL_W, LABEL_H));
			purchaseLink.setPreferredSize(new Dimension(LABEL_W, LABEL_H));

			JPanel bookInfo = new JPanel();
			bookInfo.add(title);
			bookInfo.add(author);
			bookInfo.add(website);
			bookInfo.add(price);
			bookInfo.add(purchaseLink);
			bookInfos.add(bookInfo);
		}
		return bookInfos;
	}

	/**
	 * Return title Panel
	 * 
	 * @return title Panel
	 */
	private JPanel createTitlePanel() {
		JLabel titleLabel = new JLabel("Book Price Search");

		JPanel titlePanel = new JPanel();
		titlePanel.setPreferredSize(new Dimension(FRAME_WIDTH, 20));

		titlePanel.add(titleLabel);
		return titlePanel;
	}

	/**
	 * Return search condition Panel
	 * 
	 * @return search condition Panel
	 */
	private JPanel createSearchConditionPanel() {
		this.authorInput = new JTextField(10);
		this.authorInput.setText("Author");
		this.keywordInput = new JTextField(10);
		this.keywordInput.setText("Book title");

		JPanel searchConditionPanel = new JPanel();
		searchConditionPanel.setPreferredSize(new Dimension(1000, 20));
		searchConditionPanel.setLayout(new GridLayout(1, 2));

		searchConditionPanel.add(this.authorInput);
		searchConditionPanel.add(this.keywordInput);

		return searchConditionPanel;
	}

	/**
	 * Return sort order button panel
	 * 
	 * @return sort order button panel
	 */
	private JPanel createSortOrderButtonPanel() {
		JPanel sortOrderButtonPanel = new JPanel();
		sortOrderButtonPanel.setPreferredSize(new Dimension(FRAME_WIDTH, 30));

		this.lowestPriceSortButton = new JRadioButton("Sort by lowest price");
		this.averagePriceSortButton = new JRadioButton("Sort by average price");

		ButtonGroup sortOrderButtonGroup = new ButtonGroup();
		this.lowestPriceSortButton.setSelected(true);
		sortOrderButtonGroup.add(this.lowestPriceSortButton);
		sortOrderButtonGroup.add(this.averagePriceSortButton);

		sortOrderButtonPanel.add(this.lowestPriceSortButton);
		sortOrderButtonPanel.add(this.averagePriceSortButton);

		return sortOrderButtonPanel;
	}

	/**
	 * Return search button panel
	 * 
	 * @return search button panel
	 */
	private JPanel createSearchButtonPanel() {
		JPanel searchButtonPanel = new JPanel();
		searchButtonPanel.setPreferredSize(new Dimension(FRAME_WIDTH, 40));
		this.searchButton = new JButton("Search");
		searchButtonPanel.add(this.searchButton);
		return searchButtonPanel;
	}

	/**
	 * Initialize result panel
	 */
	private JPanel createResultHeaderPanel() {
		JPanel resultHeaderPanelPanel = new JPanel();
		resultHeaderPanelPanel.setPreferredSize(new Dimension(FRAME_WIDTH, 30));
		int LABEL_HEIGHT = 195;
		int LABEL_WIDTH = 40;
		JLabel headerTitle = new JLabel("Title");
		JLabel headerAuthor = new JLabel("Author");
		JLabel headerWebsite = new JLabel("Website");
		JLabel headerPrice = new JLabel("Price");
		JLabel headerLink = new JLabel("Link");

		headerAuthor.setPreferredSize(new Dimension(LABEL_HEIGHT, LABEL_WIDTH));
		headerLink.setPreferredSize(new Dimension(LABEL_HEIGHT, LABEL_WIDTH));
		headerPrice.setPreferredSize(new Dimension(LABEL_HEIGHT, LABEL_WIDTH));
		headerTitle.setPreferredSize(new Dimension(LABEL_HEIGHT, LABEL_WIDTH));
		headerWebsite.setPreferredSize(new Dimension(LABEL_HEIGHT, LABEL_WIDTH));
		resultHeaderPanelPanel.add(headerTitle);
		resultHeaderPanelPanel.add(headerAuthor);
		resultHeaderPanelPanel.add(headerWebsite);
		resultHeaderPanelPanel.add(headerPrice);
		resultHeaderPanelPanel.add(headerLink);

		return resultHeaderPanelPanel;

	}

}
