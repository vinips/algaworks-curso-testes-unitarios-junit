package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoPost;
import com.algaworks.junit.blog.exception.PostNaoEncontradoException;
import com.algaworks.junit.blog.exception.RegraNegocioException;
import com.algaworks.junit.blog.modelo.Editor;
import com.algaworks.junit.blog.modelo.Ganhos;
import com.algaworks.junit.blog.modelo.Notificacao;
import com.algaworks.junit.blog.modelo.Post;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
public class CadastroPostTest {

    @InjectMocks
    private CadastroPost cadastroPost;

    @Mock
    private ArmazenamentoPost armazenamentoPostMock;

    @Mock
    private CalculadoraGanhos calculadoraGanhosMock;

    @Mock
    private GerenciadorNotificacao gerenciadorNotificacaoMock;

    @Captor
    private ArgumentCaptor<Notificacao> notificacaoArgumentCaptor;

    @Spy
    private Editor editor = new Editor(1L, "Vini", "vinicius@teste.com", BigDecimal.TEN, true);

    private static final String CONTEUDO_POST = "Curso ensinando como realizar testes unitários com qualidade e eficiência...";

    @Nested
    public class Criar {

        @Nested
        public class CriarComPostValido {

            private final Ganhos ganhos = new Ganhos(BigDecimal.TEN, 1, BigDecimal.TEN);

            @Spy
            private Post post = new Post("Treinando Testes Unitários", CONTEUDO_POST, editor, false, false);

            @BeforeEach
            public void init() {
                when(armazenamentoPostMock.salvar(any(Post.class)))
                        .thenAnswer(invocation -> {
                            Post postInvocado = invocation.getArgument(0, Post.class);
                            postInvocado.setId(2L);
                            return postInvocado;
                        });
            }

            private Post criarTest(Post post){
                return cadastroPost.criar(post);
            }

            @Test
            public void Dado_um_post_valido_Quando_criar_Entao_deve_retornar_um_post_com_id() {
                Post postCriado = criarTest(post);

                assertEquals(2L, postCriado.getId());
            }

            @Test
            public void Dado_um_post_valido_Quando_criar_Entao_deve_chamar_metodo_salvar_do_armazenamento() {
                criarTest(post);

                verify(armazenamentoPostMock).salvar(any(Post.class));
            }

            @Test
            public void Dado_um_post_valido_Quando_criar_Entao_deve_enviar_notificacoes() {
                criarTest(post);

                verify(gerenciadorNotificacaoMock).enviar(notificacaoArgumentCaptor.capture());

                Notificacao notificacao = notificacaoArgumentCaptor.getValue();
                assertEquals("Novo post criado -> " + post.getTitulo(), notificacao.getConteudo());
            }

            @Test
            public void Dado_um_post_valido_Quando_criar_Entao_deve_calcular_ganhos_antes_de_salvar() {
                when(calculadoraGanhosMock.calcular(post)).thenReturn(ganhos);

                Post postCriado = criarTest(post);

                assertEquals(ganhos, postCriado.getGanhos());

                InOrder inOrder = inOrder(calculadoraGanhosMock, armazenamentoPostMock);
                inOrder.verify(calculadoraGanhosMock).calcular(post);
                inOrder.verify(armazenamentoPostMock).salvar(post);
            }

        }

        @Nested
        public class CriarComPostNull {

            @Test
            public void Dado_um_post_nulo_Quando_criar_Entao_deve_retornar_exception() {
                assertThrows(NullPointerException.class, () -> cadastroPost.criar(null));

                verify(armazenamentoPostMock, never()).salvar(any(Post.class));
                verify(gerenciadorNotificacaoMock, never()).enviar(any(Notificacao.class));
            }

        }
    }

    @Nested
    public class Editar {

        @Nested
        public class EditarComPostValido {

            private final String titulo = "Treinando Testes Unitários";

            @Spy
            private Post post = new Post(1L, titulo, CONTEUDO_POST, editor, null, null, false, false);

            @BeforeEach
            public void init() {
                when(armazenamentoPostMock.salvar(any(Post.class))).thenReturn(post);
                when(armazenamentoPostMock.encontrarPorId(1L)).thenReturn(Optional.of(post));
            }

