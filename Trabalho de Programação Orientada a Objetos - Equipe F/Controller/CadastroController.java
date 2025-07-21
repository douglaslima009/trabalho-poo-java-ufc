package Controller;
import View.CadastroView;
import View.MateriaEnum;
import View.Observer;
import Model.*;

/**
 * Controller responsável pela lógica da tela de cadastro de professores.
 * Faz a ponte entre a interface (View) e os dados (Model).
 */
public class CadastroController implements Observer{

    // Referências para o Model (onde os dados ficam) e a View (a tela).
    private Model model;
    private CadastroView cadastroView;

    /**
     * Conecta este Controller com as instâncias do Model e da View e se registra
     * como um "observador" para ser notificado de mudanças.
     */
    public void iniciarCadastroController(Model model, CadastroView cadastroView){
        this.model = model;
        this.cadastroView = cadastroView;
        model.registerObserver(this); // Passa a ouvir o model.
    }

    /**
     * Método chamado pela View quando um evento, como um clique de botão, ocorre.
     */
    public void evetnCadastro(String eventoCadastro){
        // Se o evento for o clique no botão "OK", inicia o processo de cadastro.
        if(eventoCadastro.equals("OK")){
            int matricula = cadastroView.getMatriculaProfessor();
            
            // Regra de negócio: Verifica no Model se a matrícula já existe.
            if (model.buscarProfessor(matricula) != null) {
                cadastroView.mostarMensagem("Erro: Já existe um(a) professor(a) cadastrado(a) com esta matrícula.");
            } else {
                // Se a matrícula não existe, cria um novo professor e o adiciona no Model.
                Professor professor = new Professor(cadastroView.getNomeProfessor(), matricula, cadastroView.getSenhaProfessor());
                model.adicionarProfessor(professor);
                cadastroView.mostarMensagem("Professor(a) cadastrado(a) com sucesso!");
            }
            // Ao final da operação, para de "ouvir" o model, pois seu trabalho aqui terminou.
            model.removerObserver(this);
        }
    }

    /**
     * Método exigido pela interface Observer. Fica vazio porque este controller reage a eventos diretos da View, não a notificações gerais do Model.
     */
    @Override
    public void update(){}
}