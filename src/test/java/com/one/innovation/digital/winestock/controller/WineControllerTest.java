package com.one.innovation.digital.winestock.controller;

import com.one.innovation.digital.winestock.builder.WineDTOBuilder;
import com.one.innovation.digital.winestock.dto.QuantityDTO;
import com.one.innovation.digital.winestock.dto.WineDTO;
import com.one.innovation.digital.winestock.exception.WineNotFoundException;
import com.one.innovation.digital.winestock.service.WineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;


import static com.one.innovation.digital.winestock.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class WineControllerTest {

    private static final String WINE_API_URL_PATH = "/api/v1/wines";
    private static final long VALID_WINE_ID = 1L;
    private static final long INVALID_WINE_ID = 2l;
    private static final String WINE_API_SUBPATH_INCREMENT_URL = "/increment";
    private static final String WINE_API_SUBPATH_DECREMENT_URL = "/decrement";

    private MockMvc mockMvc;

    @Mock
    private WineService wineService;

    @InjectMocks
    private WineController wineController;

    @BeforeEach
    void setUp() {
        /** Esse metódo será executado sempre antes de cada teste.
         * Ele é responsável por transformar o mock em Jackson
         */
        mockMvc = MockMvcBuilders.standaloneSetup(wineController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }


    @Test
    void whenPOSTIsCalledThenAWineIsCreated() throws Exception {
        // given
        WineDTO wineDTO = WineDTOBuilder.builder().build().toWineDTO();

        // when
        when(wineService.createWine(wineDTO)).thenReturn(wineDTO);

        // then
        /** Estamos transformando o corpo do Objeto de teste em um Json para ser testado.
         */
        mockMvc.perform(post(WINE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(wineDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(wineDTO.getName())))
                .andExpect(jsonPath("$.brand", is(wineDTO.getBrand())))
                .andExpect(jsonPath("$.type", is(wineDTO.getType().toString())));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
        // given
        WineDTO wineDTO = WineDTOBuilder.builder().build().toWineDTO();
        wineDTO.setBrand(null);

        // then
        mockMvc.perform(post(WINE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(wineDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETIsCalledWithValidNameThenOkStatusIsReturned() throws Exception {
        // given
        WineDTO wineDTO = WineDTOBuilder.builder().build().toWineDTO();

        //when
        when(wineService.findByName(wineDTO.getName())).thenReturn(wineDTO);

        // then
        mockMvc.perform(get(WINE_API_URL_PATH + "/" + wineDTO.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(wineDTO.getName())))
                .andExpect(jsonPath("$.brand", is(wineDTO.getBrand())))
                .andExpect(jsonPath("$.type", is(wineDTO.getType().toString())));
    }

    @Test
    void whenGETIsCalledWithoutRegisteredNameThenNotFoundStatusIsReturned() throws Exception {
        // given
        WineDTO wineDTO = WineDTOBuilder.builder().build().toWineDTO();

        //when
        when(wineService.findByName(wineDTO.getName())).thenThrow(WineNotFoundException.class);

        // then
        mockMvc.perform(get(WINE_API_URL_PATH + "/" + wineDTO.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGETListWithWinesIsCalledThenOkStatusIsReturned() throws Exception {
        // given
        WineDTO wineDTO = WineDTOBuilder.builder().build().toWineDTO();

        //when
        when(wineService.listAll()).thenReturn(Collections.singletonList(wineDTO));

        // then
        mockMvc.perform(get(WINE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(wineDTO.getName())))
                .andExpect(jsonPath("$[0].brand", is(wineDTO.getBrand())))
                .andExpect(jsonPath("$[0].type", is(wineDTO.getType().toString())));
    }

    @Test
    void whenGETListWithoutWinesIsCalledThenOkStatusIsReturned() throws Exception {
        // given
        WineDTO wineDTO = WineDTOBuilder.builder().build().toWineDTO();

        //when
        when(wineService.listAll()).thenReturn(Collections.singletonList(wineDTO));

        // then
        mockMvc.perform(get(WINE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {
        // given
        WineDTO wineDTO = WineDTOBuilder.builder().build().toWineDTO();

        //when
        doNothing().when(wineService).deleteById(wineDTO.getId());

        // then
        mockMvc.perform(delete(WINE_API_URL_PATH + "/" + wineDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {
        //when
        doThrow(WineNotFoundException.class).when(wineService).deleteById(INVALID_WINE_ID);

        // then
        mockMvc.perform(delete(WINE_API_URL_PATH + "/" + INVALID_WINE_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenPATCHIsCalledToIncrementDiscountThenOKstatusIsReturned() throws Exception {
        QuantityDTO quantityDTO = QuantityDTO.builder()
                .quantity(10)
                .build();

        WineDTO wineDTO = WineDTOBuilder.builder().build().toWineDTO();
        wineDTO.setQuantity(wineDTO.getQuantity() + quantityDTO.getQuantity());

        when(wineService.increment(VALID_WINE_ID, quantityDTO.getQuantity())).thenReturn(wineDTO);

        mockMvc.perform(patch(WINE_API_URL_PATH + "/" + VALID_WINE_ID + WINE_API_SUBPATH_INCREMENT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(quantityDTO))).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(wineDTO.getName())))
                .andExpect(jsonPath("$.brand", is(wineDTO.getBrand())))
                .andExpect(jsonPath("$.type", is(wineDTO.getType().toString())))
                .andExpect(jsonPath("$.quantity", is(wineDTO.getQuantity())));
    }
}