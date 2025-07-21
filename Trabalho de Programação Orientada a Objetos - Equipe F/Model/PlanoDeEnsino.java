package Model;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PlanoDeEnsino {
    // Campos existentes
    private String objetivos;
    private String ementa;
    private String metodologia;
    private String avaliacoes;
    private String bibliografia;
    private int cargaHorariaTeorica;
    private int cargaHorariaPratica;
    private String justificativa;
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private String atividadesDiscentes;
    private String parecer;
    
    // Campos de identificação (preenchidos automaticamente)
    private String nomeProfessor;
    private String codigoMateria;
    private String nomeMateria;

    // Novos campos para horário de aula
    private LocalTime horaInicio;
    private LocalTime horaTermino;

    // --- CAMPO ADICIONADO ---
    private String turma;

    public PlanoDeEnsino(){}

    public PlanoDeEnsino(String objetivos, String ementa, String metodologia, String bibliografia){
        setObjetivos(objetivos);
        setBibliografia(bibliografia);
        setEmenta(ementa);
        setMetodologia(metodologia);
    }

    // Construtor com os novos campos de horário
    public PlanoDeEnsino(String objetivos, String ementa, String metodologia, String bibliografia, LocalTime horaInicio, LocalTime horaTermino){
        this(objetivos, ementa, metodologia, bibliografia);
        setHoraInicio(horaInicio);
        setHoraTermino(horaTermino);
    }

    // Getters e Setters existentes
    public String getObjetivos(){
        return this.objetivos;
    }

    public void setObjetivos(String objetivos){
        if(objetivos != null && !objetivos.trim().isEmpty()){
            this.objetivos = objetivos.trim();
        } else {
            throw new IllegalArgumentException("Objetivos não podem ser nulos ou vazios");
        }
    }

    public String getEmenta(){
        return this.ementa;
    }

    public void setEmenta(String ementa){
        if(ementa != null && !ementa.trim().isEmpty()){
            this.ementa = ementa.trim();
        } else {
            throw new IllegalArgumentException("Ementa não pode ser nula ou vazia");
        }
    }

    public String getMetodologia(){
        return this.metodologia;
    }

    public void setMetodologia(String metodologia){
        if(metodologia != null && !metodologia.trim().isEmpty()){
            this.metodologia = metodologia.trim();
        } else {
            throw new IllegalArgumentException("Metodologia não pode ser nula ou vazia");
        }
    }

    public String getAvaliacoes(){
        return this.avaliacoes;
    }

    public void setAvaliacoes(String avaliacoes){
        if(avaliacoes != null && !avaliacoes.trim().isEmpty()){
            this.avaliacoes = avaliacoes.trim();
        } else {
            throw new IllegalArgumentException("Sistema de avaliação não pode ser nulo ou vazio");
        }
    }

    public String getBibliografia(){
        return this.bibliografia;
    }

    public void setBibliografia(String bibliografia){
        if(bibliografia != null && !bibliografia.trim().isEmpty()){
            this.bibliografia = bibliografia.trim();
        } else {
            throw new IllegalArgumentException("Bibliografia não pode ser nula ou vazia");
        }
    }

    // Novos getters e setters
    public int getCargaHorariaTeorica(){
        return this.cargaHorariaTeorica;
    }

    public void setCargaHorariaTeorica(int cargaHorariaTeorica){
        if(cargaHorariaTeorica >= 0){
            this.cargaHorariaTeorica = cargaHorariaTeorica;
        } else {
            throw new IllegalArgumentException("Carga horária teórica deve ser maior ou igual a zero");
        }
    }

    public int getCargaHorariaPratica(){
        return this.cargaHorariaPratica;
    }

    public void setCargaHorariaPratica(int cargaHorariaPratica){
        if(cargaHorariaPratica >= 0){
            this.cargaHorariaPratica = cargaHorariaPratica;
        } else {
            throw new IllegalArgumentException("Carga horária prática deve ser maior ou igual a zero");
        }
    }

    public String getJustificativa(){
        return this.justificativa;
    }

    public void setJustificativa(String justificativa){
        if(justificativa != null && !justificativa.trim().isEmpty()){
            this.justificativa = justificativa.trim();
        } else {
            throw new IllegalArgumentException("Justificativa não pode ser nula ou vazia");
        }
    }

    public LocalDate getDataInicial(){
        return this.dataInicial;
    }

    public void setDataInicial(LocalDate dataInicial){
        if(dataInicial != null){
            this.dataInicial = dataInicial;
        } else {
            throw new IllegalArgumentException("Data inicial não pode ser nula");
        }
    }

    public void setDataInicial(String dataInicial){
        if(dataInicial != null && !dataInicial.trim().isEmpty()){
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                this.dataInicial = LocalDate.parse(dataInicial.trim(), formatter);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Data inicial deve estar no formato dd/MM/yyyy");
            }
        } else {
            throw new IllegalArgumentException("Data inicial não pode ser nula ou vazia");
        }
    }

    public LocalDate getDataFinal(){
        return this.dataFinal;
    }

    public void setDataFinal(LocalDate dataFinal){
        if(dataFinal != null){
            if(this.dataInicial != null && dataFinal.isBefore(this.dataInicial)){
                throw new IllegalArgumentException("Data final não pode ser anterior à data inicial");
            }
            this.dataFinal = dataFinal;
        } else {
            throw new IllegalArgumentException("Data final não pode ser nula");
        }
    }

    public void setDataFinal(String dataFinal){
        if(dataFinal != null && !dataFinal.trim().isEmpty()){
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate data = LocalDate.parse(dataFinal.trim(), formatter);
                setDataFinal(data);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Data final deve estar no formato dd/MM/yyyy");
            }
        } else {
            throw new IllegalArgumentException("Data final não pode ser nula ou vazia");
        }
    }

    public String getAtividadesDiscentes(){
        return this.atividadesDiscentes;
    }

    public void setAtividadesDiscentes(String atividadesDiscentes){
        if(atividadesDiscentes != null && !atividadesDiscentes.trim().isEmpty()){
            this.atividadesDiscentes = atividadesDiscentes.trim();
        } else {
            throw new IllegalArgumentException("Atividades discentes não podem ser nulas ou vazias");
        }
    }

    public String getParecer(){
        return this.parecer;
    }

    public void setParecer(String parecer){
        if(parecer != null && !parecer.trim().isEmpty()){
            this.parecer = parecer.trim();
        } else {
            throw new IllegalArgumentException("Parecer não pode ser nulo ou vazio");
        }
    }

    public String getNomeProfessor(){
        return this.nomeProfessor;
    }

    public void setNomeProfessor(String nomeProfessor){
        if(nomeProfessor != null && !nomeProfessor.trim().isEmpty()){
            this.nomeProfessor = nomeProfessor.trim();
        } else {
            throw new IllegalArgumentException("Nome do professor não pode ser nulo ou vazio");
        }
    }

    public String getCodigoMateria(){
        return this.codigoMateria;
    }

    public void setCodigoMateria(String codigoMateria){
        if(codigoMateria != null && !codigoMateria.trim().isEmpty()){
            this.codigoMateria = codigoMateria.trim();
        } else {
            throw new IllegalArgumentException("Código da matéria não pode ser nulo ou vazio");
        }
    }

    public String getNomeMateria(){
        return this.nomeMateria;
    }

    public void setNomeMateria(String nomeMateria){
        if(nomeMateria != null && !nomeMateria.trim().isEmpty()){
            this.nomeMateria = nomeMateria.trim();
        } else {
            throw new IllegalArgumentException("Nome da matéria não pode ser nulo ou vazio");
        }
    }

    // Getters e Setters para horaInicio e horaTermino
    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraTermino() {
        return horaTermino;
    }

    public void setHoraTermino(LocalTime horaTermino) {
        this.horaTermino = horaTermino;
    }

    // --- GETTER E SETTER ADICIONADOS ---
    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        if (turma != null && !turma.trim().isEmpty()) {
            this.turma = turma.trim();
        } else {
            throw new IllegalArgumentException("Turma não pode ser nula ou vazia");
        }
    }


    public int getCargaHorariaTotal(){
        return this.cargaHorariaTeorica + this.cargaHorariaPratica;
    }

    public String getDataInicialFormatada(){
        if(this.dataInicial != null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return this.dataInicial.format(formatter);
        }
        return "";
    }

    public String getDataFinalFormatada(){
        if(this.dataFinal != null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return this.dataFinal.format(formatter);
        }
        return "";
    }

    @Override
    public String toString(){
        // --- toString ATUALIZADO ---
        return "PlanoDeEnsino{" +
                "nomeProfessor=\'" + nomeProfessor + '\'' +
                ", codigoMateria=\'" + codigoMateria + '\'' +
                ", nomeMateria=\'" + nomeMateria + '\'' +
                ", turma=\'" + turma + '\'' +
                ", cargaHorariaTeorica=" + cargaHorariaTeorica +
                ", cargaHorariaPratica=" + cargaHorariaPratica +
                ", dataInicial=" + getDataInicialFormatada() +
                ", dataFinal=" + getDataFinalFormatada() +
                ", horaInicio=" + (horaInicio != null ? horaInicio.format(DateTimeFormatter.ofPattern("HH:mm")) : "N/A") +
                ", horaTermino=" + (horaTermino != null ? horaTermino.format(DateTimeFormatter.ofPattern("HH:mm")) : "N/A") +
                '\n' +
                "Objetivos: " + objetivos + '\n' +
                "Ementa: " + ementa + '\n' +
                "Metodologia: " + metodologia + '\n' +
                "Bibliografia: " + bibliografia + '\n' +
                "}";
    }
}
