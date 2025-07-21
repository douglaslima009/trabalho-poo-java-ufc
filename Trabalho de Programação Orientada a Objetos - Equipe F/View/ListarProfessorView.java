package View;
import Model.*;
import Controller.*;

public class ListarProfessorView implements Observer{

    private Model model;
    private ListarProfessorController listarProfessorController;

    public void iniciarListarProfessorView(Model model){
        if(model != null){
            this.model = model;
            listarProfessorController = new ListarProfessorController();
            listarProfessorController.iniciarListaController(model, this);
            model.registerObserver(this);
            mostarListarProfessore();
        }
    }

    public void mostarListarProfessore(){
        String listFormada = listarProfessorController.solicitarListarProfesores();
        System.out.println("========= Lista de Professores =========");
        System.out.println(listFormada);
    }

    public String mensagem(String mensagem){
        return mensagem;
    }

    public void update(){
    }
}