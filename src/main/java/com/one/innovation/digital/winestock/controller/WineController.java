package com.one.innovation.digital.winestock.controller;

import com.one.innovation.digital.winestock.dto.QuantityDTO;
import com.one.innovation.digital.winestock.dto.WineDTO;
import com.one.innovation.digital.winestock.entity.Wine;
import com.one.innovation.digital.winestock.exception.WineAlreadyRegisteredException;
import com.one.innovation.digital.winestock.exception.WineNotFoundException;
import com.one.innovation.digital.winestock.exception.WineStockExceededException;
import com.one.innovation.digital.winestock.service.WineService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;


@RestController
/** @RestController indica que é um Controller ao Spring, porém voltada ao padrão REST API.
Onde não iremos lidar com a parte de view, de resolver, com toda essa parte de visualização. Através de uma
simples troca de dados, por Json ou XML. Recomendo mudar para @Controller, caso trabalhe com a parte visual.
 */
@RequestMapping("/api/v1/wines")
/** @RequestMapping indica qual será o caminho padrão sempre utilizado
quando formos pesquisar no navegador
 */
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class WineController implements WineControllerDocs {


    private final WineService wineService;

    @PostMapping
    /** @PostMapping Mapeará o método como um método Post do verbo HTTP. Onde passaremos um corpo
    pela requisição.
     */
    @ResponseStatus(HttpStatus.CREATED)
    public WineDTO createWine(@RequestBody @Valid WineDTO wineDTO) throws WineAlreadyRegisteredException {
        return wineService.createWine(wineDTO);
    }

    @GetMapping("/{name}")
    /** Maperá o método com um método Get do verbo HTTP. Onde solicitaremos
     uma resposta de acordo com o nome.
     */
    public WineDTO findByName(@PathVariable String name) throws WineNotFoundException {
        return wineService.findByName(name);
    }

    @GetMapping
    /** Listará os vinhos.
     */
    public List<WineDTO> listWines() {
        return wineService.listAll();
    }

    @DeleteMapping("/{id}")
    /** @DeleteMapping Mapeará o método com um método Delete do verbo HTTP. Onde solicitaremos
    uma exclusão através do id.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws WineNotFoundException {
        wineService.deleteById(id);
    }

    @PatchMapping("/{id}/increment")
    public WineDTO increment(@PathVariable Long id, @RequestBody @Valid QuantityDTO quantityDTO) throws WineNotFoundException, WineStockExceededException {
        return wineService.increment(id, quantityDTO.getQuantity());
    }


    @PutMapping("/{id}")
    public Wine update(@PathVariable Long id, @RequestBody WineDTO wineDTO) throws WineNotFoundException {
        return wineService.update(id, wineDTO);
    }
}