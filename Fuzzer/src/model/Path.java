package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import io.swagger.models.HttpMethod;
import io.swagger.models.Model;
import model.Operation;
/**
 * 
 * @author pcollin & kevin35ledy
 *
 *Class representing a path in the swagger api 
 *It contains several operations
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
	
	public Operation getOperationOfType(String type){
		return this.pathOperations.get(type);
	}

	public void setPathOperations(Map<String, Operation> pathOperations) {
		this.pathOperations = pathOperations;
	}
}
