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
 * 
 * @author pcollin & kevin35ledy
 *
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
	 * Get the $ref field's value of parameters
	 * @return
	 */
//	public static String getDefinitionReference(Operation op){
//		String res = "";
//		op.getParameters().
//		return res;
//	}
	
	public static Parameter getParameters(Operation op){
		Parameter paramRes = new Parameter();
		String name = "";
		String type = "";
		boolean required = false;
		for(io.swagger.models.parameters.Parameter paramSwag : op.getParameters()){
//			System.out.println("PARAMETER IN : " + param.getIn());
			//si le paramètre est de type path parameter
			if(paramSwag instanceof io.swagger.models.parameters.PathParameter){
				name = paramSwag.getName();
				type = ((io.swagger.models.parameters.PathParameter) paramSwag).getType();
				required = paramSwag.getRequired();
			}
			else{
				//TODO
			}
			paramRes.setParameterName(name);
			paramRes.setParameterType(type);
			paramRes.setParameterRequirement(required);
		}
		return paramRes;
	}
	

	/**
	 * 
	 * @return ApiPaths Object
	 */
	public static ApiPaths modelBuilding(){
		
		//retrieve the swagger object
		Swagger swagger = getJson();
		
		ApiPaths result = new ApiPaths();
		
		//get all the paths
		Map<String,io.swagger.models.Path> pathsMap = swagger.getPaths(); 
		for (Map.Entry<String, io.swagger.models.Path> path : pathsMap.entrySet())
		{
			Path p = new Path();
			Map<String, model.Operation> pathOperations = p.getPathOperations();
			//get path's name
			p.setPathName(path.getKey());
			
			//get all path's operations
			Map<HttpMethod, Operation> operationsMap = path.getValue().getOperationMap();
			for(Map.Entry<HttpMethod, Operation> op : operationsMap.entrySet()){
				//key operation
				String opKey = op.getKey().toString();
				//operation summary
				String opDescription = op.getValue().getSummary();
				//operation building
				Parameter opParam = getParameters(op.getValue());
				//Display
				System.out.println("OPERATION = " + opKey);
				
				model.Operation operationModel = new model.Operation();
				operationModel.setOperationDescription(opDescription);
				operationModel.getOperationParameters().add(opParam);
				pathOperations.put(opKey, operationModel);
				
			}
			
			
			
			//add the path to queries list
			result.getApiPath().add(p);;
		}
		

		return result;
			
	}
			
}
