package main;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.swagger.models.parameters.Parameter;
import model.Operation;
import model.Path;
import model.Queries;
import model.Response;

public class Launcher {
	
	//public final String urlBase = "https://twitter.com/";
	public final static String urlBase = "http://petstore.swagger.io/v2";

	public static void main(String[] args) {		
		//Step 1
		Queries qu = JsonParser.modelBuilding();
		
		for(Path pa : qu.getQueryPath()){
			System.out.println("### PATH ### " + pa.getPathName() + "\n");			

			
			try {
				for(Response response : executeQuery(qu)){
					System.out.println(response.toString());
				}
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		}
		
		
}
	
	

	public static Queries generateQuery(List<Path> paths){
			return new Queries(paths);
	}
	
	
	public static List<Response> executeQuery(Queries q) throws MalformedURLException{
		List<Response> listResponse = new ArrayList<Response>();
		String res = "";
		//iterating on paths in q
		for(Path p : q.getQueryPath()){
			
			//generate url 
			try{
				
				
				String urlString = urlBase + p.getPathName();
				
				
				Map<String, io.swagger.models.Operation> m = p.getPathOperations();
				io.swagger.models.Operation o = m.get("GET");//pose probleme car plusieurs fois clé GET
				
				
				//urlString.replaceAll("{}", "2");
				String urlTest = urlString.replaceFirst("\\{petId\\}", "1");
				
				Response response = new Response(urlTest);
				
				
				URL url = new URL(urlTest);
				HttpURLConnection connection = (HttpURLConnection)url.openConnection();
				
				
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
				
				
				listResponse.add(response);
				
			}catch(Exception e){
				//System.err.println(e);
			}
				
				
		}
		
		

			
			
		return listResponse;
	}
}
