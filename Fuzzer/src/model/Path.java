package model;

import java.util.ArrayList;
import java.util.List;

public class Path {
	
	//ATTRIBUTES
	private String pathName;
	private String pathType;
	
	private List<Parameter> pathParameters = new ArrayList<Parameter>();

	//GETTERS AND SETTERS
	public String getPathName() {
		return pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	public String getPathType() {
		return pathType;
	}

	public void setPathType(String pathType) {
		this.pathType = pathType;
	}

	public List<Parameter> getPathParameters() {
		return pathParameters;
	}

	public void setPathParameters(List<Parameter> pathParameters) {
		this.pathParameters = pathParameters;
	}

}
