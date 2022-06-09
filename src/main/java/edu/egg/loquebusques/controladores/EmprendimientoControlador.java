package edu.egg.loquebusques.controladores;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import edu.egg.loquebusques.dto.EmprendimientoDTO;
import edu.egg.loquebusques.entidades.Domicilio;
import edu.egg.loquebusques.entidades.Emprendimiento;
import edu.egg.loquebusques.entidades.Localidad;
import edu.egg.loquebusques.servicios.CategoriaServicio;
import edu.egg.loquebusques.servicios.DomicilioServicio;
import edu.egg.loquebusques.servicios.EmprendimientoServicio;

@Controller
@RequestMapping("/emprendimientos")
public class EmprendimientoControlador {
    
    @Autowired
    private EmprendimientoServicio emprendimientoServicio;
    @Autowired
    private DomicilioServicio domicilioServicio;
    @Autowired
    private CategoriaServicio categoriaServicio;

    @GetMapping
    public ModelAndView obtenerEmprendimientos(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("");                    //nombre de la vista
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) {
            if(inputFlashMap.containsKey("exito")){
                mav.addObject("exito", inputFlashMap.get("exito"));
            }
            if(inputFlashMap.containsKey("error")){
                mav.addObject("error", inputFlashMap.get("error"));
            }
        }
        mav.addObject("emprendimientos", emprendimientoServicio.obtenerTodos());

