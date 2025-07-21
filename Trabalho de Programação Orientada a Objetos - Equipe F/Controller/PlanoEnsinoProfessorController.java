package Controller;
import Model.*;
import View.PlanoEnsinoProfessorView;


public class PlanoEnsinoProfessorController {

    private Model model;
    private PlanoEnsinoProfessorView planoEnsinoProfessorView;

    public void iniciarPlanoEnsino(Model model, PlanoEnsinoProfessorView planoEnsinoProfessorView){
        if(model != null && planoEnsinoProfessorView != null){
            this.model = model;
            this.planoEnsinoProfessorView = planoEnsinoProfessorView;
        }
    }

    /**
        A View é quem busca os dados no Model e os exibe.
    */

    public void eventListarPlanoEnsino(){
        // A ÚNICA COISA QUE O CONTROLLER FAZ É CHAMAR O MÉTODO DE EXIBIÇÃO DA VIEW.
        planoEnsinoProfessorView.exibirPlanoEnsinoProfessorLogado();
    }
}