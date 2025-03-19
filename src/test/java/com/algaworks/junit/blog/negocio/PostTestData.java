package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.modelo.Post;

public class PostTestData {

    public static final String TITULO = "Treinando Testes Unitários";

    public static final String TITULO_ATUALIZADO = "Titulo Testes Unitários Alterado";

    public static final String CONTEUDO_POST = "Curso ensinando como realizar testes unitários com qualidade e eficiência...";

    public static final String CONTEUDO_POST_ATUALIZADO = "Conteudo Alterado";

    public static Post.Builder novoPost() {
        return Post.builder()
                .comId(null)
                .comTitulo(TITULO)
                .comConteudo(CONTEUDO_POST)
                .comAutor(null)
                .comSlug(null)
                .comPago(false)
                .comPublicado(false);
    }

    public static Post.Builder postExistente() {
        return novoPost().comId(1L);
    }

    public static Post.Builder postInexistente() {
        return novoPost().comId(99L);
    }

    public static Post.Builder postAtualizado() {
        return Post.builder()
                .comId(1L)
                .comTitulo(TITULO_ATUALIZADO)
                .comConteudo(CONTEUDO_POST_ATUALIZADO)
                .comAutor(null)
                .comSlug(null)
                .comPago(false)
                .comPublicado(false);
    }

}
