package model;

public class Parameter {

	//ATTRIBUTES
	private String parameterName;
	private String parameterType;
	private boolean parameterRequirement;
	
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
	public boolean isParameterRequirement() {
		return parameterRequirement;
	}
	public void setParameterRequirement(boolean parameterRequirement) {
		this.parameterRequirement = parameterRequirement;
	}
	
}
