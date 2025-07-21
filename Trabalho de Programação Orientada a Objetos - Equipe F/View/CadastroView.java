package View;
import java.util.InputMismatchException;
import java.util.Scanner;
import Controller.CadastroController;
import Model.*;

public class CadastroView implements Observer{
    private Model model;
    private CadastroController cadastroController;
    private String nomeProfessor;
    private int matriculaProfessor;
    private String senhaProfessor;


    public void iniciarCadastro(Model model){
        this.model = model;
        this.cadastroController = new CadastroController();
        cadastroController.iniciarCadastroController(model, this);
        model.registerObserver(this);
        cadasterEvent();
    }

    public void cadasterEvent(){
        Scanner sc =  new Scanner(System.in);
        System.out.println("============ CADASTRO DE PROFESSOR(A) ============");
        
        // Loop para nome do professor
        while (true) {
            System.out.println("Digite o nome do(a) professor(a): ");
            nomeProfessor = sc.nextLine();
            if (nomeProfessor != null && !nomeProfessor.trim().isEmpty()) {
                break;
            } else {
                System.out.println("Nome do(a) professor(a) não pode ser vazio. Tente novamente.");
            }
        }

        // Loop para matrícula do professor (apenas números)
        while (true) {
            System.out.println("Digite a matrícula do(a) professor(a) (apenas números): ");
            try {
                matriculaProfessor = sc.nextInt();
                sc.nextLine(); // Consumir a nova linha
                break;
            } catch (InputMismatchException e) {
                System.out.println("Matrícula inválida. Por favor, digite apenas números.");
                sc.nextLine(); // Consumir a entrada inválida
            }
        }
        
        // Loop para senha do professor
        while (true) {
            System.out.println("Digite sua senha: ");
            senhaProfessor = sc.nextLine();
            if (senhaProfessor != null && !senhaProfessor.trim().isEmpty()) {
                break;
            } else {
                System.out.println("Senha não pode ser vazia. Tente novamente.");
            }
        }

        cadastroController.evetnCadastro("OK");

    }

    public String mensagem(String mensagem){
        return mensagem;
    }

    public String getNomeProfessor() {
        return nomeProfessor;
    }

    public int getMatriculaProfessor() {
        return matriculaProfessor;
    }

    public String getSenhaProfessor() {
        return senhaProfessor;
    }

    public void mostarMensagem(String mensagem){
        System.out.println("=====================\n" + mensagem +"\n=====================\n");
    }

    public void update(){}
}
