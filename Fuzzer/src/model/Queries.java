package model;

import java.util.ArrayList;
import java.util.List;

public class Queries {
	
	//ATTRIBUTES
	private List<Path> queryPaths;

	public Queries(List<Path> queryPath) {
		super();
		this.queryPaths = queryPath;
	}
	
	public Queries() {
		super();
		this.queryPaths = new ArrayList<Path>();
	}

	public List<Path> getQueryPath() {
		return queryPaths;
	}

	public void setQueryPath(List<Path> queryPath) {
		this.queryPaths = queryPath;
	}
	
	public void addPath(Path p){
		this.queryPaths.add(p);
	}
	
	
	
	

}
