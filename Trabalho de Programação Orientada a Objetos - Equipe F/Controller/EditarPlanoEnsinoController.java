package Controller;
import Model.*;
import View.EditarPlanoEnsinoView;

/**
 * Controller responsável pela lógica de edição de um Plano de Ensino existente.
 */
public class EditarPlanoEnsinoController {
    private Model model;
    private EditarPlanoEnsinoView view;

    // Método de inicialização para conectar o Controller com o Model e a View.
    public void iniciarEdicaoPlanoEnsino(Model model, EditarPlanoEnsinoView view) {
        if (model != null && view != null) {
            this.model = model;
            this.view = view;
        }
    }
    
    /**
     * Verifica no Model se a nova combinação de matéria e turma já não pertence a OUTRO plano,
     * evitando duplicidade durante a edição.
     *
     * @return true se a combinação já existe em outro plano, false caso contrário.
     */
    public boolean verificarDuplicidadeNaEdicao(PlanoDeEnsino planoSendoEditado, String novoCodigoMateria, String novaTurma) {
        if (model == null) {
            return false;
        }
        // Delega a lógica complexa de verificação para o Model.
        return model.planoExisteParaOutro(planoSendoEditado, novoCodigoMateria, novaTurma);
    }

    /**
     * Orquestra a validação e a confirmação da edição de um plano de ensino.
     */
    public void salvarPlanoEnsinoEditado(PlanoDeEnsino planoEditado) {
        try {
            if (planoEditado == null) {
                throw new IllegalArgumentException("Plano de ensino editado não pode ser nulo");
            }

            // Garante que um professor está logado para autorizar a alteração.
            Professor professorLogado = model.getAutenticUser();
            if (professorLogado == null) {
                throw new IllegalStateException("Nenhum(a) professor(a) está logado no sistema");
            }

            // Valida se todos os campos essenciais do plano continuam preenchidos.
            validarDadosObrigatorios(planoEditado);
            
            // Note que não há um método "model.atualizar(plano)".
            // A arquitetura assume que a View edita a própria referência do objeto
            // que veio do Model. Este método apenas valida e confirma essa alteração.
            
            view.exibirMensagem("Plano de ensino atualizado com sucesso!");
            
            exibirResumoPlano(planoEditado);

        } catch (IllegalArgumentException | IllegalStateException e) {
            // Em caso de erro de validação, exibe a mensagem para o usuário.
            view.exibirErro(e.getMessage());
        } catch (Exception e) {
            view.exibirErro("Erro inesperado ao salvar plano de ensino editado: " + e.getMessage());
        }
    }

    /**
     * Método auxiliar que lança uma exceção se algum campo obrigatório estiver vazio.
     * Mantém o método principal de salvamento mais limpo e focado.
     */
    private void validarDadosObrigatorios(PlanoDeEnsino plano) {
        if (plano.getNomeProfessor() == null || plano.getNomeProfessor().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do(a) professor(a) é obrigatório");
        }
        
        if (plano.getTurma() == null || plano.getTurma().trim().isEmpty()) {
            throw new IllegalArgumentException("Turma é obrigatória");
        }
        
        if (plano.getParecer() == null || plano.getParecer().trim().isEmpty()) {
            throw new IllegalArgumentException("Parecer é obrigatório");
        }
    }

    private void exibirResumoPlano(PlanoDeEnsino plano) {
        StringBuilder resumo = new StringBuilder();
        resumo.append("RESUMO DO PLANO DE ENSINO ATUALIZADO:\n\n");
        resumo.append("Professor(a): ").append(plano.getNomeProfessor()).append("\n");
        resumo.append("Disciplina: ").append(plano.getCodigoMateria()).append(" - ").append(plano.getNomeMateria()).append("\n");
        resumo.append("Turma: ").append(plano.getTurma()).append("\n");
        resumo.append("Período: ").append(plano.getDataInicialFormatada()).append(" a ").append(plano.getDataFinalFormatada()).append("\n");
        
        view.exibirMensagem(resumo.toString());
    }
}