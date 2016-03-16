/*package com.infy.restclient;



import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class GetMultipleClient {
 
	*//**
	 * 
	 * @param args
	 *//*
  public static void main(String[] args) {

	try {
		 
		
		 
		Client client = Client.create();

		WebResource webResource = client
		   .resource("http://10.74.30.15:8080/Tribune/BusinessServices/publishing/getMultiple");
		
		
		String input = "{     \"customerId\":\"dinesh_infosys\",   \"regionType\":\"POI\",   \"regionCode\":\"BLR\",   \"movementType\":\"movein\",   \"time\": \"1453888269\",   \"sendNotification\":\"true\"}";
		
		
		ClientResponse response = (ClientResponse) webResource.header("X-CSRF-Header", "test").header("Content-Type", "application/json;charset=UTF-8").header("Authorization", "Basic bGJzYWRtaW46aW5meUAxMjM=").post(ClientResponse.class, input);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
			     + response.getStatus());
		}

		System.out.println("Output from Server .... \n");
		String output = (String) response.getEntity(String.class);
		System.out.println(output);

	  } catch (Exception e) {

		e.printStackTrace();

	  }

	}
  	
  *//**
   * 
   * @param inputString
   * @return
   *//*
   static String getByteString(String inputString){
	   
	   StringBuffer stringByteArray = new StringBuffer();
		byte[] bytedata = inputString.getBytes();
			stringByteArray.append(bytedata[0]);	
		for(int i =1;i< bytedata.length ; i++){
			stringByteArray.append(","+bytedata[i]);
		}
		
		return stringByteArray.toString();
   }
}*/