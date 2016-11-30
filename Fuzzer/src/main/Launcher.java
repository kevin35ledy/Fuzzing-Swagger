package main;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.Path;
import model.Queries;

import model.Response;

public class Launcher {
	
	//public final String urlBase = "https://twitter.com/";
	public final static String urlBase = "http://google.com/";

	public static void main(String[] args) {		
		//Step 1
		Queries qu = JsonParser.modelBuilding();
		
		for(Path pa : qu.getQueryPath()){
			System.out.println("### PATH ### " + pa.getPathName() + "\n");			

		
		
		
		Queries q = new Queries();
		Path p = new Path();
		
		p.setPathName("");
//		p.setPathType("get");
		q.addPath(p);
		
		try {
			Response response = executeQuery(q);
			
			
			System.out.println(response);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
	
	

	public static Queries generateQuery(List<Path> paths){
			return new Queries(paths);
	}
	
	
	public static Response executeQuery(Queries q) throws MalformedURLException{
		Response response = new Response();
		String res = "";
		//iterating on paths in q
		for(Path p : q.getQueryPath()){
			//generate url 
			try{
				
				String urlString = urlBase;
//				String type = p.getPathType().toUpperCase();
				
				
				URL url = new URL(urlString);
				HttpURLConnection connection = (HttpURLConnection)url.openConnection();
				
				
//				connection.setRequestMethod(type);
				connection.connect();
				
				
				//get headers fields
				Map<String, List<String>> headers = connection.getHeaderFields();
				response.setHeaders(headers);
				
		        
		        
				InputStream in = connection.getInputStream();
				
				
				int ch;
				StringBuilder sb = new StringBuilder();
				while((ch = in.read()) != -1){
					sb.append((char)ch);
				}
				
				//get response content
				response.setContent(sb.toString()+"\n\n");
				res+= sb.toString()+"\n\n";
				
				
			}catch(Exception e){
				System.err.println(e);
			}
				
				
		}
			
			
		return response;
	}
}
