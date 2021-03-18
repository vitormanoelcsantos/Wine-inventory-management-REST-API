package com.one.innovation.digital.winestock.controller;

import com.one.innovation.digital.winestock.dto.WineDTO;
import com.one.innovation.digital.winestock.exception.WineAlreadyRegisteredException;
import com.one.innovation.digital.winestock.exception.WineNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Api("Manages wine stock")
public interface WineControllerDocs {

    @ApiOperation(value = "Wine creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success wine creation"),
            @ApiResponse(code = 400, message = "Missing required fields or wrong field range value.")
    })
    WineDTO createWine(WineDTO wineDTO) throws WineAlreadyRegisteredException;

    @ApiOperation(value = "Returns wine found by a given name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success wine found in the system"),
            @ApiResponse(code = 404, message = "Wine with given name not found.")
    })
    WineDTO findByName(@PathVariable String name) throws WineNotFoundException;

    @ApiOperation(value = "Returns a list of all wines registered in the system")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of all wines registered in the system"),
    })
    List<WineDTO> listWines();

    @ApiOperation(value = "Delete a wines found by a given valid Id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success wine deleted in the system"),
            @ApiResponse(code = 404, message = "Wine with given id not found.")
    })
    void deleteById(@PathVariable Long id) throws WineNotFoundException;
}