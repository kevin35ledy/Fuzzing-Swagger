package main;

import io.swagger.parser.SwaggerParser;
import model.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import io.swagger.models.HttpMethod;
import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.Swagger;
import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.MapProperty;
import io.swagger.models.properties.Property;
import io.swagger.models.properties.RefProperty;

import org.apache.commons.lang3.StringUtils;

/**
 * Parsing the JSON from a specific URL 
 * 
 * @author pcollin & kevin35ledy
 *
 *
 */
public class JsonParser {
	
	
	private static JsonNode root = null;
	
	/**
	 * Get a Swagger Object from a url
	 * @return a Swagger Object
	 */
	public static Swagger getJson(){
	
		Swagger swagger = null;
		try {
			swagger = new SwaggerParser().read("http://petstore.swagger.io/v2/swagger.json");
			
			root = new ObjectMapper().readTree(new URL("http://petstore.swagger.io/v2/swagger.json"));
	
		} catch (Exception e) {
			System.out.println("ERROR : " + e.getMessage());
		}
		return swagger;
	}
	
	/**
	 * Get the $ref field's value of parameters
	 *	 @return
	 */
	public static Model getDefinitionReference(Swagger swagger, Operation op, String ref){
		Map<String, Model> map = swagger.getDefinitions();
		for (Map.Entry<String, Model> def : map.entrySet())
		{
			if(def.getKey().equals(ref)){
				return def.getValue();
			}
		}
		
		return null;
	}
	
	/**
	 * Retrieve a list of parameters from a definition 
	 * @param mod
	 * @return
	 */
	public static List<Parameter> getParametersFromDefinition(Model mod, String ref){
		
		JsonNode node = null;	
	
		
		List<Parameter> res = new ArrayList<Parameter>();
		Map<String, Property> map = new HashMap<String, Property>();
		try {
			map = mod.getProperties();			
			for (Map.Entry<String, Property> prop : map.entrySet())
			{
				Parameter param = new Parameter();
				param.setParameterName(prop.getKey());
				node = root;
				Property p = prop.getValue();
				param.setParameterType(p.getType());
				
				//check if field enum exists 
				String jsonPath = "/definitions/" + ref + "/properties/"+ prop.getKey()+"/enum";
				node = node.at(jsonPath); 
				if(!node.isMissingNode()){
					ArrayNode enumNode = (ArrayNode) node;
					Iterator<JsonNode> enumIterator = enumNode.elements();
					while(enumIterator.hasNext()){
						JsonNode it = enumIterator.next();
						param.getParameterEnum().add(it.asText());
					
					}
				}
				res.add(param);
			}
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		return res;
	}
	
	public static List<Parameter> getParametersFromOperation(Swagger swagger, Operation op){
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
				name = paramSwag.getName();
				location="body";
				required = paramSwag.getRequired();
				String ref = ((io.swagger.models.parameters.BodyParameter) paramSwag).getSchema().getReference();
				ref = StringUtils.substringAfter(ref, "#/definitions/");
				//System.out.println("Reference = " + ref);
				//get the model corresponding to the reference
				Model mod = getDefinitionReference(swagger, op, ref);
				return getParametersFromDefinition(mod, ref);
				
			}
			else{
				//TODO
			}
			param.setParameterName(name);
			param.setParameterType(type);
			param.setParameterLocation(location);
			param.setParameterRequirement(required);
			paramRes.add(param);
		}
		return paramRes;
	}
	
	
	
	/**
	 * Retrieve all responses from a specific operation
	 * @return
	 */
	public static Map<String,SwaggerResponse> getResponsesFromOperation(Operation op){
		
		Map<String, SwaggerResponse> res = new HashMap<String, SwaggerResponse>();
		String responseCode = null;
		
		Map<String, io.swagger.models.Response> responses = op.getResponses();
		for (Map.Entry<String, io.swagger.models.Response> swagResponse : responses.entrySet())
		{
			SwaggerResponse rep = new SwaggerResponse();
			//System.out.println("\t\t" + "RESPONSE = " + swagResponse.getKey());
			responseCode = swagResponse.getKey();
			//description
			rep.setResponseDescription(swagResponse.getValue().getDescription());
			
			//schema
			Property schema = swagResponse.getValue().getSchema();
			rep.setResponseSchema(schema);
//			if(schema instanceof ArrayProperty){
//				System.out.println("\t\t" + "ARRAY = " + ((ArrayProperty) schema).getItems());
//			}
//			else if(schema instanceof RefProperty){
//				System.out.println("\t\t" + "REF = " + ((RefProperty) schema).getSimpleRef());
//			}
//			else if(schema instanceof io.swagger.models.properties.MapProperty){
//				System.out.println("\t\t" + "MAP = " + ((MapProperty)schema).getAdditionalProperties());
//			}
			
			//headers
			rep.setResponseHeaders(swagResponse.getValue().getHeaders());	
			
			res.put(responseCode, rep);
		}
		
		return res;
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
			System.out.println("PATH = " + path.getKey());
			
			//get all path's operations
			Map<HttpMethod, Operation> operationsMap = path.getValue().getOperationMap();
			for(Map.Entry<HttpMethod, Operation> op : operationsMap.entrySet()){
				//key operation
				String opKey = op.getKey().toString();
				//operation summary
				String opDescription = op.getValue().getSummary();
				//responses
				Map<String, SwaggerResponse> responses = getResponsesFromOperation(op.getValue());
				//operation building
				List<Parameter> opParam = getParametersFromOperation(swagger, op.getValue());
				//Display
				System.out.println("\t" +"OPERATION = " + opKey);
				
				model.Operation operationModel = new model.Operation();
				operationModel.setOperationDescription(opDescription);
				operationModel.setOperationParameters(opParam);
				operationModel.setOperationResponses(responses);
				pathOperations.put(opKey, operationModel);
				
			}
			
			
			
			//add the path to queries list
			result.getApiPath().add(p);
		}
		

		return result;
			
	}
			
}
