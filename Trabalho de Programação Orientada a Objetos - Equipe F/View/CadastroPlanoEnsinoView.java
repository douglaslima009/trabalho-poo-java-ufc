package View;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import Model.*;
import Controller.CadastroPlanoEnsinoController;

public class CadastroPlanoEnsinoView implements Observer {
    private Model model;
    private CadastroPlanoEnsinoController controller;
    private Scanner scanner;

    public CadastroPlanoEnsinoView() {
        this.scanner = new Scanner(System.in);
    }

    public void iniciarCadastroPlanoEnsino(Model model) {
        if (model != null) {
            this.model = model;
            this.controller = new CadastroPlanoEnsinoController();
            this.controller.iniciarCadastroPlanoEnsino(model, this);
            model.registerObserver(this);
            exibirFormularioCadastro();
        }
    }

    public void exibirFormularioCadastro() {
        System.out.println("================ CADASTRO DE PLANO DE ENSINO ================\n");
        
        // Exibir informações pré-preenchidas
        Professor professorLogado = model.getAutenticUser();
        if (professorLogado != null) {
            System.out.println("Professor(a): " + professorLogado.getNomeProfessor());
            
            // Exibir matérias disponíveis para seleção
            System.out.println("\nMatérias disponíveis:");
            MateriaEnum[] materias = MateriaEnum.values();
            // Excluir a última opção (SEMCADASTRO) que não é uma matéria real
            for (int i = 0; i < materias.length - 1; i++) {
                System.out.println("[" + (i + 1) + "] - " + materias[i].getCodigo() + " - " + materias[i].getNome());
            }
            
            int opcaoMateria;
            while (true) {
                try {
                    System.out.print("Selecione a matéria (número): ");
                    opcaoMateria = scanner.nextInt();
                    scanner.nextLine(); // Consumir quebra de linha
                    if (opcaoMateria >= 1 && opcaoMateria <= materias.length - 1) {
                        break;
                    } else {
                        System.out.println("Opção inválida. Por favor, digite um número entre 1 e " + (materias.length - 1) + ".");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Por favor, digite um número.");
                    scanner.nextLine(); // Consumir a entrada inválida
                }
            }
            
            MateriaEnum materiaSelecionada = materias[opcaoMateria - 1];
            System.out.println("Matéria selecionada: " + materiaSelecionada.getCodigo() + " - " + materiaSelecionada.getNome());
            
            // --- INÍCIO DA ALTERAÇÃO: Seleção de Turma ---
            // Perguntar pela turma através de um menu de seleção
            String[] turmasDisponiveis = {"Turma 1", "Turma 2", "Turma 3"};
            System.out.println("\nTurmas disponíveis:");
            for (int i = 0; i < turmasDisponiveis.length; i++) {
                System.out.println("[" + (i + 1) + "] - " + turmasDisponiveis[i]);
            }

            int opcaoTurma;
            while (true) {
                try {
                    System.out.print("Selecione a turma (número): ");
                    opcaoTurma = scanner.nextInt();
                    scanner.nextLine(); // Consumir quebra de linha
                    if (opcaoTurma >= 1 && opcaoTurma <= turmasDisponiveis.length) {
                        break;
                    } else {
                        System.out.println("Opção inválida. Por favor, digite um número entre 1 e " + turmasDisponiveis.length + ".");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Por favor, digite um número.");
                    scanner.nextLine(); // Consumir a entrada inválida
                }
            }
            String turmaSelecionada = turmasDisponiveis[opcaoTurma - 1];
            System.out.println("Turma selecionada: " + turmaSelecionada);

            // ANTES DE PROSSEGUIR: Verificar se já existe um plano para esta matéria e turma.
            // Esta verificação é feita pelo Controller.
            if (controller.planoJaExiste(materiaSelecionada, turmaSelecionada)) {
                System.out.println("\nERRO: Já existe um plano de ensino cadastrado para a matéria '" + materiaSelecionada.getNome() + "' na '" + turmaSelecionada + "'.");
                System.out.println("Cadastro cancelado.");
                return; // Retorna para o menu anterior, cancelando a operação.
            }
            // --- FIM DA ALTERAÇÃO ---

            // Criar novo plano de ensino
            PlanoDeEnsino novoPlano = new PlanoDeEnsino();
            
            try {
                // Preencher dados automáticos
                novoPlano.setNomeProfessor(professorLogado.getNomeProfessor());
                novoPlano.setCodigoMateria(materiaSelecionada.getCodigo());
                novoPlano.setNomeMateria(materiaSelecionada.getNome());
                // ADICIONADO: Salvar a turma no plano de ensino.
                novoPlano.setTurma(turmaSelecionada);
                
                // Coletar dados dos 10 tópicos
                coletarDadosPlanoEnsino(novoPlano);
                
                // Salvar plano de ensino
                controller.salvarPlanoEnsino(novoPlano);
                
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
                System.out.println("Cadastro cancelado.");
            }
        }
    }

    private void coletarDadosPlanoEnsino(PlanoDeEnsino plano) {
        System.out.println("\n================ DADOS DO PLANO DE ENSINO ================\n");
        
        // 1. Identificação - Carga Horária
        System.out.println("1. IDENTIFICAÇÃO - CARGA HORÁRIA");
        int cargaTeorica;
        while (true) {
            try {
                System.out.print("Carga Horária Teórica: ");
                cargaTeorica = scanner.nextInt();
                if (cargaTeorica >= 0) {
                    plano.setCargaHorariaTeorica(cargaTeorica);
                    break;
                } else {
                    System.out.println("Carga horária teórica deve ser maior ou igual a zero.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
                scanner.nextLine(); // Consumir a entrada inválida
            }
        }
        
        int cargaPratica;
        while (true) {
            try {
                System.out.print("Carga Horária Prática: ");
                cargaPratica = scanner.nextInt();
                if (cargaPratica >= 0) {
                    plano.setCargaHorariaPratica(cargaPratica);
                    break;
                } else {
                    System.out.println("Carga horária prática deve ser maior ou igual a zero.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
                scanner.nextLine(); // Consumir a entrada inválida
            }
        }
        scanner.nextLine(); // Consumir quebra de linha
        
        System.out.println("Carga Horária Total: " + plano.getCargaHorariaTotal() + " horas\n");

        // Horário de Início e Término da Aula
        LocalTime horaInicio = null;
        LocalTime horaTermino = null;
        boolean horarioValido = false;
        while (!horarioValido) {
            try {
                System.out.print("Digite o horário de início da aula (HH:mm): ");
                horaInicio = LocalTime.parse(scanner.nextLine());

                System.out.print("Digite o horário de término da aula (HH:mm): ");
                horaTermino = LocalTime.parse(scanner.nextLine());

                // Validação de horário
                if (horaInicio.isAfter(horaTermino)) {
                    System.out.println("Erro: Horário de início não pode ser depois do horário de término.");
                } else if (java.time.Duration.between(horaInicio, horaTermino).toHours() > 4) {
                    System.out.println("Erro: A duração da aula não pode exceder 4 horas.");
                } else {
                    // -- CORREÇÃO INÍCIO: Lógica de validação de horário --
                    LocalTime inicioManha = LocalTime.of(8, 0);
                    LocalTime fimManha = LocalTime.of(12, 0);
                    LocalTime inicioTarde = LocalTime.of(13, 30);
                    LocalTime fimTarde = LocalTime.of(17, 30);

                    boolean dentroDaManha = !horaInicio.isBefore(inicioManha) && !horaTermino.isAfter(fimManha);
                    boolean dentroDaTarde = !horaInicio.isBefore(inicioTarde) && !horaTermino.isAfter(fimTarde);

                    if (dentroDaManha || dentroDaTarde) {
                        plano.setHoraInicio(horaInicio);
                        plano.setHoraTermino(horaTermino);
                        horarioValido = true;
                    } else {
                        System.out.println("Erro: O horário da aula deve estar contido inteiramente no período de 08:00-12:00 ou 13:30-17:30.");
                    }
                    // -- CORREÇÃO FIM --
                }
            } catch (DateTimeParseException e) {
                System.out.println("Formato de horário inválido. Use HH:mm. Tente novamente.");
            }
        }
        
        // 2. Justificativa
        System.out.println("2. JUSTIFICATIVA");
        System.out.println("Digite a justificativa da disciplina:");
        String justificativa = lerTextoMultilinhas();
        plano.setJustificativa(justificativa);
        
        // 3. Ementa
        System.out.println("3. EMENTA");
        System.out.println("Digite a ementa da disciplina:");
        String ementa = lerTextoMultilinhas();
        plano.setEmenta(ementa);
        
        // 4. Objetivos
        System.out.println("4. OBJETIVOS");
        System.out.println("Digite os objetivos da disciplina:");
        String objetivos = lerTextoMultilinhas();
        plano.setObjetivos(objetivos);
        
        // -- CORREÇÃO INÍCIO: Lógica de validação de data --
        // 5. Calendário de Atividades
        System.out.println("5. CALENDÁRIO DE ATIVIDADES");
        while (true) { // Loop para garantir que o período total seja válido
            try {
                System.out.print("Data inicial (dd/MM/yyyy): ");
                String dataInicialStr = scanner.nextLine();
                plano.setDataInicial(dataInicialStr); // Valida o formato e se a data não é no passado

                System.out.print("Data final (dd/MM/yyyy): ");
                String dataFinalStr = scanner.nextLine();
                plano.setDataFinal(dataFinalStr); // Valida o formato e se a data final é após a inicial

                // Validação adicional para o período de 6 meses
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dInicial = LocalDate.parse(dataInicialStr, formatter);
                LocalDate dFinal = LocalDate.parse(dataFinalStr, formatter);

                if (dFinal.isAfter(dInicial.plusMonths(6))) {
                    // Lança uma exceção para ser tratada abaixo, mantendo o padrão do código
                    throw new IllegalArgumentException("O período entre a data inicial e final não pode exceder 6 meses.");
                }

                break; // Se tudo estiver correto, sai do loop

            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage() + " Tente novamente.");
            }
        }
        // -- CORREÇÃO FIM --
        
        // 6. Metodologia de Ensino
        System.out.println("6. METODOLOGIA DE ENSINO");
        System.out.println("Digite a metodologia de ensino:");
        String metodologia = lerTextoMultilinhas();
        plano.setMetodologia(metodologia);
        
        // 7. Atividades Discentes
        System.out.println("7. ATIVIDADES DISCENTES");
        System.out.println("Digite as atividades discentes:");
        String atividadesDiscentes = lerTextoMultilinhas();
        plano.setAtividadesDiscentes(atividadesDiscentes);
        
        // 8. Sistema de Avaliação
        System.out.println("8. SISTEMA DE AVALIAÇÃO");
        System.out.println("Digite o sistema de avaliação:");
        String avaliacoes = lerTextoMultilinhas();
        plano.setAvaliacoes(avaliacoes);
        
        // 9. Bibliografia Básica e Complementar
        System.out.println("9. BIBLIOGRAFIA BÁSICA E COMPLEMENTAR");
        System.out.println("Digite a bibliografia:");
        String bibliografia = lerTextoMultilinhas();
        plano.setBibliografia(bibliografia);
        
        // 10. Parecer
        System.out.println("10. PARECER");
        System.out.println("Digite o parecer:");
        String parecer = lerTextoMultilinhas();
        plano.setParecer(parecer);
    }

    private String lerTextoMultilinhas() {
        System.out.println("(Digite o texto. Para finalizar, digite 'FIM' em uma linha separada)");
        StringBuilder texto = new StringBuilder();
        String linha;
        
        while (!(linha = scanner.nextLine()).equalsIgnoreCase("FIM")) { // Use equalsIgnoreCase para mais flexibilidade
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
        System.out.println("ERRO: " + erro);
    }

    @Override
    public void update() {
        // Implementação do Observer se necessário
    }
}
