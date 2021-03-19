package com.one.innovation.digital.winestock.service;

import com.one.innovation.digital.winestock.builder.WineDTOBuilder;
import com.one.innovation.digital.winestock.dto.WineDTO;
import com.one.innovation.digital.winestock.entity.Wine;
import com.one.innovation.digital.winestock.exception.WineAlreadyRegisteredException;
import com.one.innovation.digital.winestock.exception.WineNotFoundException;
import com.one.innovation.digital.winestock.mapper.WineMapper;
import com.one.innovation.digital.winestock.repository.WineRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
/** @ExtendWith Diz a classe que você a quer criar e extender
o que você deseja. Então, estamos dizendo que iremos querer extender o Mocikto, para a criação
dos nossos objetos mocks para o teste.
 */
public class WineServiceTest {

    private static final long INVALID_WINE_ID = 1L;

    @Mock
    /** @Mock Diz que iremos querer criar um mock daquela classe. No caso, estamos querendo
    utilizar as funções do repositório, mas não o queremos instanciar diretamente na classe.
     */
    private WineRepository wineRepository;

    private WineMapper wineMapper = WineMapper.INSTANCE;
    /**
     * Essa instância não é recebe a anotação
     *
     * @Mock porque o Mapper já é inserido automaticamente dentro da Classse Service como uma constante.
     */

    @InjectMocks
    /** @InjectMocks Injeta o Mock dentro da instância daquela classe.
     */
    private WineService wineService;

    /**
     * Utilizando When/Then para nomeclatura dos métodos
     */
    @Test
    void whenWineInformedThenItShouldBeCreated() throws WineAlreadyRegisteredException {
        // given
        /** Utilizando o padrão Builder, para simular um dado de entrada.
         * Onde na primeira variável estamos construindo o Objeto.
         * E na segunda, estaremos criando a entidade Vinho. */
        WineDTO expectedWineDTO = WineDTOBuilder.builder().build().toWineDTO();
        Wine expectedSavedWine = wineMapper.toModel(expectedWineDTO);

        // when
        when(wineRepository.findByName(expectedWineDTO.getName())).thenReturn(Optional.empty());
        when(wineRepository.save(expectedSavedWine)).thenReturn(expectedSavedWine);

        //then
        WineDTO createdWineDTO = wineService.createWine(expectedWineDTO);

        assertThat(createdWineDTO.getId(), is(equalTo(expectedWineDTO.getId())));
        assertThat(createdWineDTO.getName(), is(equalTo(expectedWineDTO.getName())));
        assertThat(createdWineDTO.getQuantity(), is(equalTo(expectedWineDTO.getQuantity())));
    }

    @Test
    void whenAlreadyRegisteredWineInformedThenAnExceptionShouldBeThrown() {
        // given
        WineDTO expectedWineDTO = WineDTOBuilder.builder().build().toWineDTO();
        Wine duplicatedWine = wineMapper.toModel(expectedWineDTO);

        // when
        when(wineRepository.findByName(expectedWineDTO.getName())).thenReturn(Optional.of(duplicatedWine));

        // then
        assertThrows(WineAlreadyRegisteredException.class, () -> wineService.createWine(expectedWineDTO));
    }

    @Test
    void whenValidWineNameIsGivenThenReturnAWine() throws WineNotFoundException {
        // given
        WineDTO expectedFoundWineDTO = WineDTOBuilder.builder().build().toWineDTO();
        Wine expectedFoundWine = wineMapper.toModel(expectedFoundWineDTO);

        // when
        when(wineRepository.findByName(expectedFoundWine.getName())).thenReturn(Optional.of(expectedFoundWine));

        // then
        WineDTO foundWineDTO = wineService.findByName(expectedFoundWineDTO.getName());

        assertThat(foundWineDTO, is(equalTo(expectedFoundWineDTO)));
    }

    @Test
    void whenNotRegisteredWineNameIsGivenThenThrowAnException() {
        // given
        WineDTO expectedFoundWineDTO = WineDTOBuilder.builder().build().toWineDTO();

        // when
        when(wineRepository.findByName(expectedFoundWineDTO.getName())).thenReturn(Optional.empty());

        // then
        assertThrows(WineNotFoundException.class, () -> wineService.findByName(expectedFoundWineDTO.getName()));
    }

    @Test
    void whenListWineIsCalledThenReturnAListOfWines() {
        // given
        WineDTO expectedFoundWineDTO = WineDTOBuilder.builder().build().toWineDTO();
        Wine expectedFoundWine = wineMapper.toModel(expectedFoundWineDTO);

        //when
        when(wineRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundWine));

        //then
        List<WineDTO> foundListWinesDTO = wineService.listAll();

        assertThat(foundListWinesDTO, is(not(empty())));
        assertThat(foundListWinesDTO.get(0), is(equalTo(expectedFoundWineDTO)));
    }

    @Test
    void whenListWineIsCalledThenReturnAnEmptyListOfWines() {
        //when
        when(wineRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        //then
        List<WineDTO> foundListWinesDTO = wineService.listAll();

        assertThat(foundListWinesDTO, is(empty()));
    }

    @Test
    void whenExclusionIsCalledWithValidIdThenAWineShouldBeDeleted() throws WineNotFoundException {
        // given
        WineDTO expectedDeletedWineDTO = WineDTOBuilder.builder().build().toWineDTO();
        Wine expectedDeletedWine = wineMapper.toModel(expectedDeletedWineDTO);

        // when
        when(wineRepository.findById(expectedDeletedWineDTO.getId())).thenReturn(Optional.of(expectedDeletedWine));
        doNothing().when(wineRepository).deleteById(expectedDeletedWineDTO.getId());

        // then
        wineService.deleteById(expectedDeletedWineDTO.getId());

        /** Como a função para deletar um vinho não retorna nada, queremos comprovar que os métodos estão sendo
         * utilizados, então usaremos o verify para fazer a verificação do método.
         */
        verify(wineRepository, times(1)).findById(expectedDeletedWineDTO.getId());
        verify(wineRepository, times(1)).deleteById(expectedDeletedWineDTO.getId());
    }

}