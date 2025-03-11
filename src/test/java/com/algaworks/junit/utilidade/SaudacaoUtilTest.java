package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
//@DisplayName("Testes no utilitário Saudação") Também pode ser utilizada
public class SaudacaoUtilTest {

    private static final String SAUDACAO_INCORRETA = "Saudação Incorreta";

    //Nomenclatura BDD
    //Given(Dado), When(Quando), Then(Então)

    @Test
    public void Dado_um_horario_matutino_Quando_saudar_Entao_deve_retornar_bom_dia() {
        //Arrange
        int horaTeste = 9;

        //Act
        String saudacao = saudarTeste(horaTeste);

        //Assert
        assertEquals("Bom dia", saudacao, SAUDACAO_INCORRETA);
    }

    @Test
//    @DisplayName("Saudação com boa tarde")
    public void Dado_um_horario_vespertino_Quando_saudar_Entao_deve_retornar_boa_tarde() {
        String saudacao = saudarTeste(13);
        assertEquals("Boa tarde", saudacao, SAUDACAO_INCORRETA);
    }

    @Test
//    @DisplayName("Saudação com boa noite")
    public void Dado_um_horario_noturno_Quando_saudar_Entao_deve_retornar_boa_noite() {
        String saudacao = saudarTeste(20);
        assertEquals("Boa noite", saudacao, SAUDACAO_INCORRETA);
    }

    private String saudarTeste(int hora) {
        return SaudacaoUtil.saudar(hora);
    }

    @Test
//    @DisplayName("Exception de hora inválida")
    public void Dado_um_horario_invalido_Quando_saudar_Entao_deve_lancar_exception() {
        int horaInvalida = -10;
        assertThrows(IllegalArgumentException.class, () -> SaudacaoUtil.saudar(horaInvalida));
    }

    @Test
    public void Dado_um_horario_invalido_Quando_saudar_Entao_nao_deve_lancar_exception() {
        assertDoesNotThrow(() -> SaudacaoUtil.saudar(0));
    }

}