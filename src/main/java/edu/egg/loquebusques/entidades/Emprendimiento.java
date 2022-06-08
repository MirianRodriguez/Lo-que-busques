package edu.egg.loquebusques.entidades;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import static javax.persistence.EnumType.STRING;
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

    @Column(name = "imagen")
    private String imagen;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "horario", length = 70)
    private String horario;

    @Enumerated(STRING)
    @Column(name = "formas_pago")
    private FormaPago formasPago; //List<FormaPago>

    @ManyToMany
    @JoinColumn(name = "categorias", referencedColumnName = "categoria_id")
    private List<Categoria> categorias;

    @OneToOne
    @JoinColumn(name = "domicilio_id", referencedColumnName = "domicilio_id")
    private Domicilio domicilio;

    @Column(name = "inicioActividades", nullable =  false, columnDefinition = "DATE")
    private LocalDate inicioActividades;

    @OneToMany(mappedBy = "emprendimiento")
    //@JoinColumn(name = "articulos", referencedColumnName = "articulo_id")
    private List<Articulo> articulos;

    @Column(name = "eliminado", nullable = false)
    private Boolean eliminado;

        /* Constructores - Getters y Setters */

    public Emprendimiento() {
        this.eliminado = false;
    }

    public Emprendimiento(Integer id, String nombre, String descripcion, String imagen, String telefono, String horario,
            FormaPago formasPago, List<Categoria> categorias, Domicilio domicilio, LocalDate inicioActividades,
            List<Articulo> articulos, Boolean eliminado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.telefono = telefono;
        this.horario = horario;
        this.formasPago = formasPago;
        this.categorias = categorias;
        this.domicilio = domicilio;
        this.inicioActividades = inicioActividades;
        this.articulos = articulos;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
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

    public FormaPago getFormasPago() {
        return formasPago;
    }

    public void setFormasPago(FormaPago formasPago) {
        this.formasPago = formasPago;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilios(Domicilio domicilio) {
        this.domicilio = domicilio;
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

}
