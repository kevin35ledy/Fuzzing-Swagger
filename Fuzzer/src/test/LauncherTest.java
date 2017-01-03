package test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import junit.framework.Assert;
import main.Launcher;
import model.Operation;
import model.Query;
import model.Response;
import model.SwaggerResponse;

public class LauncherTest {

	/** checkIfResponseCodeExists function test **/
	@SuppressWarnings("deprecation")
	@Test
	public void checkIfResponseCodeExistsTest1() {
		Operation op = new Operation();
		Query query = new Query("","","",op);
		Response resp = new Response(query);
		Map<String, SwaggerResponse > map = new HashMap<String, SwaggerResponse>();
		map.put("200", new SwaggerResponse());
		resp.setExpectedResult(map);
		resp.setResponseCode(200);
		
		String isPresent = Launcher.checkResponseCode(resp);
		Assert.assertEquals(isPresent, "OK");
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void checkIfResponseCodeExistsTest2() {
		Operation op = new Operation();
		Query query = new Query("","","",op);
		Response resp = new Response(query);
		Map<String, SwaggerResponse > map = new HashMap<String, SwaggerResponse>();
		map.put("400", new SwaggerResponse());
		resp.setExpectedResult(map);
		resp.setResponseCode(200);
		
		String isPresent = Launcher.checkResponseCode(resp);
		Assert.assertEquals(isPresent, "WARNING !");
	}

}
