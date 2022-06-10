package edu.egg.loquebusques.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import edu.egg.loquebusques.entidades.Usuario;
import edu.egg.loquebusques.servicios.UsuarioServicio;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("/autenticacion")
public class AutenticacionControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/acceder")
    public ModelAndView acceso(@RequestParam(required = false) String error, @RequestParam(required = false) String cerrar, Principal principal) {
        ModelAndView mav = new ModelAndView("autenticacion/acceder");       

        if (error != null) mav.addObject("error", "Email o contraseña incorrecto");
        if (cerrar != null) mav.addObject("cerrar", "Se ha cerrado sesión");
        if (principal != null) mav.setViewName("redirect:/");

        return mav;
    }

    @GetMapping("/registro")
    public ModelAndView registro(HttpServletRequest request, Principal principal) {
        ModelAndView mav = new ModelAndView("autenticacion/registrar");         
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (principal != null) mav.setViewName("redirect:/");

        if (inputFlashMap != null) {
            mav.addObject("error", inputFlashMap.get("error"));
            mav.addObject("usuario", inputFlashMap.get("usuario"));
        } else {
            mav.addObject("usuario", new Usuario());
        }

        return mav;
    }

    @PostMapping("/registrar")
    public RedirectView registrar(Usuario usuarioDto, RedirectAttributes atributos, HttpServletRequest request) {
        RedirectView redirect = new RedirectView("/");

        try {
            usuarioServicio.crear(usuarioDto);
            request.login(usuarioDto.getEmail(), usuarioDto.getContrasenia());
        } catch (IllegalArgumentException e) {
            atributos.addFlashAttribute("usuario", usuarioDto);
            atributos.addFlashAttribute("error", e.getMessage());
            redirect.setUrl("/autenticacion/registro");
        } catch (ServletException e) {
            atributos.addFlashAttribute("error","Error en el autolog");
        }

        return redirect;
    }
}
