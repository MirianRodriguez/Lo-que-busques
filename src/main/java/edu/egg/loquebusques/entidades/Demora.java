package edu.egg.loquebusques.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "demora")
@SQLDelete(sql = "UPDATE demora SET eliminado = true WHERE id = ?") 
@Where(clause = "eliminado = false")
public class Demora {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidad_tiempo", nullable = false)
    private UnidadTiempo unidadTiempo;

    @Column(name = "eliminado", nullable = false)
    private Boolean eliminado;

    public Demora() {
        this.eliminado = false;
    }

    public Demora(Integer id, Integer cantidad, UnidadTiempo unidadTiempo, Boolean eliminado) {
        this.id = id;
        this.cantidad = cantidad;
        this.unidadTiempo = unidadTiempo;
        this.eliminado = eliminado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public UnidadTiempo getUnidadTiempo() {
        return unidadTiempo;
    }

    public void setUnidadTiempo(UnidadTiempo unidadTiempo) {
        this.unidadTiempo = unidadTiempo;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }

    
}
