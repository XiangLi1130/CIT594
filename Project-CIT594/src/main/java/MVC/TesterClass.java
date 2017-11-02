package MVC;

/**
 * The class runs the program and let you do the search
 *
 */
public class TesterClass {
	public static void main(String[] args) {
		Model model = new Model();
		View view = new View();
		Controller controller = new Controller(model, view);

		view.setVisible(true);

	}

}
