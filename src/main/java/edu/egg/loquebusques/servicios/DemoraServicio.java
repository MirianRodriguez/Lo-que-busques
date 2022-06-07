package edu.egg.loquebusques.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.egg.loquebusques.entidades.Demora;
import edu.egg.loquebusques.repositorios.DemoraRepositorio;

@Service
public class DemoraServicio {
    @Autowired
    private DemoraRepositorio demoraRepositorio;

    @Transactional
    public void crear(Demora demoraDto) {

        Demora demora = new Demora();

        demora.setCantidad(demoraDto.getCantidad());
        demora.setUnidadTiempo(demoraDto.getUnidadTiempo());
        
        demoraRepositorio.save(demora);
    }

    @Transactional
    public void actualizar(Demora demoraDto) {
        Demora demora = demoraRepositorio.findById(demoraDto.getId()).get();

        demora.setCantidad(demoraDto.getCantidad());
        demora.setUnidadTiempo(demoraDto.getUnidadTiempo());
        
        demoraRepositorio.save(demora);
    }

    @Transactional(readOnly = true)
    public Demora obtenerPorId(Integer id) {
        return demoraRepositorio.findById(id).get();        
    }

    @Transactional(readOnly = true)
    public List<Demora> obtenerTodos() {
        return demoraRepositorio.findAll();
    }

    @Transactional
    public void eliminarPorId(Integer id) {
        demoraRepositorio.deleteById(id);
    }
}
