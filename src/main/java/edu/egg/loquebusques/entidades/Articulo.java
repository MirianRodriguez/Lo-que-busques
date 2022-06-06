package edu.egg.loquebusques.entidades;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "articulo", indexes = {@Index(name = "idx_nombre", columnList = "nombre")})
public class Articulo {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @Lob
    @Column(name = "descripcion", columnDefinition="BLOB", nullable = false)
    private String descripcion;

    @Column(name = "foto")
    private String foto;

    @Column(name = "precio", columnDefinition = "DECIMAL(10,2)", nullable = false)
    private BigDecimal precio;

    @Column(name = "envio_a_domicilio", nullable = false)
    private Boolean envioADomicilio;

    @OneToOne(fetch = EAGER)
    @JoinColumn(name = "demora", referencedColumnName = "id")
    private Demora demora;

    @Column(name = "fecha_publicacion", nullable = false, columnDefinition = "DATE")
    private LocalDate fechaPublicacion;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "categoria", referencedColumnName = "id")
    private Categoria categoria;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "emprendimiento", nullable = false, referencedColumnName = "id")
    private Emprendimiento emprendimiento;

    @Column(name = "eliminado", nullable = false)
    private Boolean eliminado;

    public Articulo() {
        this.envioADomicilio = false;
        this.eliminado = false;
        this.fechaPublicacion = LocalDate.now();
    }

    public Articulo(Integer id, String nombre, String descripcion, String foto, BigDecimal precio,
            Boolean envioADomicilio, Demora demora, LocalDate fechaPublicacion, Categoria categoria,
            Emprendimiento emprendimiento, Boolean eliminado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.foto = foto;
        this.precio = precio;
        this.envioADomicilio = envioADomicilio;
        this.demora = demora;
        this.fechaPublicacion = fechaPublicacion;
        this.categoria = categoria;
        this.emprendimiento = emprendimiento;
        this.eliminado = eliminado;
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

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Boolean getEnvioADomicilio() {
        return envioADomicilio;
    }

    public void setEnvioADomicilio(Boolean envioADomicilio) {
        this.envioADomicilio = envioADomicilio;
    }

    public Demora getDemora() {
        return demora;
    }

    public void setDemora(Demora demora) {
        this.demora = demora;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }


    public Emprendimiento getEmprendimiento() {
        return emprendimiento;
    }

    public void setEmprendimiento(Emprendimiento emprendimiento) {
        this.emprendimiento = emprendimiento;
    }


    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Articulo [demora=" + demora + ", descripcion=" + descripcion + ", eliminado=" + eliminado
                + ", envioADomicilio=" + envioADomicilio + ", fechaPublicacion=" + fechaPublicacion + ", foto=" + foto
                + ", id=" + id + ", nombre=" + nombre + ", precio=" + precio + "]";
    }

    
}