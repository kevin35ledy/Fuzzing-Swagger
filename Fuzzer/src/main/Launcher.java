package main;

import java.util.List;

import model.Path;
import model.Queries;
/**
 * 
 * @author pcollin
 *
 */
public class Launcher {
	
	public final String urlBase = "https://twitter.com/";

	public static void main(String[] args) {
		
		//Step 1
		Queries q = JsonParser.modelBuilding();
		
		for(Path p : q.getQueryPath()){
			System.out.println("### PATH ### " + p.getPathName() + "\n");			
		}

	}

	
	

	public Queries generateQuery(List<Path> paths){
			return new Queries(paths);
	}
	
	
	public String executeQuery(Queries q){
		return null;
	}
}
