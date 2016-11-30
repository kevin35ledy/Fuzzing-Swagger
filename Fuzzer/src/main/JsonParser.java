package main;

import io.swagger.parser.SwaggerParser;
import io.swagger.models.Swagger;

public class JsonParser {

	Swagger swagger = new SwaggerParser().read("http://petstore.swagger.io/v2/swagger.json");
	
	
}