            @Test
            public void Dado_um_post_valido_Quando_editar_Entao_deve_alterar_post_salvo() {
                Post postAtualizado = new Post(1L, "Reforçando Testes Unitários", "Conteudo Alterado", editor, null, null, false, false);

                Post postEditado = cadastroPost.editar(postAtualizado);

                assertNotEquals(titulo, postEditado.getTitulo());
                assertNotEquals(CONTEUDO_POST, postEditado.getConteudo());

                verify(post).atualizarComDados(postAtualizado);
                verify(armazenamentoPostMock).salvar(post);
            }

        }

        @Nested
        public class EditarComPostInexistente {

            @Spy
            private Post post = new Post(99L, "Treinando Testes Unitários", CONTEUDO_POST, editor, null, null, false, false);

            @BeforeEach
            public void init() {
                when(armazenamentoPostMock.encontrarPorId(99L)).thenReturn(Optional.empty());
            }

            @Test
            public void Dado_um_post_inexistente_Quando_editar_Entao_nao_deve_alterar_post_salvo() {
                assertThrows(PostNaoEncontradoException.class, () -> cadastroPost.editar(post));

                verify(armazenamentoPostMock, never()).salvar(any(Post.class));
            }

        }

        @Nested
        public class EditarComPostNull {

            @Test
            public void Dado_um_post_nulo_Quando_editar_Entao_deve_lancar_exception() {
                assertThrows(NullPointerException.class, () -> cadastroPost.editar(null));

                verify(armazenamentoPostMock, never()).salvar(any(Post.class));
            }

        }
    }

    @Nested
    public class Remover {

        @Nested
        public class RemoverComPostValido {

            @Spy
            private Post post = new Post(1L, "Treinando Testes Unitários", CONTEUDO_POST, editor, null, null, false, false);

            @BeforeEach
            public void init() {
                when(armazenamentoPostMock.encontrarPorId(1L)).thenReturn(Optional.of(post));
            }

            private void removerTest(){
                cadastroPost.remover(post.getId());
            }

            @Test
            public void Dado_um_post_valido_nao_pago_e_nao_publicado_Quando_remover_Entao_deve_remover_post_do_sistema() {
                removerTest();

                verify(armazenamentoPostMock).remover(post.getId());
            }

            @Test
            public void Dado_um_post_valido_pago_Quando_remover_Entao_deve_lancar_exception() {
                post.pagar();
                RegraNegocioException regraNegocioException = assertThrows(RegraNegocioException.class, this::removerTest);

                assertEquals("Um post pago não pode ser removido", regraNegocioException.getMessage());
                verify(armazenamentoPostMock, never()).remover(post.getId());
            }

            @Test
            public void Dado_um_post_valido_publicado_Quando_remover_Entao_deve_lancar_exception() {
                post.publicar();
                RegraNegocioException regraNegocioException = assertThrows(RegraNegocioException.class, this::removerTest);

                assertEquals("Um post publicado não pode ser removido", regraNegocioException.getMessage());
                verify(armazenamentoPostMock, never()).remover(post.getId());
            }

        }

        @Nested
        public class RemoverComPostInexistente {

            @Spy
            private Post post = new Post(99L, "Treinando Testes Unitários", CONTEUDO_POST, editor, null, null, false, false);

            @BeforeEach
            public void init() {
                when(armazenamentoPostMock.encontrarPorId(99L)).thenReturn(Optional.empty());
            }

            @Test
            public void Dado_um_post_invalido_Quando_remover_Entao_deve_lancar_exception() {
                assertThrows(PostNaoEncontradoException.class, () -> cadastroPost.remover(post.getId()));

                verify(armazenamentoPostMock, never()).remover(post.getId());
            }

        }

        @Nested
        public class RemoverComPostNull {
            @Test
            public void Dado_um_post_invalido_Quando_remover_Entao_deve_lancar_exception() {
                assertThrows(NullPointerException.class, () -> cadastroPost.remover(null));

                verify(armazenamentoPostMock, never()).remover(anyLong());
            }

        }

    }

}