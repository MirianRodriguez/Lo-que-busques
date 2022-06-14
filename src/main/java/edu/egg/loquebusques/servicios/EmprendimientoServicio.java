package edu.egg.loquebusques.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.egg.loquebusques.entidades.Articulo;
import edu.egg.loquebusques.entidades.Emprendimiento;
import edu.egg.loquebusques.entidades.Usuario;
import edu.egg.loquebusques.repositorios.DomicilioRepositorio;
import edu.egg.loquebusques.repositorios.EmprendimientoRepositorio;

@Service
public class EmprendimientoServicio {

    @Autowired
    private EmprendimientoRepositorio emprendimientoRepositorio;

    @Autowired
    private DomicilioRepositorio domicilioRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void crear(Usuario usuarioDTO) {

        Emprendimiento emprendimiento = new Emprendimiento();
        emprendimiento.setUsuario(usuarioDTO);
        emprendimientoRepositorio.save(emprendimiento);
    }

    @Transactional
    public void actualizar(Emprendimiento emprendimientoDTO, MultipartFile foto) {

        if (emprendimientoRepositorio.existsByNombre(emprendimientoDTO.getNombre())) {
            throw new IllegalArgumentException("Ya existe un emprendimiento con ese nombre");
        }

        Emprendimiento emprendimiento = emprendimientoRepositorio.findById(emprendimientoDTO.getId()).get();

        emprendimiento.setNombre(emprendimientoDTO.getNombre());
        emprendimiento.setDescripcion(emprendimientoDTO.getDescripcion());
        emprendimiento.setImagen(emprendimientoDTO.getImagen());
        emprendimiento.setTelefono(emprendimientoDTO.getTelefono());
        emprendimiento.setHorario(emprendimientoDTO.getHorario());
        emprendimiento.setFormasPago(emprendimientoDTO.getFormasPago());
        emprendimiento.setCategorias(emprendimientoDTO.getCategorias());
        if (emprendimientoDTO.getDomicilio() != null) {
            emprendimiento.setDomicilio(emprendimientoDTO.getDomicilio());
        }
        emprendimiento.setInicioActividades(emprendimientoDTO.getInicioActividades());
        emprendimiento.setArticulos(emprendimientoDTO.getArticulos());

        if (!foto.isEmpty()) {
            emprendimiento.setImagen(imagenServicio.copiar(foto));
        }

        if (emprendimientoDTO.getDomicilio() != null) {
            domicilioRepositorio.save(emprendimiento.getDomicilio());
        }

        emprendimientoRepositorio.save(emprendimiento);
    }

    @Transactional(readOnly = true)
    public List<Emprendimiento> obtenerTodos() {
        return emprendimientoRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public List<Emprendimiento> obtenerTodosActivos() {
        return emprendimientoRepositorio.obtenerTodosActivos();
    }

    @Transactional(readOnly = true)
    public Emprendimiento obtenerPorId(Integer id) {
        return emprendimientoRepositorio.findById(id).get();
    }

    @Transactional
    public void eliminarPorId(Integer id) {
        emprendimientoRepositorio.eliminarArticulosDelEmprendimiento(id);
        emprendimientoRepositorio.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Articulo> articulosDeUnEmprendimiento(Integer emprendimiento_id) {
        return emprendimientoRepositorio.articulosDeUnEmprendimiento(emprendimiento_id);
    }

}
