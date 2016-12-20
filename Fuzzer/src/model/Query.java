package model;


/**
 * 
 * @author pcollin & kevin35ledy
 *
 *Class Query represents a request that will call a swagger api function
 *It contains an url to call, a description of the test, and what is expected as a response 
 */

public class Query {
	
	private String url;
	private String queryDescription;

	
	public Query(String url, String queryDescription){
		this.url = url;
		this.queryDescription = queryDescription;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getQueryDescription() {
		return queryDescription;
	}

	public void setQueryDescription(String queryDescription) {
		this.queryDescription = queryDescription;
	}

	
}
