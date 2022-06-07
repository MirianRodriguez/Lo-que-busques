package edu.egg.loquebusques.entidades;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "emprendimiento", indexes = {@Index(name = "idx_nombre", columnList = "nombre")})
@SQLDelete(sql = "UPDATE emprendimiento SET eliminado = true WHERE emprendimiento_id = ?")
@Where(clause = "eliminado = false")
public class Emprendimiento {
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "emprendimiento_id")
    private Integer id;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @Lob
    @Column(name = "descripcion", columnDefinition="BLOB", nullable = false)
    private String descripcion;

    @Column(name = "foto")
    private String foto;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "horario", length = 70)
    private String horario;

    @Enumerated(EnumType.STRING)
    @Column(name = "formas_pago")
    private List<FormaPago> formasPago;

    @ManyToMany
    @JoinColumn(name = "categorias_id", referencedColumnName = "categorias_id")
    private List<Categoria> categorias;

    @ManyToMany
    private List<Domicilio> domicilios;

    @Column(name = "inicioActividades", nullable =  false, columnDefinition = "DATE")
    private LocalDate inicioActividades;

    @OneToMany(fetch = EAGER)
    @JoinColumn(name = "articulos_id", referencedColumnName = "articulos_id")
    private List<Articulo> articulos;

    @Column(name = "eliminado", nullable = false)
    private Boolean eliminado;

    @Column(name = "pendiente", nullable = false)
    private Boolean pendiente;

        /* Constructores - Getters y Setters */

    public Emprendimiento() {
        this.eliminado = false;
        this.pendiente = true;
    }

    public Emprendimiento(Integer id, String nombre, String descripcion, String foto, String telefono, String horario,
            List<FormaPago> formasPago, List<Categoria> categorias, List<Domicilio> domicilios, LocalDate inicioActividades,
            List<Articulo> articulos, Boolean eliminado, Boolean pendiente) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.foto = foto;
        this.telefono = telefono;
        this.horario = horario;
        this.formasPago = formasPago;
        this.categorias = categorias;
        this.domicilios = domicilios;
        this.inicioActividades = inicioActividades;
        this.articulos = articulos;
        this.eliminado = eliminado;
        this.pendiente = pendiente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public List<FormaPago> getFormaPago() {
        return formasPago;
    }

    public void setFormaPago(List<FormaPago> formasPago) {
        this.formasPago = formasPago;
    }

    public List<Categoria> getCategoria() {
        return categorias;
    }

    public void setCategoria(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public List<Domicilio> getDomicilio() {
        return domicilios;
    }

    public void setDomicilio(List<Domicilio> domicilios) {
        this.domicilios = domicilios;
    }

    public LocalDate getInicioActividades() {
        return inicioActividades;
    }

    public void setInicioActividades(LocalDate inicioActividades) {
        this.inicioActividades = inicioActividades;
    }

    public List<Articulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<Articulo> articulos) {
        this.articulos = articulos;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }

    public Boolean getPendiente() {
        return pendiente;
    }

    public void setPendiente(Boolean pendiente) {
        this.pendiente = pendiente;
    }

}
