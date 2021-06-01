/*
 * Autor: Christian Felipe de Jesus Avila Valdes
 * Versión: 1.0
 * Fecha Creación: 3 - mar - 2021
 * Descripción:
 * Clase que contiene la información de Organización Vinculada
 */
package Entities;

import Database.MySqlConnection;
import Enumerations.TipoSector;

import java.sql.PreparedStatement;
import java.util.List;

/**
 * Clase que contiene la información de Organización Vinculada
 */
public class OrganizacionVinculada {
    private String nombre;
    private String direccion;
    private TipoSector sector;
    private String telefono;
    private String correoElectronico;
    private int idOrganizacion;
    private List< Integer > idResponsables;

    /**
     * Constructor sin parámetros de la clase OrganizaciónVinculada.
     * Crea una instancia con valores nulos y cadenas vacias.
     */
    public OrganizacionVinculada() {
        nombre = "";
        direccion = "";
        sector = null;
        telefono = "";
        correoElectronico = "";
        idOrganizacion = 0;
        idResponsables = null;
    }

    /**
     * Crea una nueva instancia de la clase OrganizaciónVinculada a partir
     * de una instancia existente.
     * @param original la instancia que se desea duplicar
     */
    public OrganizacionVinculada( OrganizacionVinculada original ) {
        this( original.nombre, original.direccion, original.sector, original.telefono, original.correoElectronico,
                original.idOrganizacion, original.idResponsables );
    }

    /**
     * Constructor de la clase OrganizaciónVinculada. Crea una instancia
     * con los valores introducidos.
     * @param nombreIn el nombre del organización
     * @param direccionIn la dirección de la organización
     * @param sectorIn el sector al que pertenece la organización
     * @param telefonoIn el telefono de la organización
     * @param correoIn el correo electronico de la organización
     */
    public OrganizacionVinculada( String nombreIn, String direccionIn, TipoSector sectorIn, String telefonoIn,
                                  String correoIn, int idOrganizacionIn, List< Integer > idResponsablesIn ) {
        nombre = nombreIn;
        direccion = direccionIn;
        sector = sectorIn;
        telefono = telefonoIn;
        correoElectronico = correoIn;
        idOrganizacion = idOrganizacionIn;
        idResponsables = idResponsablesIn;
    }

    public int getIdOrganizacion() { return idOrganizacion; }

    /**
     * Regresa el nombre de la organización vinculada
     * @return el nombre de la organización
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Regresa la dirección de la organización vinculada
     * @return la dirección de la organización
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Regresa el sector de la organización vinculada
     * @return el sector de la organización
     */
    public TipoSector getSector() {
        return sector;
    }

    /**
     * Regresa el telefono de la organización vinculada
     * @return el telefono de la organización
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Regresa el correo electrónico de la organización vinculada
     * @return el correo de la organización
     */
    public String getCorreo() {
        return correoElectronico;
    }

    /**
     * Regresa la lista IDs de los responsables de proyecto relacionados con
     * esta instancia de organizacion vinculada.
     * @return una lista con los IDs de los responsables de proyecto
     */
    public List< Integer > getResponsables() { return idResponsables; }

    /**
     * Cambia el nombre de la organización al valor introducido
     * @param nombreIn el nuevo nombre
     */
    public void SetNombre( String nombreIn ) {
        nombre = nombreIn;
    }

    /**
     * Cambia la dirección de la organización vinculada al valor introducido
     * @param direccionIn la nueva dirección
     */
    public void SetDireccion( String direccionIn ) {
        direccion = direccionIn;
    }

    /**
     * Cambia el sector de la organización vinculada al valor introducido
     * @param sectorIn el nuevo sector
     */
    public void SetSector( TipoSector sectorIn ) {
        sector = sectorIn;
    }

    /**
     *Cambia el teléfono de la organización vinculada al valor introducido
     * @param telefonoIn el nuevo teléfono
     */
    public void SetTelefono( String telefonoIn ) {
        telefono = telefonoIn;
    }

    /**
     * Cambia el correo electrónico de la organización vonculada al valor
     * introducido
     * @param correoIn el nuevo correo electrónico
     */
    public void SetCorreo( String correoIn ) {
        correoElectronico = correoIn;
    }

    /**
     * Agrega una ID de responsable proyecto a la lista de responsables
     * relacionados a esta instancia de organizacion vinculada.
     * @param idResponsable
     * @return booleano indicando exito o fracaso
     */
    public boolean AddResponsable( int idResponsable ) {
        boolean added = false;
        if( !idResponsables.contains( idResponsable ) ) {
            idResponsables.add(idResponsable);
            added = true;
        }
        return added;
    }

    /**
     * Elimina un ID de responsable de proyecto en caso de estar relacionado
     * a esta instancia de organizacion vinculada.
     * @param idResponsable el ID del responsable que se desea eliminar.
     */
    public void RemoveResponsable( int idResponsable ) {
        for( int i = 0; i < idResponsables.size(); i++ ) {
            if( idResponsables.get( i ) == idResponsable ) {
                idResponsables.remove( i );
            }
        }
    }

}