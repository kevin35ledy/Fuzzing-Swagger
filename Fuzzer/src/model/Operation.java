package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author pcollin & kevin35ledy
 *
 * An Operation is a path of swagger api that can be called, it contains some parameters
 */
public class Operation {
	
	//ATTRIBUTES
	private List<Parameter> operationParameters = new ArrayList<Parameter>();
	private String operationDescription;
	private Map<String, SwaggerResponse> operationResponses = new HashMap<String, SwaggerResponse>();
	
	//GETTERS AND SETTERS
	public List<Parameter> getOperationParameters() {
		return operationParameters;
	}
	public void setOperationParameters(List<Parameter> operationParameters) {
		this.operationParameters = operationParameters;
	}
	public String getOperationDescription() {
		return operationDescription;
	}
	public void setOperationDescription(String operationDescription) {
		this.operationDescription = operationDescription;
	}
	public Map<String, SwaggerResponse> getOperationResponses() {
		return operationResponses;
	}
	public void setOperationResponses(Map<String, SwaggerResponse> operationResponses) {
		this.operationResponses = operationResponses;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n");
		builder.append("\t\t");
		builder.append("Operation [operationParameters=");
		builder.append(operationParameters);
		builder.append(", operationDescription=");
		builder.append(operationDescription);
		builder.append(", operationResponses=");
		builder.append(operationResponses);
		builder.append("\n");
		builder.append("]");
		return builder.toString();
	}
	
	




}
