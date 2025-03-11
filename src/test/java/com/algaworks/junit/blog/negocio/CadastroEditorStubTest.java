package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.exception.RegraNegocioException;
import com.algaworks.junit.blog.modelo.Editor;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CadastroEditorStubTest {

    CadastroEditor cadastroEditor;
    ArmazenamentoEditorFixoEmMemoria armazenamentoEditor;
    Editor editor;

    @BeforeEach
    void beforeEach() {
        editor = new Editor(null, "Vini", "vini@teste.com", BigDecimal.TEN, true);

        armazenamentoEditor = new ArmazenamentoEditorFixoEmMemoria();
        cadastroEditor = new CadastroEditor(
                armazenamentoEditor,
                new GerenciadorEnvioEmail() {
                    @Override
                    public void enviarEmail(Mensagem msg){
                        System.out.println("Enviando mensagem para: " + msg.getDestinatario() + "\n Conteúdo: " + msg.getConteudo());
                    }
                }
        );
    }

    @Nested
    public class Criar{
        @Test
        public void Dado_um_editor_null_Quando_criar_Entao_deve_retornar_um_id_de_cadastro() {
            Editor novoEditor = cadastroEditor.criar(editor);
            assertEquals(1L, novoEditor.getId());
            assertTrue(armazenamentoEditor.chamouSalvar);
        }

        @Test
        public void Dado_um_editor_valido_Quando_criar_Entao_deve_lancar_exception() {
            assertThrows(NullPointerException.class, () -> cadastroEditor.criar(null));
            assertFalse(armazenamentoEditor.chamouSalvar);

        }

        @Test
        public void Dado_um_editor_com_email_existente_Quando_criar_Entao_deve_lancar_exception() {
            editor.setEmail("vini.existe@teste.com");
            assertThrows(RegraNegocioException.class, () -> cadastroEditor.criar(editor));
        }

        @Test
        public void Dado_um_editor_com_email_existente_Quando_criar_Entao_deve_deve_salvar() {
            editor.setEmail("vini.existe@teste.com");
            try{
                cadastroEditor.criar(editor);
            } catch (RegraNegocioException rne){ }
            assertFalse(armazenamentoEditor.chamouSalvar);
        }
    }


}