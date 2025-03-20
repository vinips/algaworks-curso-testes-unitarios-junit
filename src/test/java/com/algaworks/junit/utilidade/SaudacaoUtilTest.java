package com.algaworks.junit.utilidade;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
        String saudacaoCorreta = "Bom dia";

        //Act
        String saudacao = saudarTeste(horaTeste);

        //Assert do JUnit
        assertEquals("Bom dia", saudacao, SAUDACAO_INCORRETA);

        //Assert do AssertJ
        Assertions.assertThat(saudacao)
                .withFailMessage(SAUDACAO_INCORRETA)
                .isEqualTo(saudacaoCorreta);

        //Assert do AssertJ com Mensagens descritivas
        Assertions.assertThat(saudacao)
                .as("Validando se a saudação é %s", saudacaoCorreta)
                .withFailMessage(SAUDACAO_INCORRETA + " Resultado: %s", saudacao)
                .isEqualTo(saudacaoCorreta);


        //Assert do AssertJ com Asserções customizadas
        Condition<String> ehBomDia = new Condition<>(
                (mensagemRetornoSaudacao) -> mensagemRetornoSaudacao.equals(saudacaoCorreta),
                "igual a %s",
                saudacaoCorreta
        );

        Assertions.assertThat(saudacao)
                .is(ehBomDia);


        //Assert do AssertJ com Asserções customizadas com classe util SaudacaoUtilConditions
        Assertions.assertThat(saudacao)
                .is(SaudacaoUtilConditions.igualBomDia());
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
//        assertThrows(IllegalArgumentException.class, () -> SaudacaoUtil.saudar(horaInvalida));

        //Com AssertJ
        IllegalArgumentException illegalArgumentException = Assertions.catchThrowableOfType(() -> SaudacaoUtil.saudar(horaInvalida), IllegalArgumentException.class);
        Assertions.assertThat(illegalArgumentException)
                .hasMessage("Hora inválida");
    }

    @Test
    public void Dado_um_horario_invalido_Quando_saudar_Entao_nao_deve_lancar_exception() {
        assertDoesNotThrow(() -> SaudacaoUtil.saudar(0));
    }

    //Passa os valores parametrizados com o tipo que vc quer e os dados
    @ParameterizedTest
    @ValueSource(ints = {5, 6, 7, 8, 9, 10, 11})
    public void Dado_um_horario_matinal_Quando_saudar_Entao_deve_retornar_bom_dia(int hora) {
        String saudacao = saudarTeste(hora);

        assertEquals("Bom dia", saudacao, SAUDACAO_INCORRETA);
    }

}