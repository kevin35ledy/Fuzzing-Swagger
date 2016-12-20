package main;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import model.Parameter;
import model.Operation;
import model.Path;
import model.Query;
import model.ApiPaths;
import model.Response;

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


		for(Path pa : qu.getApiPath()){

			List<Query> requests;
			//System.out.println("### PATH ### " + pa.getPathName() + "");			

			//generate all requests on the path
			requests = generateQuery(pa);
			
			for(Query q:requests){
				System.out.println(q.getUrl()+"\n"+q.getQueryDescription()+"\n\n");
			}


			//execute requests TODO

			//check response TODO


			/*	try {
				for(Response response : executeQuery(qu)){
					//System.out.println(response.toString());
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 */
		}


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

				System.out.println("name : " + p.getParameterName() + ", required: "+p.isParameterRequired()+", type: "+p.getParameterType()+", desc: "+get.getOperationDescription());

				//si parametre requis dans l'url ex: /pet/{petId}
				if(p.isParameterRequired()){

					//generer un test et remplacer dans la chaine, le param
					switch (p.getParameterType()){
					case "integer":
						//check empty
						requests.add(intEmpty(urlToTest, p));
						//check buffer overflow with length
						requests.add(intBufferOver(urlToTest, p,25));
						requests.add(intBufferOver(urlToTest, p,100));
						requests.add(intBufferOver(urlToTest, p,200));
						requests.add(intBufferOver(urlToTest, p,500));
						//check string
						requests.add(intString(urlToTest, p));
						//check 0 value
						requests.add(intZero(urlToTest, p));
						//check negatives value
						requests.add(intNegVal(urlToTest, p));
						break;

					case "string":
						//check empty string 
						//check int
						//check alea string carac speciaux
						//check buffer overflow
						break;
					default:
						System.out.println("type non reconnu");
						break;					
					}

				}
				//parametre non requis, tester la viabilite du path
				else{
					//check if 404
				}


			}
		}



		System.out.println("\n");
		return requests;
	}



	/**
	 * generate query with a parameter integer expected and no value given
	 */
	public static Query intEmpty(String url, Parameter p){
		String uempty = url.replaceFirst("\\{"+p.getParameterName()+"\\}", "");
		Query qempty = new Query(uempty,"Test : param int expected is empty");
		return qempty;
	}
	
	/**
	 * generate query with a parameter integer expected and value given is 0
	 */
	public static Query intZero(String url, Parameter p){
		String u = url.replaceFirst("\\{"+p.getParameterName()+"\\}", "0");
		Query q = new Query(u,"Test : param int expected = 0");
		return q;
	}
	
	/**
	 * generate query with a parameter integer expected and value given is negative
	 */
	public static Query intNegVal(String url, Parameter p){
		Random random=new Random();
		int randomNumber=(random.nextInt(65536)-32768);
		String u = url.replaceFirst("\\{"+p.getParameterName()+"\\}", randomNumber+"");
		Query q = new Query(u,"Test : param int expected and value given is negative");
		return q;
	}
	
	/**
	 * generate query with a parameter integer expected and value given is a sequence of character
	 */
	public static Query intString(String url, Parameter p){
		String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+=:;,-_)({}[]'\"~@";
		Random random=new Random();
		StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            int num = random.nextInt(80);
            buffer.append(str.charAt(num));
        }
		String u = url.replaceFirst("\\{"+p.getParameterName()+"\\}", buffer.toString());
		Query q = new Query(u,"Test : param int expected and value given is  a sequence of character");
		return q;
	}
	
	/**
	 * generate query with a parameter integer expected and value given is a sequence of character
	 */
	public static Query intBufferOver(String url, Parameter p, int length){
		String str = "123456789";
		Random random=new Random();
		StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(9);
            buffer.append(str.charAt(num));
        }
		String u = url.replaceFirst("\\{"+p.getParameterName()+"\\}", buffer.toString());
		Query q = new Query(u,"Test : param int expected and value given is  a sequence of character");
		return q;
	}
	



	public static List<Response> executeQuery(ApiPaths q) throws MalformedURLException{
		List<Response> listResponse = new ArrayList<Response>();
		String res = "";
		//iterating on paths in q
		for(Path p : q.getApiPath()){

			//generate url 
			try{


				String urlString = urlBase + p.getPathName();

				Map<String, Operation> m = p.getPathOperations();

				Operation o = m.get("GET");//pose probleme car plusieurs fois clé GET
				for(Parameter param : o.getOperationParameters()){
					System.out.print("param name : "+param.getParameterName() + ", type : " + param.getParameterType());
				}
				System.out.println("\n");



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
