package com.one.innovation.digital.winestock.service;

import com.one.innovation.digital.winestock.dto.WineDTO;
import com.one.innovation.digital.winestock.entity.Wine;
import com.one.innovation.digital.winestock.exception.WineAlreadyRegisteredException;
import com.one.innovation.digital.winestock.exception.WineNotFoundException;
import com.one.innovation.digital.winestock.mapper.WineMapper;
import com.one.innovation.digital.winestock.repository.WineRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
/** @Serivce Marcamos beans com @Service para indicar que ele está mantendo a lógica de negócios.
Não há outra especialidade, além do uso na camada de serviço. Indica que essa classe também será gerenciada
pelo Spring. Permitindo também o uso do Autowired.
 */
@AllArgsConstructor(onConstructor = @__(@Autowired))
/** Cria uma construtor e implicitamente o passa como
 @Autowired. Para que possa ser utilizado, é necessário que a classe se torne um bean Spring. Através do
 @Component ou uma de suas especializações: @Respository, @Service, @Controller.
 O Spring tratará todo o ciclo de vida (Instanciar, utilizar e destruir) toda vez que o Service for instanciado.
 */
public class WineService {

    private final WineRepository wineRepository;

    private final WineMapper wineMapper = WineMapper.INSTANCE;

    public WineDTO createWine(WineDTO wineDTO) throws WineAlreadyRegisteredException {
        verifyIfIsAlreadyRegistered(wineDTO.getName());
        Wine wine = wineMapper.toModel(wineDTO);
        Wine savedWine = wineRepository.save(wine);
        return wineMapper.toDTO(savedWine);
    }

    public WineDTO findByName(String name) throws WineNotFoundException {
        Wine foundWine = wineRepository.findByName(name)
                .orElseThrow(() -> new WineNotFoundException(name));
        return wineMapper.toDTO(foundWine);
    }

    public List<WineDTO> listAll() {
        return wineRepository.findAll()
                .stream()
                .map(wineMapper::toDTO)
                .collect(Collectors.toList());
    }


    public WineDTO update(Long id, WineDTO wineDTO) throws WineNotFoundException{
        verifyIfExists(id);
        Wine wine = wineMapper.toModel(wineDTO);
        wine.setId(id);
        Wine updatedWine = wineRepository.save(wine);
        return wineMapper.toDTO(updatedWine);
    }

    public void deleteById(Long id) throws WineNotFoundException {
        verifyIfExists(id);
        wineRepository.deleteById(id);
    }

    private void verifyIfIsAlreadyRegistered(String name) throws WineAlreadyRegisteredException {
        Optional<Wine> optSavedWine = wineRepository.findByName(name);
        if (optSavedWine.isPresent()) {
            throw new WineAlreadyRegisteredException(name);
        }
    }

    private Wine verifyIfExists(Long id) throws WineNotFoundException {
        return wineRepository.findById(id)
                .orElseThrow(() -> new WineNotFoundException(id));
    }
}