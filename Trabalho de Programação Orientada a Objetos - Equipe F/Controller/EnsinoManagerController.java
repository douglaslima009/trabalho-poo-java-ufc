package Controller;
import View.CadastroView;
import View.CadastroPlanoEnsinoView;
import View.DeletarProfessorView;
import View.EnsinoManagerView;
import View.ListarProfessorView;
import View.LoginView;
import View.Observer;
import View.PlanoEnsinoProfessorView;
import View.UpdateProfessorView;
import View.EditarPlanoEnsinoView;
import Model.*;

/**
 * Controller principal que funciona como um "roteador" da aplicação.
 * Ele gerencia o menu principal e navega para as diferentes telas do sistema.
 */
public class EnsinoManagerController implements Observer {

    private Model model;
    private EnsinoManagerView ensinoManagerView;

    /**
     * Inicializa o controller principal, conectando-o ao Model e à View do menu.
     */
    public void iniciarSystem(Model model, EnsinoManagerView ensinoManagerView){
        if(model != null && ensinoManagerView != null){
            this.model = model;
            this.ensinoManagerView = ensinoManagerView;
            model.registerObserver(this);
        }
    }

    /**
     * Recebe a escolha do usuário no menu principal (como um número) e direciona
     * para a tela ou ação correspondente.
     */
    public void eventEnsinoManagerView(int eventEscolha){

        switch (eventEscolha) {
            // A lógica de muitos botões depende se há um usuário logado ou não.
            case 1:
                // Opção 1: Se não há usuário logado, abre a tela de login. Se há, faz o logout.
                if(model.getAutenticUser() == null){
                    LoginView view2 = new LoginView();
                    view2.iniciarLogin(model);
                }else{
                    model.deslogarUsuario();
                }
                break;
            case 2:
                // Opção 2: O comportamento do "cadastro" muda com base no login.
                if(model.getAutenticUser() == null){
                    // Sem login: cadastra um novo professor.
                    CadastroView cadastroView = new CadastroView();
                    cadastroView.iniciarCadastro(model);
                }else{
                    // Com login: cadastra um novo plano de ensino para o professor logado.
                    CadastroPlanoEnsinoView cadastroPlanoView = new CadastroPlanoEnsinoView();
                    cadastroPlanoView.iniciarCadastroPlanoEnsino(model);
                }
                break;
            case 3:
                 // Opção 3: Se logado, lista os planos. Se não, funciona como uma opção de saída.
                if(model.getAutenticUser() == null){
                    ensinoManagerView.finalizarSystema();
                }else{
                    PlanoEnsinoProfessorView planoView = new PlanoEnsinoProfessorView();
                    planoView.iniciarPlanoEnsinoView(model);
                }
                break;
            case 4:
                // Opção 4: Ação que exige que o professor esteja logado para atualizar seus dados.
                UpdateProfessorView updateView = new UpdateProfessorView();
                if(model.getAutenticUser() == null){
                    updateView.mostrarMensagem("O(A) professor(a) deve estar logado!");
                }else{
                    updateView.iniciarUpdateView(model);
                }
                break;
            case 5:
                // Opção 5: Ação que exige login para editar um plano de ensino.
                if(model.getAutenticUser() == null){
                    ensinoManagerView.mensagem("O(A) professor(a) deve estar logado para editar o plano de ensino!");
                }else{
                    EditarPlanoEnsinoView editarPlanoView = new EditarPlanoEnsinoView();
                    editarPlanoView.iniciarEdicaoPlanoEnsino(model);
                }
                break;
            case 6:
                // Opção 6: Encerra o sistema incondicionalmente.
                ensinoManagerView.finalizarSystema();
                break;
            default:
                break;
        }

    }

    /**
     * Método obrigatório da interface Observer. Vazio pois este controller
     * reage a eventos diretos do menu, não a notificações gerais do Model.
     */
    public void update(){}
}