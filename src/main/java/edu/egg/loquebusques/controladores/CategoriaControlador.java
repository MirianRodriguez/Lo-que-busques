package edu.egg.loquebusques.controladores;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import edu.egg.loquebusques.entidades.Categoria;
import edu.egg.loquebusques.servicios.CategoriaServicio;

@Controller
@RequestMapping("/categorias")
public class CategoriaControlador {

    @Autowired
    private CategoriaServicio categoriaServicio;

    @GetMapping
    public ModelAndView obtenerCategoria(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("categorias/index.html");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) {
            if (inputFlashMap.containsKey("exito")) {
                mav.addObject("exito", inputFlashMap.get("exito"));
            }
            if (inputFlashMap.containsKey("error")) {
                mav.addObject("error", inputFlashMap.get("error"));
            }
        }
        mav.addObject("categorias", categoriaServicio.obtenerTodos());
        return mav;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/formulario")
    public ModelAndView obtenerFormulario(HttpServletRequest request) {

        ModelAndView mav = new ModelAndView("categorias/formulario.html");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap != null) {
            mav.addObject("categoria", inputFlashMap.get("categoria"));
            mav.addObject("error", inputFlashMap.get("error"));
        } else {
            mav.addObject("categoria", new Categoria());
        }
        mav.addObject("action", "crear");
        return mav;
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/crear")
    public RedirectView crear(Categoria categoria, RedirectAttributes atributos) {
        RedirectView redireccion = new RedirectView("/categorias");

        try {
            categoriaServicio.crear(categoria);
            atributos.addFlashAttribute("exito", "La categor??a se ha almacenado.");
        } catch (IllegalArgumentException e) {
            atributos.addFlashAttribute("categoria", categoria);
            atributos.addFlashAttribute("error", e.getMessage());
            redireccion.setUrl("/categorias/formulario");
        }

        return redireccion;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/formulario/{id}")
    public ModelAndView obtenerFormularioActualizar(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("/categorias/formulario");
        mav.addObject("categoria", categoriaServicio.obtenerPorId(id));
        mav.addObject("action", "actualizar");
        return mav;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/actualizar")
    public RedirectView atualizar(Categoria categoria, RedirectAttributes atributos) {
        RedirectView redireccion = new RedirectView("/categorias");

        try {
            categoriaServicio.actualizar(categoria);
            atributos.addFlashAttribute("exito", "La categor??a se ha modificado.");
        } catch (IllegalArgumentException e) {
            atributos.addFlashAttribute("categoria", categoria);
            atributos.addFlashAttribute("error", e.getMessage());
            redireccion.setUrl("/categorias/formulario");
        }

        return redireccion;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable Integer id, RedirectAttributes atributos) {
        RedirectView redireccion = new RedirectView("/categorias");
        try {
            categoriaServicio.eliminarPorId(id);
            atributos.addFlashAttribute("exito", "Ha eliminado la categor??a.");
        } catch (Exception e) {
            atributos.addFlashAttribute("error", e.getMessage());
        }
        return redireccion;
    }

    // ver un categoria
    @GetMapping("/ver/{id}")
    public ModelAndView verCategoria(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView(""); // nombre vista
        mav.addObject("categoria", categoriaServicio.obtenerPorId(id));
        return mav;
    }
}
