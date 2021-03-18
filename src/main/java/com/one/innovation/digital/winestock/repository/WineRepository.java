package com.one.innovation.digital.winestock.repository;

import com.one.innovation.digital.winestock.entity.Wine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface WineRepository extends JpaRepository<Wine, Long> {
    /** Essa classe é um DAO, onde é implementado o padrão de arquitetura DTO, que terá a responsabilidade
     * de conversar com o banco de dados. Onde terá métodos comuns para inserção, exclusão, atualização e listagem
     * dos dados do banco. Além de ter métodos manuais, que fazem realção funções específicas como mostrado a baixo.
    */

    Optional<Wine> findByName(String name);
}