package model;

import java.util.ArrayList;
import java.util.List;

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
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n");
		builder.append("\t\t");
		builder.append("Operation [operationParameters=");
		builder.append("\n");
		builder.append(operationParameters);
		builder.append(", operationDescription=");
		builder.append(operationDescription);
		builder.append("]");
		builder.append("\n");
		return builder.toString();
	}



}
