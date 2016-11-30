package main;

import io.swagger.parser.SwaggerParser;
import io.swagger.models.Swagger;

public class JsonParser {

	Swagger swagger = new SwaggerParser().read("https://github.com/akx/twitter-swagger-api-defs/blob/master/twitter_api.json");
	
	
	
}
