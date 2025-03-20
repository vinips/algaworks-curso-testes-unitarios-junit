package com.algaworks.junit.utilidade;

import org.assertj.core.api.Condition;

public abstract class SaudacaoUtilConditions {

    public static Condition<String> igual(String saudacaoCorreta) {
        return new Condition<>(
                (mensagemRetornoSaudacao) -> mensagemRetornoSaudacao.equals(saudacaoCorreta),
                "igual a %s",
                saudacaoCorreta
        );
    }

    public static Condition<String> igualBomDia() {
        return igual("Bom dia");
    }

    }
