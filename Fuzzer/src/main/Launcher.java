package main;

import java.util.List;

import model.Path;
import model.Queries;

public class Launcher {
	
	public final String urlBase = "https://twitter.com/";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
	

	public Queries generateQuery(List<Path> paths){
			return new Queries(paths);
	}
	
	
	public String executeQuery(Queries q){
		return null;
	}
}
