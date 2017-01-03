package test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import io.swagger.models.Operation;
import io.swagger.models.Response;
import main.JsonParser;
import model.SwaggerResponse;

public class TestModel {
	
	/** JSON PARSER TEST**/
	
	/** getResponsesFromOperation function test with zero responses**/
	@Test
	public void getResponsesFromOperationTest1(){
		
		Operation op1 = new Operation();
		Map<String, Response> responses = new HashMap<String, Response>();
		op1.setResponses(responses);
		
		Map<String,SwaggerResponse> r1 = JsonParser.getResponsesFromOperation(op1);
		
		Assert.assertEquals(r1.keySet().size(), 0);
	}
	
	/** getResponsesFromOperation function test with 100 responses **/
	@Test
	public void getResponsesFromOperationTest2(){
		
		Operation op1 = new Operation();
		Map<String, Response> responses = new HashMap<String, Response>();
		for(int i = 0; i < 100; i++){
			Response rep = new Response();
			rep.setDescription("Success !");
			responses.put("200" + Integer.toString(i), rep);			
		}
		op1.setResponses(responses);
		
		Map<String,SwaggerResponse> r1 = JsonParser.getResponsesFromOperation(op1);
		
		Assert.assertEquals(r1.keySet().size(), 100);
		
		for (Map.Entry<String, SwaggerResponse> map : r1.entrySet())
		{
			Assert.assertEquals(map.getValue().getResponseDescription(), "Success !");
		}
	}
	
	@Test
	public void test3(){
		
	}

}
