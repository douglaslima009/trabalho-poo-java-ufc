package Model;
import java.util.ArrayList;
import java.util.List;
import View.Observer;

public class Model{

    List<Professor> professores =  new ArrayList<>();
    private List<PlanoDeEnsino> planosDeEnsino = new ArrayList<>();
    private ArrayList<Observer> observers = new ArrayList<>();
    private Professor autenticUser = null;
    private static Model instancModel;

    private Model(){
        super();
    }

    public static Model getInstanciaModel(){
        if(instancModel == null){
            instancModel = new Model();
        }
        return instancModel;
    }

    public void notificar(){
        for(Observer observe: observers){
            observe.update();
        }
    }

    public void registerObserver(Observer observer){
        if(observer != null){
            observers.add(observer);
        }
    }

    public void removerObserver(Observer observer){
        if(observer != null){
            observers.remove(observer);
        }
    }

    public Professor buscarProfessor(int matricula){
        for(Professor professor: professores){
            if(professor.getMatricula() == matricula){
                return professor;
            }
        }
        return null;
    }

    public Professor validarUserLogin(int matricula, String senha){
        Professor buscar = buscarProfessor(matricula);
        if(buscar != null){
            if(buscar.getMatricula() == matricula && senha.equals(buscar.getSenha())){
                autenticUser = buscar;
            }
        }
        notificar();
        return autenticUser;
    }


    public Professor getAutenticUser(){
        return autenticUser;
    }

    public Professor deslogarUsuario(){
        autenticUser = null;
        notificar();
        return autenticUser;
    }


    public void adicionarProfessor(Professor professor){
        if(professor != null){
            Professor buscar = buscarProfessor(professor.getMatricula());
            if(buscar == null){
                professores.add(professor);
                notificar();
            }
        }
    }

    public void adicionarPlanoDeEnsino(PlanoDeEnsino plano) {
        if (plano != null) {
            this.planosDeEnsino.add(plano);
        }
    }

    public boolean planoDeEnsinoExiste(String codigoMateria, String turma) {
        for (PlanoDeEnsino plano : this.planosDeEnsino) {
            if (plano.getCodigoMateria().equals(codigoMateria) && plano.getTurma().equalsIgnoreCase(turma)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean planoExisteParaOutro(PlanoDeEnsino planoSendoEditado, String novoCodigoMateria, String novaTurma) {
        for (PlanoDeEnsino plano : this.planosDeEnsino) {
            if (plano != planoSendoEditado) {
                if (plano.getCodigoMateria().equals(novoCodigoMateria) && plano.getTurma().equalsIgnoreCase(novaTurma)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getQuantiUser(){
        return professores.size();
    }

    public String listarProfesores(){
        StringBuilder listProfessor = new StringBuilder();
        for(Professor professor: professores){
            listProfessor.append("==================================\n")
            .append("Nome: ").append(professor.getNomeProfessor()).append("\n")
            .append("Matricula: ").append(professor.getMatricula()).append("\n")
            .append("Planos Cadastrados: ").append(professor.getPlanosDeEnsino().size()).append("\n");
        }
        return listProfessor.toString();
    }

    public String listarPlanoEnsino(int matricula) {
        StringBuilder planosString = new StringBuilder();
        Professor professor = buscarProfessor(matricula);
        
        if (professor == null) {
            return "Professor com a matrícula " + matricula + " não encontrado.\n";
        }

        planosString.append("==================================\n")
                    .append("Professor(a): ").append(professor.getNomeProfessor()).append("\n")
                    .append("Matrícula: ").append(professor.getMatricula()).append("\n")
                    .append("==================================\n\n");

        List<PlanoDeEnsino> planosDoProfessor = professor.getPlanosDeEnsino();

        if (planosDoProfessor.isEmpty()) {
            planosString.append("Nenhum plano de ensino cadastrado para este(a) professor(a).\n");
        } else {
            planosString.append("PLANOS DE ENSINO CADASTRADOS (").append(planosDoProfessor.size()).append("):\n\n");
            int count = 1;
            for (PlanoDeEnsino plano : planosDoProfessor) {
                planosString.append("--- Plano").append(count++).append(" ---\n");
                planosString.append("Disciplina: ").append(plano.getNomeMateria()).append(" (").append(plano.getCodigoMateria()).append(")\n");
                planosString.append("Turma: ").append(plano.getTurma()).append("\n");
                planosString.append("Período: ").append(plano.getDataInicialFormatada()).append(" a ").append(plano.getDataFinalFormatada()).append("\n");
                planosString.append("Carga Horária Total: ").append(plano.getCargaHorariaTotal()).append("h\n");
                planosString.append("Ementa: ").append(plano.getEmenta()).append("\n\n");
            }
        }
        return planosString.toString();
    }

    public void deletarProfessor(int matricula, boolean confirm){
        Professor buscar = buscarProfessor(matricula);
        if(buscar != null && buscar.getMatricula() == matricula && confirm){
            this.planosDeEnsino.removeAll(buscar.getPlanosDeEnsino());
            professores.remove(buscar);
            notificar();
        }
    }
}
