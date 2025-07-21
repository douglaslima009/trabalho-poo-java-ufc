package Model;
import java.util.ArrayList;
import java.util.List;

public class Professor {

    private String nomeProfessor;
    private String emailProfessor;
    private int matricula;
    private boolean statusProfessor;
    private String senhaUser;
    private Materia materia;
    // Lista de objetos.
    private List<PlanoDeEnsino> planosDeEnsino;

    public Professor() {
        //Inicializando a lista para evitar erros.
        this.planosDeEnsino = new ArrayList<>();
    }

    public Professor(String nomeProfessor, int matricula, boolean statusProfessor, String senhaUser, Materia Materia) {
        setNomeProfessor(nomeProfessor);
        setMatricula(matricula);
        setStatusProfessor(statusProfessor);
        setSenha(senhaUser);
        setMateria(Materia);
        //Inicializando a lista para evitar erros.
        this.planosDeEnsino = new ArrayList<>();
    }
    
    public Professor(String nomeProfessor, int matricula, String senhaUser) {
        setNomeProfessor(nomeProfessor);
        setMatricula(matricula);
        setSenha(senhaUser);
        setStatusProfessor(false); // valor padrão
        setMateria(null); // valor padrão
        //Inicializando a lista para evitar erros.
        this.planosDeEnsino = new ArrayList<>();
    }

    // Getters e Setters para os outros atributos
    public String getNomeProfessor() {
        return this.nomeProfessor;
    }

    public void setNomeProfessor(String nomeProfessor) {
        if (nomeProfessor != null && !nomeProfessor.isEmpty()) {
            this.nomeProfessor = nomeProfessor;
        }
    }

    public String getEmailProfessor() {
        return this.emailProfessor;
    }

    public void setEmailProfessor(String emailProfessor) {
        if (emailProfessor != null && !emailProfessor.isEmpty()) {
            this.emailProfessor = emailProfessor;
        }
    }

    public int getMatricula() {
        return this.matricula;
    }

    public void setMatricula(int matricula) {
        if (matricula > 0) {
            this.matricula = matricula;
        }
    }

    public String getSenha() {
        return this.senhaUser;
    }

    public void setSenha(String senha) {
        if (senha != null && !senha.isEmpty()) {
            this.senhaUser = senha;
        }
    }

    public boolean getStatusProfessor() {
        return this.statusProfessor;
    }

    public void setStatusProfessor(boolean statusProfessor) {
        this.statusProfessor = statusProfessor;
    }

    public Materia getMateria() {
        return this.materia;
    }

    public void setMateria(Materia materia) {
        if (materia != null) {
            this.materia = materia;
        }
    }
    
    /**
     * Retorna a lista de todos os planos de ensino associados a este professor.
     * @return Uma List<PlanoDeEnsino>.
     */
    public List<PlanoDeEnsino> getPlanosDeEnsino() {
        return this.planosDeEnsino;
    }

    /**
     * Adiciona um novo plano de ensino à lista do professor.
     * @param plano O plano de ensino a ser adicionado.
     */
    public void adicionarPlanoDeEnsino(PlanoDeEnsino plano) {
        if (plano != null) {
            this.planosDeEnsino.add(plano);
        }
    }

}