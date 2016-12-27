package main;

import java.util.ArrayList;
import java.util.List;

import model.Operation;
import model.Response;

/**
 * 
 * @author pcollin & kevin35ledy
 *
 *
 *Class to test our program
 */

public class Test {
	
	private String typeOperation;
	private Operation operation;
	private List<Response> responses;
	
	public Test(String type, Operation o){
		this.typeOperation = type;
		this.operation = o;
		responses = new ArrayList<Response>();
	}
	

	public void prepare(){
		
	}
	
	
	public void execute(){
		
	}
}
