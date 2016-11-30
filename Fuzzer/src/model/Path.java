package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.models.HttpMethod;
import io.swagger.models.Operation;
/**
 * 
 * @author pcollin
 *
 */
public class Path {
	
	//ATTRIBUTES
	private Map<String, Operation> pathOperations  = new HashMap<String, Operation>();
	private String pathName;	

	//GETTERS AND SETTERS
	public String getPathName() {
		return pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	public Map<String, Operation> getPathOperations() {
		return pathOperations;
	}

	public void setPathOperations(Map<String, Operation> pathOperations) {
		this.pathOperations = pathOperations;
	}
}
