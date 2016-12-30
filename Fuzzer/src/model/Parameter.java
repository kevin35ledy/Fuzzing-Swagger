package model;

import java.util.ArrayList;
import java.util.List;

import io.swagger.models.properties.Property;

/**
 * 
 * @author pcollin & kevin35ledy
 *
 *Class Parameter represents a parameter of an Operation
 */
public class Parameter {

	//ATTRIBUTES
	private String parameterName;
	private String parameterType;
	private boolean parameterRequired;
	private String parameterLocation;
	private Property parameterItems;
	private List<String> parameterEnum = new ArrayList<String>();
	
	
	//GETTERS AND SETTERS
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public String getParameterType() {
		return parameterType;
	}
	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}
	public boolean isParameterRequired() {
		return parameterRequired;
	}
	public void setParameterRequirement(boolean parameterRequirement) {
		this.parameterRequired = parameterRequirement;
	}
	public String getParameterLocation() {
		return parameterLocation;
	}
	public void setParameterLocation(String parameterLocation) {
		this.parameterLocation = parameterLocation;
	}
	public Property getParameterItems() {
		return parameterItems;
	}
	public void setParameterItems(Property parameterItems) {
		this.parameterItems = parameterItems;
	}
	public List<String> getParameterEnum() {
		return parameterEnum;
	}
	public void setParameterEnum(List<String> parameterEnum) {
		this.parameterEnum = parameterEnum;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\t\t\t");
		builder.append("Parameter [parameterName=");
		builder.append(parameterName);
		builder.append(", parameterType=");
		builder.append(parameterType);
		builder.append(", parameterRequired=");
		builder.append(parameterRequired);
		builder.append(", parameterLocation=");
		builder.append(parameterLocation);
		builder.append(", parameterItems=");
		builder.append(parameterItems);
		builder.append(", parameterEnum=");
		builder.append(parameterEnum);
		builder.append("]");
		builder.append("\n");
		return builder.toString();
	}
	
}
