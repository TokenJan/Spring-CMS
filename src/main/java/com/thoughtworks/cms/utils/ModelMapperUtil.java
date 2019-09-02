package com.thoughtworks.cms.utils;

import com.thoughtworks.cms.dto.DetailedContentDto;
import com.thoughtworks.cms.dto.SimpleContentDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class ModelMapperUtil {

    public static ModelMapper getAdminModelMapper() {
        ModelMapper mapper =  new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.addMappings(DetailedContentDto.AdminDetailedContentDtoMap);
        mapper.addMappings(SimpleContentDto.ContentToSimpleContentDtoMap);
        return mapper;
    }

    public static ModelMapper getCustomerModelMapper() {
        ModelMapper mapper =  new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.addMappings(DetailedContentDto.CustomerDetailedContentDtoMap);
        return mapper;
    }
}
