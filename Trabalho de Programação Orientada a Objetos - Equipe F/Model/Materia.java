package Model;

/**
 * Representa a entidade Matéria (ou Disciplina) no sistema.
 * É uma classe do Model, focada em guardar os dados e garantir sua integridade.
 */
public class Materia{
    
    private int idMateria;
    private String nomeMateria;
    private int cargaHoraria;
    private PlanoDeEnsino planoDeEnsino; // Associação com o plano de ensino da matéria.

    // Construtores para diferentes cenários de criação do objeto.
    public Materia(){}

    public Materia(String nomeMateria, int cargaHoraria){
        setNomeMateria(nomeMateria);
        setCargaHoraria(cargaHoraria);
    }

    public Materia(int idMateria, String nomeMateria, int cargaHoraria, PlanoDeEnsino planoDeEnsino){
        setIdMateria(idMateria);
        setCargaHoraria(cargaHoraria);
        setNomeMateria(nomeMateria);
        setPlanodeEnsino(planoDeEnsino);
    }


    public int getIdMateria(){
        return this.idMateria;
    }

    public void setIdMateria(int idMateria){
        // Validação simples para garantir que o ID seja um número positivo.
        if(idMateria > 0){
            this.idMateria = idMateria;
        }
    }

    /**
     * Retorna o nome da matéria. Se nenhum nome foi atribuído,
     * retorna um valor padrão para evitar erros de valor nulo.
     * @return O nome da matéria ou "Matéria Não atribuída".
     */
    public String getNomeMateria(){
        return (nomeMateria == null || nomeMateria.isEmpty()) ? "Matéria Não atribuída" : nomeMateria;
    }
    
    public void setNomeMateria(String nomeMateria){
        if(nomeMateria != null && !nomeMateria.trim().isEmpty()){
            this.nomeMateria = nomeMateria;
        }
    }

    public int getCargahoraria(){
        return this.cargaHoraria;
    }

    /**
     * Define a carga horária, aplicando uma regra de negócio que limita o valor
     * para garantir consistência nos dados.
     */
    public void setCargaHoraria(int cargaHoraria){
        if(cargaHoraria > 0 && cargaHoraria <= 128){
            this.cargaHoraria = cargaHoraria;
        }
    }

    public PlanoDeEnsino getPlanoDeEnsino(){
        return this.planoDeEnsino;
    }

    public void setPlanodeEnsino(PlanoDeEnsino planoDeEnsino){
        if(planoDeEnsino != null){
            this.planoDeEnsino = planoDeEnsino;
        }
    }
}