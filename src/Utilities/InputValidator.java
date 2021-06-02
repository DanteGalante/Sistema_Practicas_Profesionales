/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 31 - mar - 2021
 * Descripción:
 * Clase encargada de validar la información introducida
 * por el usuario.
 */
package Utilities;

import Entities.Estudiante;
import Entities.OrganizacionVinculada;
import Entities.ResponsableProyecto;

/**
 * Clase encargada de validar la información introducida
 * por el usuario.
 */
public class InputValidator {
    private final int minNameSize = 3;
    private final int maxNameSize = 30;
    private final int minLastNameSize = 3;
    private final int maxLastNameSize = 20;
    private final int matriculaSize = 10;
    private final int minEmailSize = 10;
    private final int maxEmailSize = 40;
    private final int minPasswordSize = 8;
    private final int maxPasswordSize = 20;
    private final int phoneSize = 10;
    private final int nrcSize = 5;
    private final int minDireccion = 10;
    private final int maxDireccion = 200;

    /**
     * Verifica que la información de una instancia de Estudiante sea valida y que las
     * contraseñas introducidas en el registro coinciden
     * @param student la instancia de Estudiante que se desea verificar
     * @param passwordConfirm la contraseña que se revisa contra la contraseña del Estudiante
     * @return true si todos los campos del Estudiante son validos, false si no
     */
    public boolean IsStudentInformationValid( Estudiante student, String passwordConfirm ) {
        return AreNamesValid( student.getNombres() ) && AreLastNamesValid( student.GetApellidos() ) &&
                IsMatriculaValid( student.getMatricula() ) && IsTelephoneValid( student.GetTelefono() ) &&
                IsEmailValid( student.GetCorreo() ) && IsNRCValid( student.getNrc() ) &&
                IsPasswordValid( student.GetContrasena() ) && DoPasswordsMatch( student.GetContrasena(), passwordConfirm );
    }

    /**
     * Verifica que la información de una instancia de Organización Vinculada sea valida
     * @param organizacionVinculada la instancia de OrganizacionVinculada que se desea verificar
     * @return true si todos los campos de la OrganizacionVinculada son validos, false si no
     */
    public boolean IsOrganizationInformationValid(OrganizacionVinculada organizacionVinculada ) {
        return AreNamesValid( organizacionVinculada.getNombre() ) && DireccionValida( organizacionVinculada.getDireccion() ) &&
                IsEmailValid( organizacionVinculada.getCorreo() ) && IsTelephoneValid( organizacionVinculada.getTelefono() );
    }

    /**
     * Verifica que la información de una instancia de ResponsableProyecto sea valida
     * @param responsableProyecto la instancia de ResponsableProyecto que se desea verificar
     * @return true si todos los campos de la OrganizacionVinculada son validos, false si no
     */
    public boolean IsResponsableInformationValid(ResponsableProyecto responsableProyecto ) {
        return AreNamesValid( responsableProyecto.GetNombres() ) && AreLastNamesValid( responsableProyecto.GetApellidos() ) &&
                IsEmailValid( responsableProyecto.GetCorreo() ) && IsTelephoneValid( responsableProyecto.GetTelefono() );
    }

    /**
     * Verifica que la información de inicia de sesión introducida por el usuario sea valida.
     * @param username el número personal o matrícula introducida por el usuario
     * @param password la contraseña introducida por el usuario
     * @return true si la información es valida, false si no
     */
    public boolean IsLoginInformationValid( String username, String password ) {
        return ( IsMatriculaValid( username ) || IsNumeroPersonalDocenteValid( username ) ||
                IsNumeroPersonalCoordinadorValid( username ) ) && IsPasswordValid( password );
    }

    /**
     * Verifica que nombres introducidos por el usuario sean validos
     * @param name la cadena con los nombres
     * @return true si los nombres son validos, false si no
     */
    public boolean AreNamesValid( String name ) {
        return IsStringValidSize( name, minNameSize, maxNameSize ) && !HasInvalidCharacter( name ) &&
                !HasNumbers( name );
    }

