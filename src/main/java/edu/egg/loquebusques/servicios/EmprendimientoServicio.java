package edu.egg.loquebusques.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.egg.loquebusques.entidades.Emprendimiento;
import edu.egg.loquebusques.repositorios.EmprendimientoRepositorio;


@Service
public class EmprendimientoServicio{

    @Autowired
    private EmprendimientoRepositorio emprendimientoRepositorio;

    @Transactional
    public void crear(Emprendimiento emprendimientoDto) {

        if (emprendimientoRepositorio.existsByNombre(emprendimientoDto.getNombre())){
            throw new IllegalArgumentException("Ya existe un emprendimiento con ese nombre");
        }

        Emprendimiento emprendimiento = new Emprendimiento();

        emprendimiento.setNombre(emprendimientoDto.getNombre());
        emprendimiento.setDescripcion(emprendimientoDto.getDescripcion());
        emprendimiento.setImagen(emprendimientoDto.getImagen());
        emprendimiento.setTelefono(emprendimientoDto.getTelefono());
        emprendimiento.setHorario(emprendimientoDto.getHorario());
        emprendimiento.setFormasPago(emprendimientoDto.getFormasPago());
        emprendimiento.setCategorias(emprendimientoDto.getCategorias());
        emprendimiento.setDomicilio(emprendimientoDto.getDomicilio());
        emprendimiento.setInicioActividades(emprendimientoDto.getInicioActividades());
        emprendimiento.setArticulos(emprendimientoDto.getArticulos());
        

        emprendimientoRepositorio.save(emprendimiento);
    }
}

