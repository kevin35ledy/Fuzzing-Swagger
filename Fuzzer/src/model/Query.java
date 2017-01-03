package model;


/**
 * 
 * @author pcollin & kevin35ledy
 *
 *Class Query representing a request that will call a swagger api function
 *It contains an url to call, a description of the test, and what is expected as a response 
 */

public class Query {
	
	private String url;
	private String queryDescription;
	private Operation op;

	
	public Query(String url, String queryDescription, Operation op){
		this.url = url;
		this.queryDescription = queryDescription;
		this.op = op;
	}
	
	public Operation getOp(){
		return this.op;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Query [url=");
		builder.append(url);
		builder.append(", queryDescription=");
		builder.append(queryDescription);
		builder.append("]");
		return builder.toString();
	}

	
}
