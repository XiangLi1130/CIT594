package MVC;

/**
 * Enumerator of web sites
 * 
 * @author Ruqing
 *
 */
public enum Website {
	AMAZON("Amazon"), EBAYBRANDNEW("eBay-brand new"), EBAYSECONDHAND("eBay-second hand");

	private String websiteName;

	private Website(String websiteName) {
		this.websiteName = websiteName;
	}

	/**
	 * Return name
	 * 
	 * @return name
	 */
	public String websiteName() {
		return websiteName;
	}

}
