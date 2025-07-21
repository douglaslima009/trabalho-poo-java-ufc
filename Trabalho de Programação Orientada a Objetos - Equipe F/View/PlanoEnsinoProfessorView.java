package View;
import java.util.List;
import java.util.Scanner;
import Controller.PlanoEnsinoProfessorController;
import Model.*;

public class PlanoEnsinoProfessorView implements Observer{

    private Model model;
    private PlanoEnsinoProfessorController planoEnsinoProfessorController;

    public void iniciarPlanoEnsinoView(Model model){
        if(model != null){
            this.model = model;
            planoEnsinoProfessorController = new PlanoEnsinoProfessorController();
            planoEnsinoProfessorController.iniciarPlanoEnsino(model, this);
            exibirPlanoEnsinoProfessorLogado();
        }   
    }   

    public void exibirPlanoEnsinoProfessorLogado(){
        System.out.println("========== PLANOS DE ENSINO DO PROFESSOR =========="); // Título ajustado para o plural
        Professor professorLogado = model.getAutenticUser();

        if (professorLogado == null) {
            mostarMensagem("Nenhum professor logado. Faça login para ver seu plano de ensino.");
            return;
        }

        // Busca a LISTA de planos, em vez de um único plano.
        List<PlanoDeEnsino> planos = professorLogado.getPlanosDeEnsino();

        // Verifica se a lista está vazia.
        if (planos == null || planos.isEmpty()) {
            mostarMensagem("O professor " + professorLogado.getNomeProfessor() + " ainda não possui um plano de ensino cadastrado.");
            return;
        }

        int contador = 1;
        // Cria um laço para percorrer e exibir CADA plano na lista.
        for (PlanoDeEnsino plano : planos) {
            
            // Adiciona um cabeçalho para cada plano, útil quando há mais de um.
            if (planos.size() > 1) {
                System.out.println("---------- EXIBINDO PLANO " + contador + " de " + planos.size() + " ----------");
            }
            
            StringBuilder detalhesPlano = new StringBuilder();
            detalhesPlano.append("\nProfessor: ").append(plano.getNomeProfessor());
            detalhesPlano.append("\nDisciplina: ").append(plano.getCodigoMateria()).append(" - ").append(plano.getNomeMateria());
            detalhesPlano.append("\nTurma: ").append(plano.getTurma()).append("\n");
            detalhesPlano.append("\nCarga Horária Teórica: ").append(plano.getCargaHorariaTeorica());
            detalhesPlano.append("\nCarga Horária Prática: ").append(plano.getCargaHorariaPratica());
            detalhesPlano.append("\nCarga Horária Total: ").append(plano.getCargaHorariaTotal());
            detalhesPlano.append("\nData Inicial: ").append(plano.getDataInicialFormatada());
            detalhesPlano.append("\nData Final: ").append(plano.getDataFinalFormatada());
            detalhesPlano.append("\n\n1. JUSTIFICATIVA:\n").append(plano.getJustificativa());
            detalhesPlano.append("\n\n2. EMENTA:\n").append(plano.getEmenta());
            detalhesPlano.append("\n\n3. OBJETIVOS:\n").append(plano.getObjetivos());
            detalhesPlano.append("\n\n4. METODOLOGIA DE ENSINO:\n").append(plano.getMetodologia());
            detalhesPlano.append("\n\n5. ATIVIDADES DISCENTES:\n").append(plano.getAtividadesDiscentes());
            detalhesPlano.append("\n\n6. SISTEMA DE AVALIAÇÃO:\n").append(plano.getAvaliacoes());
            detalhesPlano.append("\n\n7. BIBLIOGRAFIA BÁSICA E COMPLEMENTAR:\n").append(plano.getBibliografia());
            detalhesPlano.append("\n\n8. PARECER:\n").append(plano.getParecer());

            mostarMensagem(detalhesPlano.toString());
            contador++;
        }
    }

    public void mostarMensagem(String mensagem){
        System.out.println("=====================\n" + mensagem +"\n=====================\n");
    }

    @Override
    public void update(){}
    
}