package com.amazonaws.samples;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class will connect to a URL.
 * @author swapneel
 *
 */
public class URLGetter {
	private URL url;
	private HttpURLConnection connection;
	
	/**
	 * The constructor
	 * Creates a URL object
	 * Opens a connection that will be used later.
	 * @param url the url to get the information from
	 */
	public URLGetter(String url) {
		try {
			this.url = new URL(url);
			
//			connection = new HttpURLConnection(this.url);
				
			URLConnection urlConnection = this.url.openConnection();
			connection = (HttpURLConnection) urlConnection;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method will print HTTP Response Codes for this connection.
	 */
	public void printStatusCode() {
		try {
			int code = connection.getResponseCode();
			String message = connection.getResponseMessage();
			
			System.out.println(code + ": " + message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * This method will return the HTML contents.
	 * @return an arraylist of lines of the page.
	 */
	public ArrayList<String> getContents() {
		ArrayList<String> contents = new ArrayList<String>();
		Scanner in;
		
		try {
			in = new Scanner(connection.getInputStream());
			
			while (in.hasNextLine()) {
				String line = in.nextLine();
				contents.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return contents;
		
	}

}
