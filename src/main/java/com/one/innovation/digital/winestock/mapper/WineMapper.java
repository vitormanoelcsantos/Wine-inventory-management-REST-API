package com.one.innovation.digital.winestock.mapper;

import com.one.innovation.digital.winestock.dto.WineDTO;
import com.one.innovation.digital.winestock.entity.Wine;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WineMapper {

    WineMapper INSTANCE = Mappers.getMapper(WineMapper.class);

    Wine toModel(WineDTO wineDTO);

    WineDTO toDTO(Wine wine);
}