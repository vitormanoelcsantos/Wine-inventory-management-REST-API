package com.one.innovation.digital.winestock.builder;

import com.one.innovation.digital.winestock.dto.WineDTO;
import com.one.innovation.digital.winestock.enums.WineType;
import lombok.Builder;

/** Essa classe é utilizada apenas para testes.*/
/** Padrão Builder é muito utilizado para testes,
 *  porque nos auxilia retornando um Objeto com todos os valores preenchidos. */
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
        return new WineDTO(
                id,
                name,
                brand,
                max,
                quantity,
                type);
    }
}