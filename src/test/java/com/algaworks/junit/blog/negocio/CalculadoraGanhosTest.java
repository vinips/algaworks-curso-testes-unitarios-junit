package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.modelo.Editor;
import com.algaworks.junit.blog.modelo.Ganhos;
import com.algaworks.junit.blog.modelo.Post;
import com.algaworks.junit.blog.utilidade.ProcessadorTextoSimples;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CalculadoraGanhosTest {

    private static CalculadoraGanhos calculadoraGanhos;

    private  Editor editor;

    private Post post;

    @BeforeAll
    public static void beforeAll() {
        calculadoraGanhos = new CalculadoraGanhos(new ProcessadorTextoSimples(), BigDecimal.TEN);
    }

    @BeforeEach
    public void beforeEach() {
        editor = new Editor(1L, "Vini", "vini@teste.com", new BigDecimal(5), true);

        post = new Post(1L, "Ecossistema Java", "O Ecossistema do Java é muito maduro",
                editor, "ecossistema-java-abc123", null, false, false);
    }

    @Nested
    class CalcularGanhos {
        @Test
        public void deveCalcularGanhosComPremium() {
            Ganhos ganhos = calculadoraGanhos.calcular(post);

            assertEquals(new BigDecimal(45), ganhos.getTotalGanho());
            assertEquals(7, ganhos.getQuantidadePalavras());
            assertEquals(editor.getValorPagoPorPalavra(), ganhos.getValorPagoPorPalavra());
        }

        @Test
        public void deveCalcularGanhosSemPremium() {
            editor.setPremium(false);

            Ganhos ganhos = calculadoraGanhos.calcular(post);

            assertEquals(new BigDecimal(35), ganhos.getTotalGanho());
            assertEquals(7, ganhos.getQuantidadePalavras());
            assertEquals(editor.getValorPagoPorPalavra(), ganhos.getValorPagoPorPalavra());
        }

    }

}