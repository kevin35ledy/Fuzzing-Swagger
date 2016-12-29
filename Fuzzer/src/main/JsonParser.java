package main;

import io.swagger.parser.SwaggerParser;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import io.swagger.models.HttpMethod;
import io.swagger.models.Operation;
import io.swagger.models.Swagger;
import io.swagger.models.properties.Property;

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
//	public static String getDefinitionReference(Swagger swagger, Operation op){
//		swagger.getDefinitions();
//		String res = "";
//		op.getParameters().
//		return res;
//	}
	
	public static List<Parameter> getParameters(Operation op){
		List<Parameter> paramRes = new ArrayList<Parameter>();
		String name = "";
		String type = "";
		String location = "";
		Property items = null;
		boolean required = false;
		for(io.swagger.models.parameters.Parameter paramSwag : op.getParameters()){
			Parameter param = new Parameter();

			//PARAMETER TYPE
			if(paramSwag instanceof io.swagger.models.parameters.PathParameter){
				name = paramSwag.getName();
				location = "path";
				type = ((io.swagger.models.parameters.PathParameter) paramSwag).getType();
				required = paramSwag.getRequired();
			}
			//QUERY TYPE
			if(paramSwag instanceof io.swagger.models.parameters.QueryParameter){
				name = paramSwag.getName();
				location = "query";
				required = paramSwag.getRequired();
				type = ((io.swagger.models.parameters.QueryParameter) paramSwag).getType();
				if(type.equals("array")){
					//System.out.println("Je suis un tableau !");
					items = ((io.swagger.models.parameters.QueryParameter) paramSwag).getItems();
				}
			}
			//BODY TYPE
			if(paramSwag instanceof io.swagger.models.parameters.BodyParameter){
			
			}
			else{
				//TODO
			}
			param.setParameterName(name);
			param.setParameterType(type);
			param.setParameterRequirement(required);
			paramRes.add(param);
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
				List<Parameter> opParam = getParameters(op.getValue());
				//Display
				System.out.println("OPERATION = " + opKey);
				
				model.Operation operationModel = new model.Operation();
				operationModel.setOperationDescription(opDescription);
				operationModel.setOperationParameters(opParam);
				pathOperations.put(opKey, operationModel);
				
			}
			
			
			
			//add the path to queries list
			result.getApiPath().add(p);
		}
		

		return result;
			
	}
			
}
