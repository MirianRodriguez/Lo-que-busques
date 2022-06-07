package edu.egg.loquebusques.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.egg.loquebusques.entidades.Domicilio;
import edu.egg.loquebusques.repositorios.DomicilioRepositorio;
@Service
public class DomicilioServicio {
    
    @Autowired
    private DomicilioRepositorio domicilioRepositorio;

    @Transactional
    public void crear(Domicilio domicilioDTO) { 

        Domicilio domicilio = new Domicilio();

        domicilio.setCalle(domicilioDTO.getCalle());
        domicilio.setNumero(domicilioDTO.getNumero());
        domicilio.setCodPostal(domicilioDTO.getcodPostal());
        domicilio.setReferencia(domicilioDTO.getReferencia());
        
        domicilioRepositorio.save(domicilio);
    }

    @Transactional
    public void actualizar(Domicilio domicilioDto) { 
        Domicilio domicilio = domicilioRepositorio.findById(domicilioDto.getId()).get();

        domicilio.setCalle(domicilioDto.getCalle());
        domicilio.setNumero(domicilioDto.getNumero());
        domicilio.setCodPostal(domicilioDto.getcodPostal());
        domicilio.setReferencia(domicilioDto.getReferencia());
        
        
        domicilioRepositorio.save(domicilio);
    }

    @Transactional
    public void eliminarPorId(Integer id) {
        Domicilio domicilio = domicilioRepositorio.findById(id).get();
        demoraRepositorio.deleteById(articulo.getDemora().getId());
        articuloRepositorio.deleteById(id);
    }

}
