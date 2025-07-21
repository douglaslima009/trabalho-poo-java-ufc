package View;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import Model.*;
import Controller.EditarPlanoEnsinoController;

public class EditarPlanoEnsinoView implements Observer {
    private Model model;
    private EditarPlanoEnsinoController controller;
    private Scanner scanner;

    public EditarPlanoEnsinoView() {
        this.scanner = new Scanner(System.in);
    }

    public void iniciarEdicaoPlanoEnsino(Model model) {
        if (model != null) {
            this.model = model;
            this.controller = new EditarPlanoEnsinoController();
            this.controller.iniciarEdicaoPlanoEnsino(model, this);
            model.registerObserver(this);
            exibirFormularioEdicao();
        }
    }

    public void exibirFormularioEdicao() {
        System.out.println("================ EDITAR PLANO DE ENSINO ================\n");

        Professor professorLogado = model.getAutenticUser();
        if (professorLogado == null) {
            exibirMensagem("Nenhum(a) professor(a) logado. Faça login para editar seu plano de ensino.");
            return;
        }

        List<PlanoDeEnsino> planosDoProfessor = professorLogado.getPlanosDeEnsino();

        if (planosDoProfessor == null || planosDoProfessor.isEmpty()) {
            exibirMensagem("O(A) professor(a) " + professorLogado.getNomeProfessor() + " ainda não possui planos de ensino para edição.");
            return;
        }

        System.out.println("Professor(a): " + professorLogado.getNomeProfessor());
        System.out.println("Selecione o plano de ensino que deseja editar:");
        for (int i = 0; i < planosDoProfessor.size(); i++) {
            PlanoDeEnsino plano = planosDoProfessor.get(i);
            System.out.println("[" + (i + 1) + "] - " + plano.getNomeMateria() + " (" + plano.getCodigoMateria() + ") - " + plano.getTurma());
        }
        System.out.println("[" + (planosDoProfessor.size() + 1) + "] - Cancelar Edição");

        int escolha;
        PlanoDeEnsino planoParaEditar;

        try {
            System.out.print("Digite o número do plano: ");
            escolha = Integer.parseInt(scanner.nextLine());

            if (escolha > 0 && escolha <= planosDoProfessor.size()) {
                planoParaEditar = planosDoProfessor.get(escolha - 1);
            } else if (escolha == planosDoProfessor.size() + 1) {
                exibirMensagem("Edição cancelada pelo professor.");
                return;
            } else {
                exibirErro("Opção inválida.");
                return;
            }
        } catch (NumberFormatException e) {
            exibirErro("Entrada inválida. Por favor, digite um número.");
            return;
        }

        try {
            coletarDadosEdicaoPlanoEnsino(planoParaEditar);
            controller.salvarPlanoEnsinoEditado(planoParaEditar);

        } catch (IllegalArgumentException e) {
            exibirErro("Erro: " + e.getMessage());
            exibirMensagem("Edição cancelada.");
        }
    }

    private MateriaEnum getMateriaEnumByCode(String code) {
        for (MateriaEnum m : MateriaEnum.values()) {
            if (m.getCodigo().equals(code)) {
                return m;
            }
        }
        return null; // Retorna null se não encontrar
    }

