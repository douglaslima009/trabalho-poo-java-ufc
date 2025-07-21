package Controller;
import Model.*;
import View.UpdateProfessorView;

/**
 * Controller que gerencia a lógica para atualizar os dados de um professor.
 */
public class UpdateProfessorController {
    private Model model;
    private UpdateProfessorView updateProfessorView;

    /**
     * Inicializa o controller, conectando-o com o Model e a View de atualização.
     */
    public void inciarControllerUpdate(Model model, UpdateProfessorView updateProfessorView){
        if(model != null && updateProfessorView != null){
            this.model = model;
            this.updateProfessorView = updateProfessorView;
        }
    }

    /**
     * Recebe os novos dados da View e os aplica ao professor correspondente no Model.
     */
    public void atualizarProfessor(int matricula, String novoNome, String novaSenha) {
        // Primeiro, busca a referência do professor que será alterado.
        Professor professor = model.buscarProfessor(matricula);
        
        if (professor != null) {
            try {
                // As alterações são feitas diretamente no objeto vindo do Model.
                // Isso funciona pois Java trabalha com referências de objetos.
                professor.setNomeProfessor(novoNome);
                professor.setSenha(novaSenha);
                updateProfessorView.mostrarMensagem("Dados do professor atualizados com sucesso!");
            } catch (IllegalArgumentException e) {
                // Captura erros de validação que podem vir dos métodos 'set' (ex: senha muito curta).
                updateProfessorView.mostrarMensagem("Erro ao atualizar dados: " + e.getMessage());
            }
        } else {
            updateProfessorView.mostrarMensagem("Professor com a matrícula " + matricula + " não encontrado.");
        }
    }
}