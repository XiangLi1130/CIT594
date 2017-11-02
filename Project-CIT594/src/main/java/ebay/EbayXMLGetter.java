package ebay;

import java.util.ArrayList;

import com.amazonaws.samples.URLGetter;

/**
 * @author XiangLi
 * the class get access to the ebay API
 
 */
public class EbayXMLGetter {
	/**the APP id used to connect to ebay API */
	public final static String EBAY_APP_ID = "XiangLi-findinga-PRD-869dbd521-cbb4aa71";
	
	/**the url link */
	 public final static String EBAY_FINDING_SERVICE_URI = "http://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME="
	            + "{operation}&SERVICE-VERSION={version}&SECURITY-APPNAME="
	            + "{applicationId}&productId.@type=ISBN&productId={isbn}";
    
    
    
    public static final String SERVICE_VERSION = "1.0.0";
    public static final String OPERATION_NAME = "findItemsByProduct";
    public static final String GLOBAL_ID = "EBAY-US";
    public final static int REQUEST_DELAY = 3000;
    
    
    /**
     * the method create to url link with the given ISBN
     * @param isbn the ISBN of book
     * @return the url address
     */
    private String createAddress(String isbn) {

        
        String address = EbayXMLGetter.EBAY_FINDING_SERVICE_URI;
        address = address.replace("{version}", EbayXMLGetter.SERVICE_VERSION);
        address = address.replace("{operation}", EbayXMLGetter.OPERATION_NAME);
        address = address.replace("{globalId}", EbayXMLGetter.GLOBAL_ID);
        address = address.replace("{applicationId}", EbayXMLGetter.EBAY_APP_ID);
        address = address.replace("{isbn}", isbn);

        return address;

    }
    
    /**
     * the method return the content in xml form
     * @param isbn the given ISBN of the book
     * @return the content in xml form
     */
    public String content(String isbn) {
    	String address = createAddress(isbn);
    	URLGetter url = new URLGetter(address);
    	ArrayList<String> contents = url.getContents();
    	String content = contents.get(0);
    	return content;
    }
    
    
   

}
