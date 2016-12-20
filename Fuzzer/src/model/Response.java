package model;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 
 * @author pcollin & kevin35ledy
 *
 *Response is the result obtained after emitting a request on swagger api 
 *
 */

public class Response {

	private Map<String, List<String>> headers;
	private String content; 
	private String urlRequest;
	
	public Response(String url){
		this.urlRequest = url;
	}
	
	//GETTERS AND SETTERS
	
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
		sb.append(this.urlRequest+"\n");
		sb.append("---------------------\n");
		sb.append("------HEADERS--------\n");
		sb.append(headersToString()+"\n");
		sb.append("--------------------\n");
		sb.append(content);
		sb.append("---------------------\n");
		sb.append("---------------------\n");
		sb.append("---------------------\n\n\n\n\n");
		
		return sb.toString();
	}
	
	
}
