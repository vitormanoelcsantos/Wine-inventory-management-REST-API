package com.one.innovation.digital.winestock.entity;

import com.one.innovation.digital.winestock.enums.WineType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data // @Data Gera getters e setters, hash e equal para as propriedades.
@Entity // @Entity Descreve a entendidade. Mapeamento da JPA.
@NoArgsConstructor // @NoArgs... Irá gerar um construtor sem parâmetros se possível.
@AllArgsConstructor // @AllArgs... Irá gerar um construtor com 1 parâmetro para cada propriedade.
public class Wine {

    @Id // @Id Informa ao JPA qual campo é identificado como chave primária de uma tabela.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /** @GeneratedValue é utilizada para informar
    que a geração do valor do identificador único da entidade será gerenciada pelo provedor de persistência.
    Identity: Informamos ao provedor de persistência que os valores a serem atribuídos ao identificador único
    serão gerados pela coluna de auto incremento do banco de dados. Assim, um valor para o identificador é
    gerado para cada registro inserido no banco.
     */
    private Long id;


    @Column(nullable = false, unique = true) // @Column é utilizada para especificar os detalhes da coluna que um campo
    // ou propriedade será mapeado.
    private String name;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private int max;

    @Column(nullable = false)
    private int quantity;

    @Enumerated(EnumType.STRING) // Declarando o tipo do Enum
    @Column(nullable = false)
    private WineType type;

}