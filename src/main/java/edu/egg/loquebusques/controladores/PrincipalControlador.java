package edu.egg.loquebusques.controladores;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import edu.egg.loquebusques.entidades.Rol;
import edu.egg.loquebusques.entidades.Usuario;
import edu.egg.loquebusques.servicios.ArticuloServicio;
import edu.egg.loquebusques.servicios.UsuarioServicio;

@Controller
public class PrincipalControlador {
    @Autowired
    private ArticuloServicio articuloServicio;

    @GetMapping
    public ModelAndView getIndex(HttpSession session, Principal principal) {
        if(principal != null){
            if (session.getAttribute("rol").equals(Rol.EMPRENDEDOR)){
                Integer usuarioId = (Integer) session.getAttribute("id");
                return new ModelAndView(new RedirectView("emprendimientos/ver/"+usuarioId));
            }
        }
        ModelAndView mav = new ModelAndView("home");
        mav.addObject("articulos", articuloServicio.obtenerMasRecientes());
        return mav;
    }

    @GetMapping("/quienes-somos")
    public ModelAndView quienesSomos(){
        ModelAndView mav = new ModelAndView("quienes-somos");
        return mav;
    }
}
