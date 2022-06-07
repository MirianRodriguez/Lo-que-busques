package edu.egg.loquebusques.controladores;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class CategoriaControlador {

    @Autowired
    private CategoriaServicio categoriaServicio;

    @GetMapping
    public ModelAndView obtenerCategoria(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("categoria/index.html");
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

    @GetMapping("/formulario")
    public ModelAndView obtenerFormulario(HttpServletRequest request) {

        ModelAndView mav = new ModelAndView("categoria/formulario.html");
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

    @PostMapping("/crear")
    public RedirectView crear(Categoria categoriaDto, RedirectAttributes atributos) {
        RedirectView redireccion = new RedirectView("/categorias");

        try {
            categoriaServicio.crear(categoriaDto);
            atributos.addFlashAttribute("exito", "El categoria se ha almacenado.");
        } catch (IllegalArgumentException e) {
            atributos.addFlashAttribute("categoria", categoriaDto);
            atributos.addFlashAttribute("error", e.getMessage());
            redireccion.setUrl("/categorias/formulario");
        }

        return redireccion;
    }

    @GetMapping("/formulario/{id}")
    public ModelAndView obtenerFormularioActualizar(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("/categoria/formulario");
        mav.addObject("categoria", categoriaServicio.obtenerPorId(id));
        mav.addObject("action", "actualizar"); /* ATENCION */
        return mav;
    }

    @PostMapping("/actualizar")
    public RedirectView atualizar(Categoria categoriaDto, RedirectAttributes atributos) {
        RedirectView redireccion = new RedirectView("/categorias");
        categoriaServicio.actualizar(categoriaDto);
        atributos.addFlashAttribute("exito", "Categoria modificado.");
        return redireccion;
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable Integer id, RedirectAttributes atributos) {
        RedirectView redireccion = new RedirectView("/categorias");
        try {
            categoriaServicio.eliminarPorId(id);
            atributos.addFlashAttribute("exito", "Categoria eliminado.");
        } catch (Exception e) {
            atributos.addFlashAttribute("error", e.getMessage());
        }
        return redireccion;
    }
}
