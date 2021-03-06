package model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringEscapeUtils;


/**
 * 
 * @author pcollin & kevin35ledy
 *
 *Response is the result obtained after emitting a request on swagger api 
 *
 */

public class Response {

	private Query query;
	private Map<String, List<String>> headers;
	private String content; 
	private int responseCode;
	private String error;
	private String isPassed;
	private Map<String, SwaggerResponse> expectedResult;


	public Response(Query query){
		this.query = query;
		this.headers = new HashMap<String, List<String>> ();
		this.content="";
		this.responseCode=-1;
		this.error = "";
		this.isPassed = "";
		this.expectedResult = null;
	}

	//GETTERS AND SETTERS
	public Map<String, SwaggerResponse> getExpectedResult() {
		return expectedResult;
	}

	public String stringifyExpectedResult(){

		String res = "";
		Set<String> set = expectedResult.keySet();
		Iterator<String> it = set.iterator();
		while(it.hasNext()){
			res+=(it.next().replace(",", "VIRGULE"))+"__";
		}

		return res;
	}

	public String stringifyExpectedResultDescription(){

		String res = "";
		Iterator<SwaggerResponse> it = expectedResult.values().iterator();
		while(it.hasNext()){
			SwaggerResponse sr = it.next();
			sr.getResponseDescription();
			res+=StringEscapeUtils.escapeJava(sr.getResponseDescription())+"; ";
		}

		return res;
	}


	public void setExpectedResult(Map<String, SwaggerResponse> expectedResult) {
		this.expectedResult = expectedResult;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public Map<String, List<String>> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, List<String>> headers) {
		this.headers = headers;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	//METHODS


	public String headersToString(){
		StringBuilder sb = new StringBuilder();

		Set<Map.Entry<String, List<String>>> entrySet = headers.entrySet();
		for (Map.Entry<String, List<String>> entry : entrySet) {
			String headerName = entry.getKey();
			sb.append("Header Name:" + headerName);
			List<String> headerValues = entry.getValue();
			for (String value : headerValues) {
				sb.append("Header value:" + value);
			}
		}


		return sb.toString();
	}


	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();

		sb.append("---------------------\n");
		sb.append(this.query.getQueryDescription()+"\n");
		sb.append("---------------------\n");
		sb.append(this.query.getUrl()+"\n");
		sb.append("---------------------\n");
		sb.append("Response : "+this.getResponseCode()+"\n");
		sb.append("---------------------\n");
		sb.append(this.error+"\n");
		sb.append("---------------------\n\n\n\n\n");

		return sb.toString();
	}


}
