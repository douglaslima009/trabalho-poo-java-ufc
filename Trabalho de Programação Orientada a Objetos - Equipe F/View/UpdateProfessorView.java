package View;
import java.util.InputMismatchException;
import java.util.Scanner;
import Controller.UpdateProfessorController;
import Model.*;

public class UpdateProfessorView implements Observer{

    private Model model;
    private UpdateProfessorController updateProfessorController;

    public void iniciarUpdateView(Model model){
        if(model != null){
            this.model = model;
            updateProfessorController = new UpdateProfessorController();
            updateProfessorController.inciarControllerUpdate(model,this);
            updateProfessor(); // Chama o método para iniciar o processo de atualização
        }
    }

    public void updateProfessor(){
        System.out.println("========= EDITAR DADOS DO(A) PROFESSOR(A) =========");
        Scanner sc = new Scanner(System.in);
        Professor professorLogado = model.getAutenticUser();

        if (professorLogado == null) {
            mostrarMensagem("Nenhum(a) professor(a) logado(a). Faça login para editar seus dados.");
            return;
        }

        System.out.println("Professor(a) logado(a): " + professorLogado.getNomeProfessor());
        System.out.println("Matrícula: " + professorLogado.getMatricula());
        System.out.println("Senha atual: " + professorLogado.getSenha());

        System.out.println("\nDigite os novos dados (deixe em branco para manter o atual):");

        System.out.print("Novo nome: ");
        String novoNome = sc.nextLine();
        if (novoNome.isEmpty()) novoNome = professorLogado.getNomeProfessor();

        System.out.print("Nova senha: ");
        String novaSenha = sc.nextLine();
        if (novaSenha.isEmpty()) novaSenha = professorLogado.getSenha();

        updateProfessorController.atualizarProfessor(professorLogado.getMatricula(), novoNome, novaSenha);
    }

    public void mostrarMensagem(String mensagem){
        System.out.println("=====================\n" + mensagem +"\n=====================\n");
    }

    @Override
    public void update(){}
}


