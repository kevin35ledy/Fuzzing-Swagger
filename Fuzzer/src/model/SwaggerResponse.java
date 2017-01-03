package model;
/**
 * 
 * @author pcollin & kevin35ledy
 *
 *SwaggerResponse is the representation of a response in the swagger api
 */

import java.util.HashMap;
import java.util.Map;

import io.swagger.models.properties.*;

public class SwaggerResponse{
	
	//attributes
	
	private String responseDescription;
	private Property responseSchema;
	private Map<String, Property> responseHeaders = new HashMap<String, Property>();
	
	//getters and setters
	
	public String getResponseDescription() {
		return responseDescription;
	}
	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}
	public Property getResponseSchema() {
		return responseSchema;
	}
	public void setResponseSchema(Property responseSchema) {
		this.responseSchema = responseSchema;
	}
	public Map<String, Property> getResponseHeaders() {
		return responseHeaders;
	}
	public void setResponseHeaders(Map<String, Property> responseHeaders) {
		this.responseHeaders = responseHeaders;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SwaggerResponse [responseDescription=");
		builder.append(responseDescription);
		builder.append(", responseSchema=");
		builder.append(responseSchema);
		builder.append(", responseHeaders=");
		builder.append(responseHeaders);
		builder.append("]");
		return builder.toString();
	}
	

	
}
