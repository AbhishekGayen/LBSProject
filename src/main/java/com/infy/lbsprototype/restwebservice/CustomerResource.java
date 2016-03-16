package com.infy.lbsprototype.restwebservice;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.infy.lbsprototype.model.GetMultipleJsonOutput;
import com.infy.lbsprototype.model.GetMyNewsStoriesJsonData;
import com.infy.lbsprototype.model.InputRequest;
import com.infy.lbsprototype.service.LbsService;


@Path("/publishing")
public class CustomerResource {

	
	@Autowired
	LbsService lbsService;
	
	@Path("/getMultiple")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomer(InputRequest inputRequest){
		
		List<GetMultipleJsonOutput> jsonMessageList = lbsService.getMultipleJsonMessage(inputRequest,false);
		
		
		
		if(jsonMessageList!=null && jsonMessageList.size()>0)
			return Response.ok(jsonMessageList, MediaType.APPLICATION_JSON).build();
		else if(jsonMessageList.isEmpty()||jsonMessageList==null)
			return Response.status(Response.Status.NOT_FOUND).entity("{\"errorCode\":\"-1\",\"errorMessage\":\"Messages Not found\"}").build();
		else
			return Response.status(Response.Status.NOT_FOUND).entity("{\"errorCode\":\"-1\",\"errorMessage\":\"Messages Not found\"}").build();
	}
	
	@Path("/pushMultiple/{param1}/{param2}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response getNewNotifications(@PathParam("param1") String regionType, @PathParam("param2") String regionCode){
		
		String messages = lbsService.getMovedInCustomers(regionType, regionCode);
	
		if(messages!=null)
			return Response.ok(messages, MediaType.TEXT_PLAIN).build();
		else
			return Response.status(Response.Status.NOT_FOUND).entity("{\"errorCode\":\"-1\",\"errorMessage\":\"Messages Not found\"}").build();
	}
	
	@Path("/modifyFlag/{param1}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response modifyFlag(@PathParam("param1") String confKey){
		
		String message = lbsService.modifyFlag(confKey);
		return Response.ok(message, MediaType.TEXT_PLAIN).build();

	}
	
	@Path("/getMyNewsStories")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMyNewsStories(InputRequest inputRequest){
		
	GetMyNewsStoriesJsonData getMyNewsStoriesObj = lbsService.getMyNewsStories(inputRequest);

	if(getMyNewsStoriesObj!=null)
		return Response.ok(getMyNewsStoriesObj, MediaType.APPLICATION_JSON).build();
	else
		return Response.status(Response.Status.NOT_FOUND).entity("{\"errorCode\":\"-1\",\"errorMessage\":\"Invalid Customer ID or Region Code\"}").build();
	}

}
