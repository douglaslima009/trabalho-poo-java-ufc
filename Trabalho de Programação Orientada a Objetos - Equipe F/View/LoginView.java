package View;
import Model.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import Controller.LoginController;

public class LoginView implements Observer{
    
    private Model model;
    private LoginController loginController;
    private int matriculaUser;
    private String senha;

    public void iniciarLogin(Model model){
        this.model = model;
        loginController = new LoginController();
        loginController.iniciar(model, this);
        model.registerObserver(loginController);
        loginUser();
    }

    public void loginUser(){
        Scanner sc = new Scanner(System.in);
        System.out.println("========== LOGIN ==========");


        try {
            System.out.println("Usuário (Matrícula): ");
            matriculaUser = sc.nextInt();
        } catch (InputMismatchException e) {
            mostarMensagem("Erro apenas numeros na matrícula: " );
            return;
        }

        sc.nextLine();
        System.out.println("Senha: ");
        senha = sc.nextLine();
        loginController.loginEvent("OK");
        model.removerObserver(this);
    }   

    public int getMatriculaUser(){
        return this.matriculaUser;
    }
    public String getSenha(){
        return this.senha;
    }

    public void update(){}

    public void mostarMensagem(String mensagem){
        System.out.println("=====================\n" + mensagem +"\n=====================\n");
    }

}