        return mav;
    }

    @GetMapping("/formulario")
    public ModelAndView obtenerFormulario(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("");                //nombre de la vista
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null){
            mav.addObject("emprendimientoDTO", inputFlashMap.get("emprendimientoDTO"));
            mav.addObject("error", inputFlashMap.get("error"));
        }else{
            mav.addObject("emprendimientoDTO", new EmprendimientoDTO());
        }
        mav.addObject("categorias", categoriaServicio.obtenerTodos());
        mav.addObject("Localidad", Localidad.values());
        mav.addObject("action", "crear");
        return mav;        
    }

    @PostMapping("/crear")
    public RedirectView crear(EmprendimientoDTO emprendimientoDTO, RedirectAttributes atributos,@RequestParam(required = false) MultipartFile foto) { // 
        RedirectView redireccion = new RedirectView("/emprendimientos");

        //creo el objeto domicilio
        Domicilio domicilio = new Domicilio();
        domicilio.setLocalidad(emprendimientoDTO.getLocalidad());
        domicilio.setCalle(emprendimientoDTO.getCalle());
        domicilio.setNumero(emprendimientoDTO.getNumero());
        domicilio.setCodPostal(emprendimientoDTO.getCodPostal());
        domicilio.setReferencia(emprendimientoDTO.getReferencia());

        //creo el objeto emprendimiento
        Emprendimiento emprendimiento = new Emprendimiento();
        emprendimiento.setNombre(emprendimientoDTO.getNombre());
        emprendimiento.setDescripcion(emprendimientoDTO.getDescripcion());
        emprendimiento.setTelefono(emprendimientoDTO.getTelefono());
        emprendimiento.setHorario(emprendimientoDTO.getHorario());
        emprendimiento.setFormasPago(emprendimientoDTO.getFormasPago());
        emprendimiento.setInicioActividades(emprendimientoDTO.getInicioActividades());
        emprendimiento.setCategorias(emprendimientoDTO.getCategorias());
        

        try {
            emprendimientoServicio.crear(emprendimiento, foto);
            atributos.addFlashAttribute("exito", "El emprendimiento se ha almacenado");
        } catch (IllegalArgumentException e) {
            atributos.addFlashAttribute("emprendimientoDTO", emprendimientoDTO);
            atributos.addFlashAttribute("error", e.getMessage());
            redireccion.setUrl("/emprendimientos/formulario");
        }
        
        return redireccion;
    }

    @GetMapping("/formulario/{id}")
    public ModelAndView obtenerFormularioActualizar(@PathVariable Integer id, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("");                                                  //nombre formulario
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null){
            mav.addObject("emprendimientoDTO", inputFlashMap.get("emprendimientoDTO"));
            mav.addObject("error", inputFlashMap.get("error"));
        }else{
            EmprendimientoDTO emprendimientoDTO = new EmprendimientoDTO();
            Emprendimiento emprendimiento = emprendimientoServicio.obtenerPorId(id);
            
            Domicilio domicilio = emprendimiento.getDomicilio();
            emprendimientoDTO.setEmprendimientoId(emprendimiento.getId());
            emprendimientoDTO.setNombre(emprendimiento.getNombre());
            emprendimientoDTO.setDescripcion(emprendimiento.getDescripcion());
            emprendimientoDTO.setTelefono(emprendimiento.getTelefono());
            emprendimientoDTO.setHorario(emprendimiento.getHorario());
            emprendimientoDTO.setFormasPago(emprendimiento.getFormasPago());
            emprendimientoDTO.setInicioActividades(emprendimientoDTO.getInicioActividades());

            emprendimientoDTO.setDomicilioId(domicilio.getId());
            emprendimientoDTO.setLocalidad(domicilio.getLocalidad());
            emprendimientoDTO.setCalle(domicilio.getCalle());
            emprendimientoDTO.setNumero(domicilio.getNumero());
            emprendimientoDTO.setCodPostal(domicilio.getCodPostal());
            emprendimientoDTO.setReferencia(domicilio.getReferencia());

            mav.addObject("emprendimientoDTO", emprendimientoDTO);
        }
        mav.addObject("categorias", categoriaServicio.obtenerTodos());
        mav.addObject("Localidad", Localidad.values());
        mav.addObject("action", "actualizar");
        return mav;
    }

    @PostMapping("/actualizar")
    public RedirectView actualizar(EmprendimientoDTO emprendimientoDTO, RedirectAttributes atributos, @RequestParam(required = false) MultipartFile foto) {
        RedirectView redireccion = new RedirectView("/emprendimientos");
        //creo el objeto domicilio
        Domicilio domicilio = domicilioServicio.obtenerPorId(emprendimientoDTO.getDomicilioId());
        domicilio.setLocalidad(emprendimientoDTO.getLocalidad());
        domicilio.setCalle(emprendimientoDTO.getCalle());
        domicilio.setNumero(emprendimientoDTO.getNumero());
        domicilio.setCodPostal(emprendimientoDTO.getCodPostal());
        domicilio.setReferencia(emprendimientoDTO.getReferencia());

        //creo el objeto emprendimiento
        Emprendimiento emprendimiento = new Emprendimiento();
        emprendimiento.setNombre(emprendimientoDTO.getNombre());
        emprendimiento.setDescripcion(emprendimientoDTO.getDescripcion());
        emprendimiento.setTelefono(emprendimientoDTO.getTelefono());
        emprendimiento.setHorario(emprendimientoDTO.getHorario());
        emprendimiento.setFormasPago(emprendimientoDTO.getFormasPago());
        emprendimiento.setInicioActividades(emprendimientoDTO.getInicioActividades());
        emprendimiento.setCategorias(emprendimientoDTO.getCategorias());
        
        try {
            emprendimientoServicio.actualizar(emprendimiento, foto);
            atributos.addFlashAttribute("exito", "El emprendimiento se ha modificado");
        } catch (IllegalArgumentException e) {
            atributos.addFlashAttribute("emprendimientoDTO", emprendimientoDTO);
            atributos.addFlashAttribute("error", e.getMessage());
            redireccion.setUrl("/emprendimientos/formulario");
        }
        return redireccion;
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable Integer id, RedirectAttributes atributos) {
        RedirectView redireccion = new RedirectView("/emprendimientos");
        emprendimientoServicio.eliminarPorId(id);
        atributos.addFlashAttribute("exito", "Se ha eliminado el emprendimiento");
        return redireccion;
    }

    //ver un emprendimiento
    @GetMapping("/ver/{id}")
    public ModelAndView verEmprendimiento(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("");                //nombre vista
        mav.addObject("emprendimiento", emprendimientoServicio.obtenerPorId(id));
        return mav;
    }
}