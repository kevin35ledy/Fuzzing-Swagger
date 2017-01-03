package main;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringEscapeUtils;

import io.swagger.models.HttpMethod;
import model.Parameter;
import model.Operation;
import model.Path;
import model.Query;
import model.ApiPaths;
import model.Response;
import model.SwaggerResponse;

/**
 * 
 * @author pcollin & kevin35ledy
 *
 *Main class that does the fuzzing test
 */

public class Launcher {

	//public final String urlBase = "https://twitter.com/";
	public final static String urlBase = "http://petstore.swagger.io/v2";

	public static void main(String[] args) {		
		//Step 1
		ApiPaths qu = JsonParser.modelBuilding();

		System.out.println("############ VISULATION DE L'OBJET CREE A PARTIR DE JSON PARSER ###########################");
		System.out.println(qu.toString());
		System.out.println("##################  FIN VISUALISATION  #####################");
		List<Response> responses = new ArrayList<Response>();

		for(Path pa : qu.getApiPath()){

			List<Query> requests;
			//System.out.println("### PATH ### " + pa.getPathName() + "");			

			//generate all requests on the path
			requests = generateQuery(pa );


			//execute requests
			try {
				for(Query q:requests){
					System.out.println(q.getUrl()+"\n"+q.getQueryDescription()+"\n\n");
					responses.add(executeQuery(q));
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			

			//write in a csv file responses
			try{
				PrintWriter writer = new PrintWriter("responses.csv", "UTF-8");
				writer.println("Url tested, Expected code, Description, Received code, Test passed, Comments");
				
				//traitement des réponses recues
				for(Response response : responses){
					String line = "";
					line+= StringEscapeUtils.escapeJava(response.getQuery().getUrl().replace(",", "VIRGULE"))+",";
					line+= StringEscapeUtils.escapeJava(response.stringifyExpectedResult())+",";
					line+= StringEscapeUtils.escapeJava(response.stringifyExpectedResultDescription().replace(";", ". "))+",";
					line+= StringEscapeUtils.escapeJava(response.getResponseCode()+"")+",";
					line+= StringEscapeUtils.escapeJava(checkResponseCode(response))+",";
					line+= StringEscapeUtils.escapeJava(response.getError().replace(",", "").replace(";", ""));
					
					writer.println(line);
					
				}
				
				writer.close();
			} catch (IOException e) {
				// do something

			}

		}


	} //end main
	
	/**
	 * Check response code 
	 * @return a string (if the response code corresponds to the expected response code)
	 */
	public static String checkResponseCode(Response resp){
		String res = "WARNING !";
		String expectedRespondeCode = "";
		try {
			expectedRespondeCode = Integer.toString(resp.getResponseCode());			
		} catch (Exception e) {
			
		}
		for(Map.Entry<String, SwaggerResponse> swag : resp.getExpectedResult().entrySet()){
			if(swag.getKey().equals(expectedRespondeCode)){
				res="OK";
			}
		}
		return res;
	}


	/**
	 * 
	 * @param path
	 * @return a list of Query or request with description of the test
	 */
	public static List<Query> generateQuery(Path path){

		System.out.println("Path : "+path.getPathName());
		List<Query> requests = new ArrayList<Query>();


		Operation get = path.getOperationOfType("GET");
		
	

		String urlToTest = urlBase+path.getPathName();

		if(get != null){

			List<Parameter> params = get.getOperationParameters();
			for(Parameter p: params){

				//System.out.println("name : " + p.getParameterName() + ", required: "+p.isParameterRequired()+", type: "+p.getParameterType()+", desc: "+get.getOperationDescription());
				String description = get.getOperationDescription();

				//si parametre requis dans l'url ex: /pet/{petId}
				if(p.isParameterRequired()){

					//generer un test et remplacer dans la chaine, le param
					switch (p.getParameterType()){
					case "integer":
						//check empty
						requests.add(empty(urlToTest, p, description, get));
						//check buffer overflow with length
						requests.add(intBufferOver(urlToTest, p, description, 25, get));
						requests.add(intBufferOver(urlToTest, p, description,100, get));
						requests.add(intBufferOver(urlToTest, p, description,200, get));
						requests.add(intBufferOver(urlToTest, p, description,500, get));
						//check string
						requests.add(randomString(urlToTest, p, description, get));
						//check 0 value
						requests.add(intZero(urlToTest, p, description, get));
						//check negatives value
						requests.add(intNegVal(urlToTest, p, description, get));
						break;

					case "string":
						//check empty string 
						requests.add(empty(urlToTest, p, description, get));
						//check int
						requests.add(strWithInt(urlToTest, p, description, get));
						//check alea string carac speciaux
						requests.add(randomString(urlToTest, p, description, get));
						//check buffer overflow
						requests.add(stringBufferOver(urlToTest, p, description,25, get));
						requests.add(stringBufferOver(urlToTest, p, description,100, get));
						requests.add(stringBufferOver(urlToTest, p, description,200, get));
						requests.add(stringBufferOver(urlToTest, p, description,300, get));
						requests.add(stringBufferOver(urlToTest, p, description,500, get));
						break;
					default:
						System.out.println("type non reconnu");
						break;					
					}

				}
				//parametre non requis, tester la viabilite du path
				else{
					//check if 404
					requests.add(new Query(urlToTest, "Test: " + description, get));
				}


			}
		}



		System.out.println("\n");
		return requests;
	}









	/**
	 * Function that executes the query and return a response
	 * @param q
	 * @return
	 * @throws MalformedURLException
	 */

	public static Response executeQuery(Query q) throws MalformedURLException{
		String res = "";
		Response response = new Response(q);
		response.setExpectedResult(q.getOp().getOperationResponses());


		//generate url 
		try{

			URL url = new URL(q.getUrl());
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();

			connection.connect();

			//get headers fields
			Map<String, List<String>> headers = connection.getHeaderFields();

			response.setResponseCode(connection.getResponseCode());

			InputStream err = connection.getErrorStream();
			int c;
			StringBuilder sb1 = new StringBuilder();
			while((c = err.read()) != -1){
				sb1.append((char)c);
			}
			response.setError(sb1.toString());


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
			//System.err.println(e);
		}

		return response;
	}





	/////////////////////////////
	///TEST GENERATOR////////////
	/////////////////////////////


	/**
	 * generate query with a parameter integer expected and no value given
	 */
	public static Query empty(String url, Parameter p, String opDescription, Operation op){
		String uempty = url.replaceFirst("\\{"+p.getParameterName()+"\\}", "");
		Query qempty = new Query(uempty,"Test : param int expected is empty\n"+opDescription, op);
		return qempty;
	}

	/**
	 * generate query with a parameter integer expected and value given is 0
	 */
	public static Query intZero(String url, Parameter p, String opDescription, Operation op){
		String u = url.replaceFirst("\\{"+p.getParameterName()+"\\}", "0");
		Query q = new Query(u,"Test : param int expected = 0\n"+opDescription, op);
		return q;
	}

	/**
	 * generate query with a parameter integer expected and value given is negative
	 */
	public static Query intNegVal(String url, Parameter p, String opDescription, Operation op){
		Random random=new Random();
		int randomNumber=(random.nextInt(65536)-32768);
		String u = url.replaceFirst("\\{"+p.getParameterName()+"\\}", randomNumber+"");
		Query q = new Query(u,"Test : param int expected and value given is negative\n"+opDescription, op);
		return q;
	}

	/**
	 * generate query with a parameter expected and value given is a sequence of character
	 */
	public static Query randomString(String url, Parameter p, String opDescription, Operation op){
		String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+=:;,-_)({}[]'\"~@";
		Random random=new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 10; i++) {
			int num = random.nextInt(79);
			buffer.append(str.charAt(num));
		}
		String u = url.replaceFirst("\\{"+p.getParameterName()+"\\}", buffer.toString());
		Query q = new Query(u,"Test : param int expected and value given is  a sequence of character\n"+opDescription, op);
		return q;
	}

	/**
	 * generate query with a parameter integer expected and value given is a sequence of character
	 */
	public static Query intBufferOver(String url, Parameter p, String opDescription, int length, Operation op){
		String str = "123456789";
		Random random=new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int num = random.nextInt(9);
			buffer.append(str.charAt(num));
		}
		String u = url.replaceFirst("\\{"+p.getParameterName()+"\\}", buffer.toString());
		Query q = new Query(u,"Test : param integer expected and value is given with the purpose to create a bufferoverflow\n"+opDescription, op);
		return q;
	}

	/**
	 * generate query with a parameter string expected and value given is an integer
	 */
	public static Query strWithInt(String url, Parameter p, String opDescription, Operation op){
		String str = "123456789";
		Random random=new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 5; i++) {
			int num = random.nextInt(9);
			buffer.append(str.charAt(num));
		}
		String u = url.replaceFirst("\\{"+p.getParameterName()+"\\}", buffer.toString());
		Query q = new Query(u,"Test : param string expected and value given is  an integer\n"+opDescription, op);
		return q;
	}

	/**
	 * generate query with the purpose to create a bufferoverflow
	 */
	public static Query stringBufferOver(String url, Parameter p, String opDescription, int length, Operation op){
		String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random=new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int num = random.nextInt(52);
			buffer.append(str.charAt(num));
		}
		String u = url.replaceFirst("\\{"+p.getParameterName()+"\\}", buffer.toString());
		Query q = new Query(u,"Test : param string expected and value is given with the purpose to create a bufferoverflow\n"+opDescription, op);

		return q;
	}


}
