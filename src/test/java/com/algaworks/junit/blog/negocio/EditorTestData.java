package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.modelo.Editor;

import java.math.BigDecimal;

public class EditorTestData {

    public static Editor.Builder novoEditor() {
        return Editor.builder()
                .comId(null)
                .comNome("Vini")
                .comEmail("vinicius@teste.com")
                .comValorPagoPorPalavra(BigDecimal.TEN)
                .comPremium(true);
    }

    public static Editor.Builder editorExistente() {
        return EditorTestData.novoEditor()
                .comId(1L);
    }

    public static Editor.Builder editorInexistente() {
        return EditorTestData.novoEditor()
                .comId(99L);
    }

    public static Editor.Builder editorAtualizado() {
       return Editor.builder()
                .comId(1L)
                .comNome("Vini Silveira")
                .comEmail("vinicius.silveira@teste.com")
                .comValorPagoPorPalavra(BigDecimal.ZERO)
                .comPremium(false);
    }
}
