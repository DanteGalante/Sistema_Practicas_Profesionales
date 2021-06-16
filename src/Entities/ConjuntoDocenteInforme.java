package Entities;

/**
 * Clase que contiene la información de docente
 * e informe.
 */
public class ConjuntoDocenteInforme {
    private Docente docente;
    private InformeProblema informeProblema;

    public ConjuntoDocenteInforme(Docente docente,InformeProblema informe){
        this.docente = docente;
        this.informeProblema = informe;
    }

    public String getNombreDocente(){
        return docente.getNombreCompleto();
    }

    /**
     * Regresa el nrc del Docente
     * @return
     */
    public String getNrc() {
        return docente.GetNrc();
    }

    /**
     * Regresa la fecha en la cual fue enviado el informe
     * @return la fecha de envio
     */
    public String getFechaEnviada() {

        return informeProblema.getFechaEnviada();
    }

    /**
     * Regresa el número de personal de la persona que envió el informe
     * @return el número de personal
     */
    public String getNumeroPersonal() {

        return docente.GetNumeroPersonal();
    }

    /**
     * Regresa la matrícula del estudiante del cual trata el informe
     * @return la matrícula del estudiante
     */
    public String getEstudiante() {
        return informeProblema.getEstudiante();
    }

    /**
     * Regresa el asunto del informe
     * @return el asunto del informe
     */
    public String getAsunto() {
        return informeProblema.getAsunto();
    }

    /**
     * Regresa el contenido del informe
     * @return el contenido del informe
     */
    public String getContenido() {

        return informeProblema.GetContenido();
    }

    /**
     * Regresa el InformeProblema
     * @return InformeProblema
     */
    public InformeProblema getInforme() {
        return informeProblema;
    }
}
