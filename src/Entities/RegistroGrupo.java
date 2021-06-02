package Entities;

public class RegistroGrupo {
    private String nrc;
    private String identificador;

    public RegistroGrupo( Estudiante estudiante ) {
        nrc = estudiante.getNrc();
        identificador = estudiante.getMatricula();
    }

    public RegistroGrupo( Docente docente ) {
        nrc = docente.GetNrc();
        identificador = docente.GetNumeroPersonal();
    }

    public RegistroGrupo( String identificadorIn, String nrcIn ) {
        nrc = nrcIn;
        identificador = identificadorIn;
    }

    public RegistroGrupo( RegistroGrupo original ) {
        nrc = original.nrc;
        identificador = original.identificador;
    }

    public String getNRC() {
        return nrc;
    }

    public String getIdentificador() {
        return identificador;
    }
}
