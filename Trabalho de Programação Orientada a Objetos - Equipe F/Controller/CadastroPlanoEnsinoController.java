package Controller;
import Model.*;
import View.CadastroPlanoEnsinoView;
import View.MateriaEnum;

public class CadastroPlanoEnsinoController {
    private Model model;
    private CadastroPlanoEnsinoView view;

    public void iniciarCadastroPlanoEnsino(Model model, CadastroPlanoEnsinoView view) {
        if (model != null && view != null) {
            this.model = model;
            this.view = view;
        }
    }
    /**
     * Verifica se já existe um plano de ensino para uma dada matéria e turma.
     * Este método DELEGA a verificação para a classe Model.
     *
     * @param materia A matéria a ser verificada.
     * @param turma A turma a ser verificada.
     * @return true se o plano já existe, false caso contrário.
     */

    public boolean planoJaExiste(MateriaEnum materia, String turma) {
        if (model == null) {
            // Se o model for nulo, não é possível verificar. Retorna false para evitar bloqueio.
            System.out.println("AVISO: Model não inicializado no controller, verificação de duplicidade pulada.");
            return false;
        }
        // A chamada abaixo pressupõe que o método `planoDeEnsinoExiste` está implementado no Model.
        return model.planoDeEnsinoExiste(materia.getCodigo(), turma);
    }


    public void salvarPlanoEnsino(PlanoDeEnsino plano) {
        try {
            if (plano == null) {
                throw new IllegalArgumentException("Plano de ensino não pode ser nulo");
            }

            // Validar se o professor está logado.
            Professor professorLogado = model.getAutenticUser();
            if (professorLogado == null) {
                throw new IllegalStateException("Nenhum(a) professor(a) está logado no sistema");
            }

            // Validar dados obrigatórios.
            validarDadosObrigatorios(plano);

            // Associar o plano ao professor logado e adicioná-lo à lista geral no modelo.
            model.adicionarPlanoDeEnsino(plano); // Adiciona na lista geral do model
            professorLogado.adicionarPlanoDeEnsino(plano); // Associa ao professor

            // Exibir mensagem de sucesso
            view.exibirMensagem("Plano de ensino cadastrado com sucesso!");
            
            // Exibir resumo do plano cadastrado
            exibirResumoPlano(plano);

        } catch (IllegalArgumentException | IllegalStateException e) {
            view.exibirErro(e.getMessage());
        } catch (Exception e) {
            view.exibirErro("Erro inesperado ao salvar plano de ensino: " + e.getMessage());
        }
    }

    private void validarDadosObrigatorios(PlanoDeEnsino plano) {
        if (plano.getNomeProfessor() == null || plano.getNomeProfessor().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do(a) professor(a) é obrigatório");
        }
        
        if (plano.getCodigoMateria() == null || plano.getCodigoMateria().trim().isEmpty()) {
            throw new IllegalArgumentException("Código da matéria é obrigatório");
        }
        
        if (plano.getNomeMateria() == null || plano.getNomeMateria().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da matéria é obrigatório");
        }

        // --- VALIDAÇÃO ADICIONADA ---
        if (plano.getTurma() == null || plano.getTurma().trim().isEmpty()) {
            throw new IllegalArgumentException("Turma é obrigatória");
        }
        
        if (plano.getCargaHorariaTeorica() < 0) {
            throw new IllegalArgumentException("Carga horária teórica deve ser maior ou igual a zero");
        }
        
        if (plano.getCargaHorariaPratica() < 0) {
            throw new IllegalArgumentException("Carga horária prática deve ser maior ou igual a zero");
        }
        
        if (plano.getCargaHorariaTotal() <= 0) {
            throw new IllegalArgumentException("Carga horária total deve ser maior que zero");
        }
        
        if (plano.getDataInicial() == null) {
            throw new IllegalArgumentException("Data inicial é obrigatória");
        }
        
        if (plano.getDataFinal() == null) {
            throw new IllegalArgumentException("Data final é obrigatória");
        }
        
        if (plano.getJustificativa() == null || plano.getJustificativa().trim().isEmpty()) {
            throw new IllegalArgumentException("Justificativa é obrigatória");
        }
        
        if (plano.getEmenta() == null || plano.getEmenta().trim().isEmpty()) {
            throw new IllegalArgumentException("Ementa é obrigatória");
        }
        
        if (plano.getObjetivos() == null || plano.getObjetivos().trim().isEmpty()) {
            throw new IllegalArgumentException("Objetivos são obrigatórios");
        }
        
        if (plano.getMetodologia() == null || plano.getMetodologia().trim().isEmpty()) {
            throw new IllegalArgumentException("Metodologia é obrigatória");
        }
        
        if (plano.getAtividadesDiscentes() == null || plano.getAtividadesDiscentes().trim().isEmpty()) {
            throw new IllegalArgumentException("Atividades discentes são obrigatórias");
        }
        
        if (plano.getAvaliacoes() == null || plano.getAvaliacoes().trim().isEmpty()) {
            throw new IllegalArgumentException("Sistema de avaliação é obrigatório");
        }
        
        if (plano.getBibliografia() == null || plano.getBibliografia().trim().isEmpty()) {
            throw new IllegalArgumentException("Bibliografia é obrigatória");
        }
        
        if (plano.getParecer() == null || plano.getParecer().trim().isEmpty()) {
            throw new IllegalArgumentException("Parecer é obrigatório");
        }
    }

    private void exibirResumoPlano(PlanoDeEnsino plano) {
        StringBuilder resumo = new StringBuilder();
        resumo.append("RESUMO DO PLANO DE ENSINO CADASTRADO:\n\n");
        resumo.append("Professor(a): ").append(plano.getNomeProfessor()).append("\n");
        resumo.append("Disciplina: ").append(plano.getCodigoMateria()).append(" - ").append(plano.getNomeMateria()).append("\n");
        resumo.append("Turma: ").append(plano.getTurma()).append("\n");
        resumo.append("Carga Horária: ").append(plano.getCargaHorariaTotal()).append(" horas ");
        resumo.append("(Teórica: ").append(plano.getCargaHorariaTeorica()).append(", ");
        resumo.append("Prática: ").append(plano.getCargaHorariaPratica()).append(")\n");
        resumo.append("Período: ").append(plano.getDataInicialFormatada()).append(" a ").append(plano.getDataFinalFormatada()).append("\n");
        
        view.exibirMensagem(resumo.toString());
    }

    public void cancelarCadastro() {
        view.exibirMensagem("Cadastro de plano de ensino cancelado.");
    }
}
