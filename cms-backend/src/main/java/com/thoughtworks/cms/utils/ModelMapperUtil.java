package com.thoughtworks.cms.utils;

import com.thoughtworks.cms.application.response.DetailedAdminContentResponse;
import com.thoughtworks.cms.application.response.DetailedCustomerContentResponse;
import com.thoughtworks.cms.application.response.SimpleContentResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class ModelMapperUtil {

    public static ModelMapper getModelMapper() {
        ModelMapper mapper =  new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.addMappings(DetailedAdminContentResponse.DetailedAdminContentDtoMap);
        mapper.addMappings(DetailedCustomerContentResponse.DetailedCustomerContentDtoMap);
        mapper.addMappings(SimpleContentResponse.ContentToSimpleContentDtoMap);
        return mapper;
    }
}
