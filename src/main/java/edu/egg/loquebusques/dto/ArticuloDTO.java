package edu.egg.loquebusques.dto;

import java.math.BigDecimal;

import edu.egg.loquebusques.entidades.Emprendimiento;
import edu.egg.loquebusques.entidades.UnidadTiempo;

public class ArticuloDTO {
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Boolean envioADomicilio;
    private Categoria categoria;
    private Emprendimiento emprendimiento;

    private Integer cantidad;
    private UnidadTiempo unidadTiempo;
    
    public ArticuloDTO() {
    }

    public ArticuloDTO(String nombre, String descripcion, BigDecimal precio, Boolean envioADomicilio,
            Categoria categoria, Emprendimiento emprendimiento, Integer cantidad, UnidadTiempo unidadTiempo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.envioADomicilio = envioADomicilio;
        this.categoria = categoria;
        this.emprendimiento = emprendimiento;
        this.cantidad = cantidad;
        this.unidadTiempo = unidadTiempo;
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Emprendimiento getEmprendimiento() {
        return emprendimiento;
    }

    public void setEmprendimiento(Emprendimiento emprendimiento) {
        this.emprendimiento = emprendimiento;
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

    
}
