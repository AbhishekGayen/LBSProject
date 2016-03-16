/*package com.infy.restclient;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class PushMultipleClient {
	
	 public static void main(String[] args) {

			try {
				 


				Client client = Client.create();

				WebResource webResource = client
				   .resource("http://localhost:8080/LbsPrototype/BusinessServices/publishing/pushMultiple/poi/BLR");
				
				
				//String input = "{     \"customerId\":\"dinesh_infosys\",   \"regionType\":\"POI\",   \"regionCode\":\"BLR\",   \"movementType\":\"movein\",   \"time\": \"1453888269\",   \"sendNotification\":\"true\", \"lat\":\"12.8505\", \"lon\":\"77.6655\", \"city\":\"Bangalore\", \"town\":\"Electronic City\", \"country\":\"India\"}";
				
				
				ClientResponse response = (ClientResponse) webResource.header("X-CSRF-Header", "test").header("Authorization", "Basic bGJzYWRtaW46aW5meUAxMjM=").get(ClientResponse.class);

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

}
*/