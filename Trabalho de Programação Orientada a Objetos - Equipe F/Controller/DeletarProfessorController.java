package Controller;
import View.DeletarProfessorView;
import View.Observer;
import Model.*;

/**
 * Controller que gerencia a lógica para deletar um professor.
 */
public class DeletarProfessorController implements Observer{
    
    private Model model;
    private DeletarProfessorView deletarProfessorView;

    /**
     * Conecta o Controller com o Model e a View e se registra para ouvir atualizações.
     */
    public void iniciarControllerDelete(Model model, DeletarProfessorView deleteProfessorView){
        if(model != null && deleteProfessorView != null){
            this.model = model;
            this.deletarProfessorView = deleteProfessorView;
            model.registerObserver(this); // Começa a "escutar" o model.
        }
    }

    /**
     * Executa a exclusão do professor após a confirmação do usuário na View.
     */
    public void deletarProfessor(String confirm){
        if(confirm.equalsIgnoreCase("OK")){
            int matricula = deletarProfessorView.getMatricula();
            boolean validar = deletarProfessorView.getValidar(); // Pega a confirmação da checkbox/botão.
            Professor professor = model.buscarProfessor(deletarProfessorView.getMatricula());

            // Dupla verificação: garante que o professor existe E que o usuário confirmou a ação.
            if(professor != null && validar == true){
                // Delega ao Model a tarefa de efetivamente remover o professor.
                model.deletarProfessor(matricula, validar);
                deletarProfessorView.mostarMensagem("Professor deletado com sucesso!!");
            }
        }
    }

    /**
     * Busca um professor pela matrícula e retorna seus dados formatados para exibição na tela.
     * @return Uma String com os dados do professor ou uma mensagem de erro.
     */
    public String buscarProfessor(int matricula){
        StringBuilder stringFormatada = new StringBuilder();
        if(matricula > 0){
            Professor professorBuscar = model.buscarProfessor(matricula);
            
            // Se o professor for encontrado, formata seus dados para a View.
            if(professorBuscar != null){
                stringFormatada.append("Nome: ").append(professorBuscar.getNomeProfessor()).append("\n")
                               .append("Matricula: ").append(professorBuscar.getMatricula()).append("\n")
                               .append("Materia: ").append(professorBuscar.getMateria().getNomeMateria()).append("\n");
            }else{
                stringFormatada.append("Professor com matricula ").append(matricula).append(" nao encontrada");
            }
        }else{
            stringFormatada.append("Matrícula Inválida");
        }

        return stringFormatada.toString();
    }

    /**
     * Método da interface Observer. Fica vazio pois este controller reage a ações diretas do usuário.
     */
    public void update(){}
}