package com.infy.lbsprototype.service;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.infy.lbsprototype.model.AddMultipleJsonInput;
import com.infy.lbsprototype.model.GetMultipleJsonOutput;
import com.infy.lbsprototype.model.InputRequest;

public interface LbsService {
	 List<GetMultipleJsonOutput> getMultipleJsonMessage(InputRequest customer, boolean isPushMultiple);
	 List<AddMultipleJsonInput> preparePushMultipleInput(InputRequest customerReq, boolean isPushMultiple);
	 String getMovedInCustomers(String regionType, String regionCode);
	 String customersWithinToJava(String jsonString, String regionType, String regionCode) throws JsonParseException, JsonMappingException, IOException;
	 String modifyFlag(String confKey);
}
