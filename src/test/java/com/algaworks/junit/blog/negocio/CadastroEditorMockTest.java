package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoEditor;
import com.algaworks.junit.blog.exception.EditorNaoEncontradoException;
import com.algaworks.junit.blog.exception.RegraNegocioException;
import com.algaworks.junit.blog.modelo.Editor;
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
public class CadastroEditorMockTest {

    @InjectMocks
    private CadastroEditor cadastroEditor;

    @Mock
    private ArmazenamentoEditor armazenamentoEditorMock;

    @Mock
    private GerenciadorEnvioEmail gerenciadorEnvioEmailMock;

    @Captor
    private ArgumentCaptor<Mensagem> mensagemArgumentCaptor;

    @Nested
    public class CadastrarComEditorValido {

        @Spy
        private Editor editor = EditorTestData.novoEditor().build();

        @BeforeEach
        public void beforeEach() {
            when(armazenamentoEditorMock.salvar(any(Editor.class)))
                    .thenAnswer(invocation -> {
                        Editor editorInvocado = invocation.getArgument(0, Editor.class);
                        editorInvocado.setId(1L);
                        return editorInvocado;
                    });
        }

        @Test
        public void Dado_um_editor_valido_Quando_criar_Entao_deve_retornar_um_id_de_cadastro() {
            Editor editorSalvo = cadastroEditor.criar(editor);

            assertEquals(1L, editorSalvo.getId());
        }

        @Test
        public void Dado_um_editor_valido_Quando_criar_Entao_deve_chamar_metodo_salvar_do_armazenamento() {
            cadastroEditor.criar(editor);
            verify(armazenamentoEditorMock).salvar(eq(editor));
        }

        @Test
        public void Dado_um_editor_valido_Quando_criar_e_lancar_exception_ao_salvar_Entao_nao_deve_enviar_email() {
            when(armazenamentoEditorMock.salvar(editor))
                    .thenThrow(new RuntimeException());

            assertAll("Não deve enviar email quando lançar Exception do armazenamento",
                    () -> assertThrows(RuntimeException.class, () -> cadastroEditor.criar(editor)),
                    () -> verify(gerenciadorEnvioEmailMock, never()).enviarEmail(any(Mensagem.class)));
        }

        @Test
        public void Dado_um_editor_valido_Quando_criar_Entao_Deve_enviar_email_com_destino_ao_editor() {
            Editor editorSalvo = cadastroEditor.criar(editor);

            verify(gerenciadorEnvioEmailMock).enviarEmail(mensagemArgumentCaptor.capture());
            Mensagem mensagemCapturada = mensagemArgumentCaptor.getValue();
            assertEquals(editorSalvo.getEmail(), mensagemCapturada.getDestinatario());
        }

        @Test
        public void Dado_um_editor_valido_Quando_cadastrar_Entao_deve_verificar_o_email() {
            cadastroEditor.criar(editor);
            verify(editor, atLeast(1)).getEmail();
        }

        @Test
        public void Dado_um_editor_com_email_existente_Quando_criar_Entao_deve_lancar_exception() {
            when(armazenamentoEditorMock.encontrarPorEmail("vinicius@teste.com"))
                    .thenReturn(Optional.empty())
                    .thenReturn(Optional.of(editor));

            Editor editorComEmailExistente = EditorTestData.novoEditor().build();

            cadastroEditor.criar(editor);
            assertThrows(RegraNegocioException.class, () -> cadastroEditor.criar(editorComEmailExistente));
        }

        @Test
        public void Dado_um_editor_valido_Quando_cadastrar_Entao_deve_enviar_email_apos_salvar(){
            cadastroEditor.criar(editor);
            InOrder inOrder = inOrder(armazenamentoEditorMock, gerenciadorEnvioEmailMock);
            inOrder.verify(armazenamentoEditorMock).salvar(editor);
            inOrder.verify(gerenciadorEnvioEmailMock).enviarEmail(any(Mensagem.class));
        }
    }

    @Nested
    public class CadastrarComEditorNull {

        @Test
        public void Dado_um_editor_nulo_Quando_cadastrar_Entao_deve_lancar_exception () {
            assertThrows(NullPointerException.class, () -> cadastroEditor.criar(null));
            verify(armazenamentoEditorMock, never()).salvar(any());
            verify(gerenciadorEnvioEmailMock, never()).enviarEmail(any());
        }
    }

    @Nested
    public class EditarComEditorValido {

        @Spy
        private Editor editor = EditorTestData.editorExistente().build();

        @BeforeEach
        public void init() {
            when(armazenamentoEditorMock.salvar(editor)).thenReturn(editor);
            when(armazenamentoEditorMock.encontrarPorId(1L)).thenReturn(Optional.of(editor));
        }

        @Test
        public void Dado_um_editor_valido_Quando_editar_Entao_deve_alterar_editor_salvo() {
            Editor editorAtualizado = EditorTestData.editorAtualizado().build();

            cadastroEditor.editar(editorAtualizado);
            verify(editor, times(1)).atualizarComDados(editorAtualizado);

            InOrder inOrder = inOrder(editor, armazenamentoEditorMock);
            inOrder.verify(editor).atualizarComDados(editorAtualizado);
            inOrder.verify(armazenamentoEditorMock).salvar(editor);

        }

    }

    @Nested
    public class EditarComEditorInexistente {

        private Editor editor = EditorTestData.editorInexistente().build();

        @BeforeEach
        public void init() {
            when(armazenamentoEditorMock.encontrarPorId(99L)).thenReturn(Optional.empty());
        }

        @Test
        public void Dado_um_editor_inexistente_Quando_editar_Entao_deve_lancar_exception() {
            assertThrows(EditorNaoEncontradoException.class, () -> cadastroEditor.editar(editor));
            verify(armazenamentoEditorMock, never()).salvar(any(Editor.class));

        }


    }

}
