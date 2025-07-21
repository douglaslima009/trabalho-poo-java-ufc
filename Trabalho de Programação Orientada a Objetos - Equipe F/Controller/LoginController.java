package Controller;
import Model.*;
import View.LoginView;
import View.Observer;

/**
 * Controller que gerencia a lógica de autenticação do usuário (login).
 */
public class LoginController implements Observer{

    private Model model;
    private LoginView loginView;

    /**
     * Inicializa o controller, conectando-o com Model e a View de login.
     */
    public void iniciar(Model model, LoginView loginView){
        if(model != null && loginView != null){
            this.loginView = loginView;
            this.model = model;
            model.registerObserver(this); // Começa a "ouvir" o model.
        }
    }

    /**
     * Chamado quando o usuário tenta fazer login, geralmente ao clicar no botão "OK".
     */
    public void loginEvent(String confirm){
        if(confirm != null && confirm.equalsIgnoreCase("OK")){
            // Pede ao Model para validar as credenciais. Toda a lógica de verificação fica no Model.
            Professor verificador =  model.validarUserLogin(loginView.getMatriculaUser(), loginView.getSenha());
            
            // Se o Model retorna nulo, as credenciais estão incorretas.
            if(verificador == null){
                loginView.mostarMensagem("Erro: Usuário ou senha invalidos!");
            }else{
                loginView.mostarMensagem("Bem vindo(a) " + verificador.getNomeProfessor() + "!");
            }
            
            // Após a tentativa de login, o controller se "desregistra" pois sua tarefa foi concluída.
            model.removerObserver(this);
        }
    }

    /**
     * Método da interface Observer. Fica vazio pois este controller só age em eventos diretos da View.
     */
    public void update(){

    }
}