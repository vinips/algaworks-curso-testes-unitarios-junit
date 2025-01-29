package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ContaBancariaTest {

    /* Construtor */
    @Test
    public void testContaBancariaSaldoPositivo() {
        ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.TEN);
        assertEquals(BigDecimal.TEN, contaBancaria.saldo());
    }

    @Test
    public void testContaBancariaSaldoException() {
        assertThrows(IllegalArgumentException.class, () -> new ContaBancaria(null));
    }

    /* Saque */
    @Test
    public void testSaque() {
        ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.TEN);
        contaBancaria.saque(new BigDecimal(3));
        assertEquals(new BigDecimal(7), contaBancaria.saldo());
    }

    @Test
    public void testSaqueExcessivo() {
        ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.TEN);
        assertThrows(RuntimeException.class, () -> contaBancaria.saque(BigDecimal.TEN));
    }

    @Test
    public void testSaqueNull() {
        ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.TEN);
        assertThrows(IllegalArgumentException.class, () -> contaBancaria.saque(null));
    }

    @Test
    public void testSaqueZero() {
        ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.TEN);
        assertThrows(IllegalArgumentException.class, () -> contaBancaria.saque(BigDecimal.ZERO));
    }

    @Test
    public void testSaqueNegativo() {
        ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.TEN);
        assertThrows(IllegalArgumentException.class, () -> contaBancaria.saque(new BigDecimal(-12)));
    }

    /* Depósito */
    @Test
    public void testDeposito() {
        ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.TEN);
        contaBancaria.deposito(new BigDecimal(3));
        assertEquals(new BigDecimal(13), contaBancaria.saldo());
    }

    @Test
    public void testDepositoNull() {
        ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.TEN);
        assertThrows(IllegalArgumentException.class, () -> contaBancaria.deposito(null));
    }

    @Test
    public void testDepositoZero() {
        ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.TEN);
        assertThrows(IllegalArgumentException.class, () -> contaBancaria.deposito(BigDecimal.ZERO));
    }

    @Test
    public void testDepositoNegativo() {
        ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.TEN);
        assertThrows(IllegalArgumentException.class, () -> contaBancaria.deposito(new BigDecimal(-12)));
    }

}