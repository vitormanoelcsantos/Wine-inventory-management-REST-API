package com.one.innovation.digital.winestock.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.one.innovation.digital.winestock.dto.WineDTO;
import com.one.innovation.digital.winestock.enums.WineType;
import lombok.Builder;

@Builder
public class WineDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Cabernet Sauvignon";

    @Builder.Default
    private String brand = "Villa Lobos";

    @Builder.Default
    private int max = 50;

    @Builder.Default
    private int quantity = 10;

    @Builder.Default
    private WineType type = WineType.REDWINE;

    public WineDTO toWineDTO() {
        return new WineDTO(id,
                name,
                brand,
                max,
                quantity,
                type);
    }
}