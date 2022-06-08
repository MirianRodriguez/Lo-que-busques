package edu.egg.loquebusques.dto;

import java.time.LocalDate;
import java.util.List;

import edu.egg.loquebusques.entidades.Categoria;
import edu.egg.loquebusques.entidades.FormaPago;
import edu.egg.loquebusques.entidades.Localidad;

public class EmprendimientoDTO {

    // Entidad Emprendimiento
    private Integer emprendimientoId;
    private String nombre;
    private String descripcion;
    private String telefono;
    private String horario;
    private FormaPago formasPago;
    private LocalDate inicioActividades;
    private List<Categoria> categorias;

    // Entidad Domicilio
    private Integer domicilioId;
    private Localidad localidad;
    private String calle;
    private Integer numero;
    private String codPostal;
    private String referencia;

    public EmprendimientoDTO() {
    }

    public EmprendimientoDTO(Integer emprendimientoId, String nombre, String descripcion, String telefono,
            String horario, FormaPago formasPago, LocalDate inicioActividades, List<Categoria> categorias,
            Integer domicilioId, Localidad localidad, String calle, Integer numero, String codPostal,
            String referencia) {
        this.emprendimientoId = emprendimientoId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.telefono = telefono;
        this.horario = horario;
        this.formasPago = formasPago;
        this.inicioActividades = inicioActividades;
        this.categorias = categorias;
        this.domicilioId = domicilioId;
        this.localidad = localidad;
        this.calle = calle;
        this.numero = numero;
        this.codPostal = codPostal;
        this.referencia = referencia;
    }

    public Integer getEmprendimientoId() {
        return emprendimientoId;
    }

    public void setEmprendimientoId(Integer emprendimientoId) {
        this.emprendimientoId = emprendimientoId;
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

    public LocalDate getInicioActividades() {
        return inicioActividades;
    }

    public void setInicioActividades(LocalDate inicioActividades) {
        this.inicioActividades = inicioActividades;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Integer getDomicilioId() {
        return domicilioId;
    }

    public void setDomicilioId(Integer domicilioId) {
        this.domicilioId = domicilioId;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
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

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    
    
    }
