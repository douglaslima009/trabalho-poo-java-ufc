package View;
import java.util.*;
import Model.Model;
import Controller.*;

public class EnsinoManagerView implements Observer{
    private Model model;
    private EnsinoManagerController ensinoManagerController;
    private boolean systemFinal = false;
    private String userLogado;
    private int totalUser;

    public void iniciarEnsinoManagerView(Model model){
        if(model != null){
            this.model = model;
            ensinoManagerController = new EnsinoManagerController();
            ensinoManagerController.iniciarSystem(model,this);
            model.registerObserver(this);
            menuSistema();
        }
    }

    public void finalizarSystema(){
        this.systemFinal = true;
    }

    public void menuSistema(){
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println("================ MENU ================\n");

            if(model.getAutenticUser() != null){
                System.out.println("Quantidade de professores cadastrados: " + totalUser);
                System.out.println("Professor logado atualmente: " + model.getAutenticUser().getNomeProfessor() + "\n");
                System.out.println("[1] - Fazer Logout");
                System.out.println("[2] - Fazer cadastro de Plano de Ensino");
                System.out.println("[3] - Ver Planos de Ensino cadastrados pelo(a) Professor(a)");
                System.out.println("[4] - Editar dados do(a) Professor(a)");
                System.out.println("[5] - Editar Planos de Ensino");
                System.out.println("[6] - Encerrar sistema");
            }else{
                System.out.println("[1] - Fazer login");
                System.out.println("[2] - Cadastrar novo(a) professor(a)");
                System.out.println("[3] - Encerrar sistema");
            }

            int eventEscolha;
            while (true) {
                try {
                    System.out.print("Digite a Opção Selecionada: ");
                    eventEscolha = sc.nextInt();
                    sc.nextLine(); // Consumir a nova linha
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Opção inválida. Por favor, digite um número.");
                    sc.nextLine(); // Consumir a entrada inválida
                }
            }
            ensinoManagerController.eventEnsinoManagerView(eventEscolha);

        }while(!systemFinal);
    }

    public void mensagem(String mensagem){
        System.out.println("=====================\n" + mensagem +"\n=====================\n");
    }
    

    public void update(){
        if(model.getAutenticUser() != null){
            userLogado = model.getAutenticUser().getNomeProfessor();
        }
        totalUser = model.getQuantiUser();
    };
}