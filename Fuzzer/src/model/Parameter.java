package model;

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
	
}
