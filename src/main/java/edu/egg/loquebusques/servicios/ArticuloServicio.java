package edu.egg.loquebusques.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.egg.loquebusques.entidades.Articulo;
import edu.egg.loquebusques.repositorios.ArticuloRepositorio;
import edu.egg.loquebusques.repositorios.DemoraRepositorio;

@Service
public class ArticuloServicio {
    
    @Autowired
    private ArticuloRepositorio articuloRepositorio;

    @Autowired
    private DemoraRepositorio demoraRepositorio;

    @Transactional
    public void crear(Articulo articuloDto) { //MultipartFile foto
        // if (articuloRepositorio.existsByNombre(articuloDto.getNombre()))
        //     throw new IllegalArgumentException("Ya existe un articulo con ese nombre"); 

        Articulo articulo = new Articulo();

        articulo.setNombre(articuloDto.getNombre());
        articulo.setDescripcion(articuloDto.getDescripcion());
        articulo.setPrecio(articuloDto.getPrecio());
        articulo.setEnvioADomicilio(articuloDto.getEnvioADomicilio());
        if(articuloDto.getDemora()!=null){
            articulo.setDemora(articuloDto.getDemora());
        }
        articulo.setCategoria(articuloDto.getCategoria());
        articulo.setEmprendimiento(articuloDto.getEmprendimiento());
        

        // if (!foto.isEmpty()){
        //     articulo.setImagen(imagenServicio.copy(foto));
        // }
        
        articuloRepositorio.save(articulo);
    }

    @Transactional
    public void actualizar(Articulo articuloDto) { //MultipartFile foto
        Articulo articulo = articuloRepositorio.findById(articuloDto.getId()).get();

        articulo.setNombre(articuloDto.getNombre());
        articulo.setDescripcion(articuloDto.getDescripcion());
        articulo.setPrecio(articuloDto.getPrecio());
        articulo.setEnvioADomicilio(articuloDto.getEnvioADomicilio());
        if(articuloDto.getDemora()!=null){
            articulo.setDemora(articuloDto.getDemora());
        }
        articulo.setCategoria(articuloDto.getCategoria());
        articulo.setEmprendimiento(articuloDto.getEmprendimiento());
        
        // if(!foto.isEmpty()){
        //     articulo.setImagen(imagenServicio.copy(foto));
        // }
        
        articuloRepositorio.save(articulo);
    }

    @Transactional(readOnly = true)
    public Articulo obtenerPorId(Integer id) {
        return articuloRepositorio.findById(id).get();        
    }

    @Transactional(readOnly = true)
    public List<Articulo> obtenerTodos() {
        return articuloRepositorio.findAll();
    }

    @Transactional
    public void eliminarPorId(Integer id) {
        Articulo articulo = articuloRepositorio.findById(id).get();
        demoraRepositorio.deleteById(articulo.getDemora().getId());
        articuloRepositorio.deleteById(id);
    }

}
