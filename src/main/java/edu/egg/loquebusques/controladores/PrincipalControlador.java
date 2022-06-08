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
    private EmprendimientoServicio emprendimientoServicio;
    @Autowired
    private ArticuloServicio articuloServicio;
    @Autowired
    private CategoriaServicio categoriaServicio;

    @GetMapping
    public ModelAndView getIndex() {
        ModelAndView mav = new ModelAndView("home");
        mav.addObject("emprendimiento", emprendimientoServicio.obtenerTodos());
        mav.addObject("articulos", articuloServicio.obtenerTodos());
        mav.addObject("articulosMasRecientes", articuloServicio.obtenerMasRecientes());
        mav.addObject("categorias", categoriaServicio.obtenerTodos());
        return mav;
    }
}
