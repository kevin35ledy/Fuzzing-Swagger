package model;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author pcollin & kevin35ledy
 *
 *Class listing all paths in the swagger api
 *
 */
public class ApiPaths {
	
	//ATTRIBUTES
	private List<Path> apiPaths;

	//GETTERS AND SETTERS
	public ApiPaths(List<Path> queryPath) {
		super();
		this.apiPaths = queryPath;
	}
	
	public ApiPaths() {
		super();
		this.apiPaths = new ArrayList<Path>();
	}

	public List<Path> getApiPath() {
		return apiPaths;
	}

	public void setApiPath(List<Path> apiPath) {
		this.apiPaths = apiPath;
	}
	
	public void addPath(Path p){
		this.apiPaths.add(p);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ApiPaths [apiPaths=");
		builder.append(apiPaths);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
	
	

}
