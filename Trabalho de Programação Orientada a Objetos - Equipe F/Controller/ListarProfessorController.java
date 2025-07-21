package Controller;
import View.*;
import Model.*;

/**
 * Controller responsável por orquestrar a exibição da lista de professores.
 */
public class ListarProfessorController implements Observer{

    private Model model;
    private ListarProfessorView listarProfessorView;

    /**
     * Inicializa o controller, conectando-o com o Model e a View.
     */
    public void iniciarListaController(Model model,ListarProfessorView listarProfessorView){
            if(model != null && listarProfessorView != null){
                this.listarProfessorView = listarProfessorView;
                this.model = model;
                model.registerObserver(this); // Começa a "ouvir" o model.
            }
    }

    /**
     * Pede ao Model a lista já formatada de todos os professores e a retorna.
     * A View usará essa String para exibir os dados ao usuário.
     * @return Uma String contendo a lista de professores.
     */
    public String solicitarListarProfesores(){
        // A responsabilidade de buscar e formatar a lista é toda do Model.
        String listaFormada =  model.listarProfesores(); 
        return listaFormada;
    }

    /**
     * Método da interface Observer. Fica vazio pois a lista é carregada sob demanda
     * pela View, e não através de uma notificação automática do Model.
     */
    public void update() {

    }
}