    /**
     * Verifica que apellidos introducidos por el usuario sean validos
     * @param lastNames la cadena con los apellidos
     * @return true si los apellidos son validos, false si no
     */
    public boolean AreLastNamesValid( String lastNames ) {
        return IsStringValidSize( lastNames, minLastNameSize, maxLastNameSize ) && !HasInvalidCharacter( lastNames ) &&
                !HasNumbers( lastNames );
    }

    /**
     * Verifica que la matrícula introducida por el usuario sea valida
     * @param matricula la cadena con la matrícula
     * @return true si la matrícula es valida, false si no
     */
    public boolean IsMatriculaValid( String matricula ) {
        return matricula.length() == matriculaSize && !HasSpaces( matricula ) && HasZAndSChar( matricula ) &&
                Has8Numbers( matricula ) && !HasInvalidCharacter( matricula );
    }

    /**
     * Verifica que el numero personal introducido por el usuario sea valido
     * @param numeroPersonal la cadena con el numero personal
     * @return true si el numero personal es valido, false si no
     */
    public boolean IsNumeroPersonalCoordinadorValid( String numeroPersonal ) {
        return numeroPersonal.length() == matriculaSize && !HasSpaces( numeroPersonal ) && HasZAndCChar( numeroPersonal ) &&
                Has8Numbers( numeroPersonal ) && !HasInvalidCharacter( numeroPersonal );
    }

    /**
     * Verifica que el numero personal introducido por el usuario sea valido
     * @param numeroPersonal la cadena con el numero personal
     * @return true si el numero personal es valido, false si no
     */
    public boolean IsNumeroPersonalDocenteValid( String numeroPersonal ) {
        return numeroPersonal.length() == matriculaSize && !HasSpaces( numeroPersonal ) && HasZAndDChar( numeroPersonal ) &&
                Has8Numbers( numeroPersonal ) && !HasInvalidCharacter( numeroPersonal );
    }

    /**
     * Verifica que el teléfono introducido por el usuario es valido
     * @param telephone la cadena con el teléfono
     * @return true si el teléfono es valida, false si no
     */
    public boolean IsTelephoneValid( String telephone ) {
        return telephone.length() == phoneSize && HasOnlyNumbers( telephone );
    }

    /**
     * Verifica que el correo electrónico introducido por el usuario es valido
     * @param email la cadena con el correo electrónico
     * @return true si el correo electrónico es valida, false si no
     */
    public boolean IsEmailValid( String email ) {
        return IsStringValidSize( email, minEmailSize, maxEmailSize ) && HasSingleAtChar( email ) &&
                !HasInvalidCharacter( email ) && !HasSpaces( email );
    }

    /**
     * Verifica que la direccion introducida por el usuario sea valida
     * @param direccion la cadena con los nombres
     * @return true si la sireccion es valida, false si no
     */
    public boolean DireccionValida( String direccion ) {
        return IsStringValidSize( direccion, minDireccion, maxDireccion );
    }

    /**
     * Verifica que el NRC introducido por el usuario es valido
     * @param nrc la cadena con el NRC
     * @return true si el NRC es valida, false si no
     */
    public boolean IsNRCValid( String nrc ) {
        return nrc.length() == nrcSize && HasOnlyNumbers( nrc );
    }

    /**
     * Verifica que la contraseña introducida por el usuario sea valida
     * @param password la cadena con la contraseña
     * @return true si la contraseña es valida, false si no
     */
    public boolean IsPasswordValid( String password ) {
        return IsStringValidSize( password, minPasswordSize, maxPasswordSize ) && !HasInvalidCharacter( password ) &&
                !HasSpaces( password );
    }

    /**
     * Verifica que las constraseñas introducida por el usuario coincidan
     * @param password las cadenas con la contraseñas
     * @return true si la contraseñas coinciden, false si no
     */
    public boolean DoPasswordsMatch( String password, String confirmPassword ) {
        return password.equals( confirmPassword );
    }

    /**
     * Revisa que una cadena tenga un mínimo y máximo de longitud
     * @param input la cadena que se desea revisar
     * @param minSize el tamaño mínimo permitido de la cadena
     * @param maxSize el tamaño máximo permitido de la cadena
     * @return true si la cadena tiene una logitud dentro de los rangos introducidos, false si no
     */
    private boolean IsStringValidSize( String input, int minSize, int maxSize ) {
        return ( input.length() >= minSize && input.length() <= maxSize );
    }

