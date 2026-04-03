package com.novaraspace.model.mapper.utils;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper
public interface MapperUtils {

    @Named("trim")
    default String trim(String str) {
        return str == null ? null : str.trim();
    }
}
