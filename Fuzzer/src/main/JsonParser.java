package main;

import io.swagger.parser.SwaggerParser;
import model.*;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import io.swagger.models.HttpMethod;
import io.swagger.models.Operation;
import io.swagger.models.Swagger;

/**
 * Parsing the JSON from a specific URL 
 * @author pcollin
 *
 */
public class JsonParser {
	
	/**
	 * Get a Swagger Object from a url
	 * @return a Swagger Object
	 */
	public static Swagger getJson(){
	
		Swagger swagger = null;
		try {
			swagger = new SwaggerParser().read("http://petstore.swagger.io/v2/swagger.json");
		} catch (Exception e) {
			System.out.println("ERROR : " + e.getMessage());
		}
		return swagger;
	}
	

	/**
	 * 
	 * @return Queries Object
	 */
	public static Queries modelBuilding(){
		
		//retrieve the swagger object
		Swagger swagger = getJson();
		
		Queries result = new Queries();
		
		//get all the paths
		Map<String,io.swagger.models.Path> pathsMap = swagger.getPaths(); 
		for (Map.Entry<String, io.swagger.models.Path> path : pathsMap.entrySet())
		{
			Path p = new Path();
			//get path's name
			p.setPathName(path.getKey());
			
			//get all path's operations
			path.getValue().getOperations();
			Map<HttpMethod, Operation> operationsMap = path.getValue().getOperationMap();
			for(Map.Entry<HttpMethod, Operation> op : operationsMap.entrySet()){
				p.getPathOperations().put(op.getKey().toString(), op.getValue());
			}
//			p.setPathType(pathType);
//			p.setPathParameters(pathParameters);
			
			
			
			//add the path to queries list
			result.getQueryPath().add(p);;
		}
		

		return result;
			
	}
			
}
