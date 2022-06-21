package edu.egg.loquebusques.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.egg.loquebusques.servicios.ArticuloServicio;
import edu.egg.loquebusques.servicios.CategoriaServicio;
import edu.egg.loquebusques.servicios.EmprendimientoServicio;

@Controller
public class PrincipalControlador {
    @Autowired
    private ArticuloServicio articuloServicio;
    

    @GetMapping
    public ModelAndView getIndex() {
        ModelAndView mav = new ModelAndView("home");
        mav.addObject("articulos", articuloServicio.obtenerMasRecientes());
        return mav;
    }
}
