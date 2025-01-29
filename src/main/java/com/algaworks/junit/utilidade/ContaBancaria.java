package com.algaworks.junit.utilidade;

import java.math.BigDecimal;

public class ContaBancaria {

    private BigDecimal saldo;

    public ContaBancaria(BigDecimal saldo) {
        if (saldo != null) {
            this.saldo = saldo;
        } else {
            throw new IllegalArgumentException("Valor inválido");
        }
        //TODO 1 - validar saldo: não pode ser nulo, caso seja, deve lançar uma IllegalArgumentException
        //TODO 2 - pode ser zero ou negativo
    }

    public void saque(BigDecimal valor) {
        if (valor != null && valor.compareTo(BigDecimal.ZERO) > 0){
            this.saldo = this.saldo.subtract(valor);
            if (this.saldo.compareTo(BigDecimal.ZERO) <= 0){
                throw new RuntimeException("Saldo Insuficiente");
            }
        } else {
            throw new IllegalArgumentException("Valor inválido");
        }
        //TODO 1 - validar valor: não pode ser nulo, zero ou menor que zero, caso seja, deve lançar uma IllegalArgumentException
        //TODO 2 - Deve subtrair o valor do saldo
        //TODO 3 - Se o saldo for insuficiente deve lançar uma RuntimeException
    }

    public void deposito(BigDecimal valor) {
        if (valor != null && valor.compareTo(BigDecimal.ZERO) > 0){
            this.saldo = this.saldo.add(valor);
        } else {
            throw new IllegalArgumentException("Valor inválido");
        }
        //TODO 1 - validar valor: não pode ser nulo, zero ou menor que zero, caso seja, deve lançar uma IllegalArgumentException
        //TODO 2 - Deve adicionar o valor ao saldo
    }

    public BigDecimal saldo() {
        return saldo;
    }
}
