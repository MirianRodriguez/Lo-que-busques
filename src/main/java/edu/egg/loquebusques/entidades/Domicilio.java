package edu.egg.loquebusques.entidades;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "domicilio")
@SQLDelete(sql = "UPDATE categoria SET eliminado = true WHERE domicilio_id = ?") 
@Where(clause = "eliminado = false")
public class Domicilio {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "domicilio_id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "localidad", nullable = false)
    private Localidad localidad;

    @Column(name = "calle", length = 50, nullable = false)
    private String calle;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "codPostal", length = 50, nullable = false)
    private String codPostal;

    @Lob
    @Column(name = "referencia", columnDefinition="BLOB")
    private String referencia;

    @Column(name = "eliminado", nullable = false)
    private Boolean eliminado;
    
    public Domicilio(String calle, Integer numero, String codPostal, String referencia, Boolean eliminado) {
        this.calle = calle;
        this.numero = numero;
        this.codPostal = codPostal;
        this.referencia = referencia;
        this.eliminado = eliminado;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setUnidadTiempo(Localidad localidad) {
        this.localidad = localidad;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getcodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia= referencia;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }


    

}