    private void coletarDadosEdicaoPlanoEnsino(PlanoDeEnsino plano) {
        System.out.println("\n================ EDITAR DADOS DO PLANO DE ENSINO ================\n");
        System.out.println("Você está editando o plano da matéria: " + plano.getNomeMateria() + " - " + plano.getTurma());
        System.out.println("Deixe o campo em branco e pressione Enter para manter o valor atual.");
        
        MateriaEnum materiaOriginal = getMateriaEnumByCode(plano.getCodigoMateria());
        String turmaOriginal = plano.getTurma();
        
        MateriaEnum materiaPotencial = materiaOriginal;
        String turmaPotencial = turmaOriginal;

        // Edição da Matéria
        System.out.println("\nMatérias disponíveis para alteração:");
        MateriaEnum[] materias = MateriaEnum.values();
        for (int i = 0; i < materias.length - 1; i++) {
            System.out.println("[" + (i + 1) + "] - " + materias[i].getCodigo() + " - " + materias[i].getNome());
        }
        System.out.print("Selecione a nova matéria (ou deixe em branco para manter a atual '" + materiaOriginal.getNome() + "'): ");
        try {
            String inputMateria = scanner.nextLine();
            if (!inputMateria.isEmpty()) {
                int opcaoMateria = Integer.parseInt(inputMateria);
                if (opcaoMateria >= 1 && opcaoMateria <= materias.length - 1) {
                    materiaPotencial = materias[opcaoMateria - 1];
                } else {
                    exibirMensagem("Opção de matéria inválida. Mantendo matéria atual.");
                }
            }
        } catch (NumberFormatException | InputMismatchException e) {
            exibirMensagem("Entrada inválida para matéria. Mantendo matéria atual.");
        }

        // Edição da Turma
        System.out.println("\nTurmas disponíveis para alteração:");
        String[] turmasDisponiveis = {"Turma 1", "Turma 2", "Turma 3"};
        for (int i = 0; i < turmasDisponiveis.length; i++) {
            System.out.println("[" + (i + 1) + "] - " + turmasDisponiveis[i]);
        }
        System.out.print("Selecione a nova turma (ou deixe em branco para manter a atual '" + turmaOriginal + "'): ");
        try {
            String inputTurma = scanner.nextLine();
            if (!inputTurma.isEmpty()) {
                int opcaoTurma = Integer.parseInt(inputTurma);
                if (opcaoTurma >= 1 && opcaoTurma <= turmasDisponiveis.length) {
                    turmaPotencial = turmasDisponiveis[opcaoTurma - 1];
                } else {
                    exibirMensagem("Opção de turma inválida. Mantendo turma atual.");
                }
            }
        } catch (NumberFormatException e) {
            exibirMensagem("Entrada inválida para turma. Mantendo turma atual.");
        }

        // Validação de Duplicidade
        if (controller.verificarDuplicidadeNaEdicao(plano, materiaPotencial.getCodigo(), turmaPotencial)) {
            exibirErro("A combinação da matéria '" + materiaPotencial.getNome() + "' com a '" + turmaPotencial + "' já existe em outro plano de ensino!");
            exibirMensagem("As alterações na matéria e na turma não foram salvas.");
        } else {
            plano.setCodigoMateria(materiaPotencial.getCodigo());
            plano.setNomeMateria(materiaPotencial.getNome());
            plano.setTurma(turmaPotencial);
            System.out.println("==> Matéria/Turma atualizada para: " + plano.getNomeMateria() + " / " + plano.getTurma() + "\n");
        }
        
        System.out.println("1. IDENTIFICAÇÃO - CARGA HORÁRIA");
        System.out.print("Carga Horária Teórica atual (" + plano.getCargaHorariaTeorica() + "): ");
        String inputCargaTeorica = scanner.nextLine();
        if (!inputCargaTeorica.isEmpty()) {
            try { plano.setCargaHorariaTeorica(Integer.parseInt(inputCargaTeorica)); } 
            catch (NumberFormatException e) { exibirErro("Carga horária teórica inválida. Mantendo valor atual."); }
        }
        
        System.out.print("Carga Horária Prática atual (" + plano.getCargaHorariaPratica() + "): ");
        String inputCargaPratica = scanner.nextLine();
        if (!inputCargaPratica.isEmpty()) {
            try { plano.setCargaHorariaPratica(Integer.parseInt(inputCargaPratica)); } 
            catch (NumberFormatException e) { exibirErro("Carga horária prática inválida. Mantendo valor atual."); }
        }
        System.out.println("Carga Horária Total: " + plano.getCargaHorariaTotal() + " horas\n");
        
        System.out.println("2. JUSTIFICATIVA (Atual: " + (plano.getJustificativa().length() > 50 ? plano.getJustificativa().substring(0, 50) + "..." : plano.getJustificativa()) + ")");
        System.out.println("Digite a nova justificativa:");
        String justificativa = lerTextoMultilinhas();
        if (!justificativa.isEmpty()) plano.setJustificativa(justificativa);
        
        System.out.println("3. EMENTA (Atual: " + (plano.getEmenta().length() > 50 ? plano.getEmenta().substring(0, 50) + "..." : plano.getEmenta()) + ")");
        System.out.println("Digite a nova ementa:");
        String ementa = lerTextoMultilinhas();
        if (!ementa.isEmpty()) plano.setEmenta(ementa);
        
        System.out.println("4. OBJETIVOS (Atual: " + (plano.getObjetivos().length() > 50 ? plano.getObjetivos().substring(0, 50) + "..." : plano.getObjetivos()) + ")");
        System.out.println("Digite os novos objetivos:");
        String objetivos = lerTextoMultilinhas();
        if (!objetivos.isEmpty()) plano.setObjetivos(objetivos);
        
        System.out.println("5. CALENDÁRIO DE ATIVIDADES");
        System.out.print("Data inicial atual (" + plano.getDataInicialFormatada() + "): ");
        String dataInicial = scanner.nextLine();
        if (!dataInicial.isEmpty()) {
            try { plano.setDataInicial(dataInicial); } 
            catch (IllegalArgumentException e) { exibirErro("Data inicial inválida. Mantendo valor atual."); }
        }
        
        System.out.print("Data final atual (" + plano.getDataFinalFormatada() + "): ");
        String dataFinal = scanner.nextLine();
        if (!dataFinal.isEmpty()) {
            try { plano.setDataFinal(dataFinal); } 
            catch (IllegalArgumentException e) { exibirErro("Data final inválida. Mantendo valor atual."); }
        }
        
        System.out.println("6. METODOLOGIA DE ENSINO (Atual: " + (plano.getMetodologia().length() > 50 ? plano.getMetodologia().substring(0, 50) + "..." : plano.getMetodologia()) + ")");
        System.out.println("Digite a nova metodologia de ensino:");
        String metodologia = lerTextoMultilinhas();
        if (!metodologia.isEmpty()) plano.setMetodologia(metodologia);
        
        System.out.println("7. ATIVIDADES DISCENTES (Atual: " + (plano.getAtividadesDiscentes().length() > 50 ? plano.getAtividadesDiscentes().substring(0, 50) + "..." : plano.getAtividadesDiscentes()) + ")");
        System.out.println("Digite as novas atividades discentes:");
        String atividadesDiscentes = lerTextoMultilinhas();
        if (!atividadesDiscentes.isEmpty()) plano.setAtividadesDiscentes(atividadesDiscentes);
        
        System.out.println("8. SISTEMA DE AVALIAÇÃO (Atual: " + (plano.getAvaliacoes().length() > 50 ? plano.getAvaliacoes().substring(0, 50) + "..." : plano.getAvaliacoes()) + ")");
        System.out.println("Digite o novo sistema de avaliação:");
        String avaliacoes = lerTextoMultilinhas();
        if (!avaliacoes.isEmpty()) plano.setAvaliacoes(avaliacoes);
        
        System.out.println("9. BIBLIOGRAFIA BÁSICA E COMPLEMENTAR (Atual: " + (plano.getBibliografia().length() > 50 ? plano.getBibliografia().substring(0, 50) + "..." : plano.getBibliografia()) + ")");
        System.out.println("Digite a nova bibliografia:");
        String bibliografia = lerTextoMultilinhas();
        if (!bibliografia.isEmpty()) plano.setBibliografia(bibliografia);
        
        System.out.println("10. PARECER (Atual: " + (plano.getParecer().length() > 50 ? plano.getParecer().substring(0, 50) + "..." : plano.getParecer()) + ")");
        System.out.println("Digite o novo parecer:");
        String parecer = lerTextoMultilinhas();
        if (!parecer.isEmpty()) plano.setParecer(parecer);
    }

    private String lerTextoMultilinhas() {
        System.out.println("(Digite o texto. Para finalizar, digite 'FIM' em uma linha separada)");
        StringBuilder texto = new StringBuilder();
        String linha;
        
        while (!(linha = scanner.nextLine()).equalsIgnoreCase("FIM")) {
            if (texto.length() > 0) {
                texto.append("\n");
            }
            texto.append(linha);
        }
        
        return texto.toString();
    }

    public void exibirMensagem(String mensagem) {
        System.out.println("=====================");
        System.out.println(mensagem);
        System.out.println("=====================\n");
    }

    public void exibirErro(String erro) {
        System.err.println("ERRO: " + erro + "\n");
    }

    @Override
    public void update() {
    }
}
