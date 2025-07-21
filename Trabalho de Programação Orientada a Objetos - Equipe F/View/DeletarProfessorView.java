package View;
import Model.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import Controller.DeletarProfessorController;

public class DeletarProfessorView implements Observer{

    private Model model;
    private DeletarProfessorController deletarProfessorController;
    private int matriculaDeletar;
    private boolean validar;
    
    public void iniciarDeleteView(Model model){
        if(model != null){
            this.model = model;
            deletarProfessorController = new DeletarProfessorController();
            deletarProfessorController.iniciarControllerDelete(model, this);
            model.registerObserver(this);
            deletarProfessor();
        }

    }

    public void deletarProfessor(){
        Scanner sc = new Scanner(System.in);
        System.out.println("========= Deletar Professor =========");
        try {
            System.out.println("Digite a matricula do professor: ");
            matriculaDeletar = sc.nextInt();
            sc.nextLine();
            String infoProfessor = deletarProfessorController.buscarProfessor(matriculaDeletar);
            if(infoProfessor != null && !infoProfessor.isEmpty()){    
                System.out.println(deletarProfessorController.buscarProfessor(matriculaDeletar));
                try {
                    System.out.println("Deseja deletar esse professor digite 1 ou 2:\n[1] - Sim\n[2] - nao");
                    int confirm = sc.nextInt();
                    sc.nextLine();
                    if(confirm == 1){
                        validar = true;
                        deletarProfessorController.deletarProfessor("ok");
                    }else{
                        System.out.println("Delete Cancelado!!");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Erro Apenas 1 ou 2 para confirmar o delete!!");
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Erro apenas numeros na matricula!!");
        }
    }

    public boolean getValidar(){
        return this.validar;
    }

    public int getMatricula(){
        return this.matriculaDeletar;
    }

    public void mostarMensagem(String mensagem){
        System.out.println("=====================\n" + mensagem +"\n=====================\n");
    }

    public void update(){}
}
