package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SaudacaoUtilTest {

    private static final String SAUDACAO_INCORRETA = "Saudação Incorreta";

    @Test
    public void saudarTesteBomDia() {
        String saudacao = saudarTeste(9);
        assertEquals("Bom dia", saudacao, SAUDACAO_INCORRETA);
    }

    @Test
    public void saudarTesteBoaTarde() {
        String saudacao = saudarTeste(13);
        assertEquals("Boa tarde", saudacao, SAUDACAO_INCORRETA);
    }

    @Test
    public void saudarTesteBoaNoite() {
        String saudacao = saudarTeste(20);
        assertEquals("Boa noite", saudacao, SAUDACAO_INCORRETA);
    }

    private String saudarTeste(int hora) {
        return SaudacaoUtil.saudar(hora);
    }

    @Test
    public void salvarTesteException() {
        IllegalArgumentException argumentException = assertThrows(IllegalArgumentException.class, () -> SaudacaoUtil.saudar(-10));
    }

    @Test
    public void salvarTesteNaoLancaException() {
        assertDoesNotThrow(() -> SaudacaoUtil.saudar(0));

    }

}