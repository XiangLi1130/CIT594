package MVC;

import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JLabel;

/**
 * A hyper link JLabel
 * 
 * @author Ruqing
 *
 */
public class LinkLabel extends JLabel {

	/**
	 * Constructor
	 * 
	 * @param text
	 *            text on label
	 * @param link
	 *            link
	 */
	public LinkLabel(String text, final URL link) {
		super("<html>" + text + "</html>");
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(link.toURI());
				} catch (IOException err) {
					err.printStackTrace();
				} catch (URISyntaxException err) {
					err.printStackTrace();
				}
			}
		});
	}

}
