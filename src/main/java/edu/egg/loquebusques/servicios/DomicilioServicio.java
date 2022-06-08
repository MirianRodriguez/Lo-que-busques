package edu.egg.loquebusques.servicios;


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
    public void crear(Domicilio domicilioDto) { 

        Domicilio domicilio = new Domicilio();
        
        domicilio.setLocalidad(domicilio.getLocalidad());
        domicilio.setCalle(domicilioDto.getCalle());
        domicilio.setNumero(domicilioDto.getNumero());
        domicilio.setCodPostal(domicilioDto.getCodPostal());
        domicilio.setReferencia(domicilioDto.getReferencia());
        

        domicilioRepositorio.save(domicilio);
    }

    @Transactional
    public void actualizar(Domicilio domicilioDto) { 
        Domicilio domicilio = domicilioRepositorio.findById(domicilioDto.getId()).get();

        domicilio.setLocalidad(domicilioDto.getLocalidad());
        domicilio.setCalle(domicilioDto.getCalle());
        domicilio.setNumero(domicilioDto.getNumero());
        domicilio.setCodPostal(domicilioDto.getCodPostal());
        domicilio.setReferencia(domicilioDto.getReferencia());
        
        
        domicilioRepositorio.save(domicilio);
    }

    @Transactional
    public void eliminarPorId(Integer id) {
        domicilioRepositorio.deleteById(id);
    }

}