    /**
     * Revisa si una cadena contiene números
     * @param input la cadena que se desea revisar
     * @return true si la cadena contiene números, false si no
     */
    private boolean HasNumbers( String input ) {
        boolean hasNumbers = false;
        char[] testInput = input.toCharArray();
        for( char currentCharacter : testInput ) {
            if( Character.isDigit( currentCharacter ) ) {
                hasNumbers = true;
            }
        }
        return hasNumbers;
    }

    /**
     * Revisa si una cadena contiene espacios
     * @param input la cadena que se desea revisar
     * @return true si la cadena contiene espacios, false si no
     */
    private boolean HasSpaces( String input ) {
        boolean hasSpaces = false;
        char[] testInput = input.toCharArray();
        for( char currentCharacter : testInput ) {
            if( currentCharacter == ' ' ) {
                hasSpaces = true;
            }
        }
        return hasSpaces;
    }

    /**
     * Revisa si la cadena contiene el character "@"
     * @param input la cadena que se desea revisar
     * @return true si contiene el character "@", false si no
     */
    private boolean HasSingleAtChar( String input ) {
        int atCounter = 0;
        char[] testInput = input.toCharArray();
        for( char currentCharacter : testInput ) {
            if( currentCharacter == '@' ) {
                atCounter += 1;
            }
        }
        return atCounter == 1;
    }

    /**
     * Revisa si la cadena contiene characteres no permitidos de
     * funciones SQL.
     * @param input la cadena que se desea revisar
     * @return true si contiene characteres no permitidos, false si no
     */
    private boolean HasInvalidCharacter( String input ) {
        boolean hasInvalidCharacter = false;
        char[] testInput = input.toCharArray();
        for( char currentCharacter : testInput ) {
            if( currentCharacter == '|' || currentCharacter == ';' ||
                currentCharacter == '=' || currentCharacter == 39 ) {
                hasInvalidCharacter = true;
            }
        }
        return hasInvalidCharacter;
    }

    /**
     * Revisa si la cadena contiene exactamente 8 números.
     * Utilizada para revisar si una matrícula es valida.
     * @param input la cadena que se desea revisar
     * @return true si la cadena contiene únicamente 8 números, false si no
     */
    private boolean Has8Numbers( String input ) {
        int numberCount = 0;
        char[] testInput = input.toCharArray();
        for( char currentCharacter : testInput ) {
            if( Character.isDigit( currentCharacter ) ) {
                numberCount++;
            }
        }
        return numberCount == 8;
    }

    /**
     * Revisa si una cadena contiene los caracteres zS al
     * inicio de la cadena
     * @param input la cadena que se desea revisar
     * @return true si contiene zS al inicio, false si no
     */
    private boolean HasZAndSChar( String input ) {
        return input.contains( "zS" ) && input.lastIndexOf( "zS" ) == 0;
    }

    /**
     * Revisa si una cadena contiene los caracteres zC al inicio
     * de la cadena
     * @param input la cadena que se desea revisar
     * @return true si contiene zC al inicio, false si no
     */
    private boolean HasZAndCChar( String input ) {
        return input.contains( "zC" ) && input.lastIndexOf( "zC" ) == 0;
    }

    /**
     * Recisa si una cadena contiene los caracteres zD al inicio
     * de la cadena
     * @param input la cadena que se desea revisar
     * @return true si contiene zD al inicio, false si no
     */
    private boolean HasZAndDChar( String input ) { return input.contains( "zD" ) && input.lastIndexOf( "zD" ) == 0; }

    /**
     * Revisa si una cadena esta compuesta únicamente de números
     * @param input la cadena que se desea revisar
     * @return true si la cadena solo contiene número, false si no
     */
    private boolean HasOnlyNumbers( String input ) {
        boolean hasOnlyNumbers = true;
        char[] testInput = input.toCharArray();
        for( char currentCharacter : testInput ) {
            if( !Character.isDigit( currentCharacter ) ) {
                hasOnlyNumbers = false;
            }
        }
        return hasOnlyNumbers;
    }
}