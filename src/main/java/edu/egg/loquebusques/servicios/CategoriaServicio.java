package edu.egg.loquebusques.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.egg.loquebusques.repositorios.CategoriaRepositorio;

@Service
public class CategoriaServicio {
    @Autowired
    private CategoriaRepositorio categoriaRepositorio;



    @Transactional
    public void crear(Categoria categoriaDto) {

        if (categoriaRepositorio.existsByNombre(categoriaDto.getNombre())){
            throw new IllegalArgumentException("Ya existe una categoria con ese nombre");
        }
        Categoria categoria = new Categoria();
        categoria.setNombre(categoriaDto.getNombre());
        categoria.setDescripcion(categoriaDto.getNombre());
        
        categoriaRepositorio.save(categoria);
    }

    @Transactional
    public void actualizar(Categoria categoriaDto) {
        Categoria categoria = categoriaRepositorio.findById(categoriaDto.getId()).get();
        categoria.setNombre(categoriaDto.getNombre());
        categoria.setDescripcion(categoriaDto.getCategoria());
        
        categoriaRepositorio.save(categoria);
    }

    @Transactional(readOnly = true)
    public Categoria obtenerPorId(Integer id) {
        return categoriaRepositorio.findById(id).get();
    }

    @Transactional(readOnly = true)
    public List<Categoria> obtenerTodos() {
        return categoriaRepositorio.findAll();
    }

    @Transactional
    public void eliminarPorId(Integer id) throws Exception {
        if(categoriaRepositorio.referenciasEnArticulo(id)>0 || categoriaRepositorio.referenciasEnEmprenimiento(id)>0){
            throw new Exception("No se puede eliminar porque hay registros asociados.");
        }
        categoriaRepositorio.deleteById(id);
    }
}
