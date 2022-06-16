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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import edu.egg.loquebusques.dto.EmprendimientoDTO;
import edu.egg.loquebusques.entidades.Domicilio;
import edu.egg.loquebusques.entidades.Emprendimiento;
import edu.egg.loquebusques.entidades.FormaPago;
import edu.egg.loquebusques.entidades.Localidad;
import edu.egg.loquebusques.entidades.Rol;
import edu.egg.loquebusques.entidades.Usuario;
import edu.egg.loquebusques.servicios.ArticuloServicio;
import edu.egg.loquebusques.servicios.CategoriaServicio;
import edu.egg.loquebusques.servicios.DomicilioServicio;
import edu.egg.loquebusques.servicios.EmprendimientoServicio;
import edu.egg.loquebusques.servicios.UsuarioServicio;

@Controller
@RequestMapping("/emprendimientos")
public class EmprendimientoControlador {
    
    @Autowired
    private EmprendimientoServicio emprendimientoServicio;
    @Autowired
    private DomicilioServicio domicilioServicio;
    @Autowired
    private CategoriaServicio categoriaServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private ArticuloServicio articuloServicio;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ModelAndView obtenerEmprendimientos(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("emprendimientos/index-admin.html");                    //nombre de la vista
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/alta-emprendimiento")
    public ModelAndView obtenerFormulario(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("emprendimientos/alta-emprendimiento");                
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null){
            mav.addObject("usuario", inputFlashMap.get("usuario"));
            mav.addObject("error", inputFlashMap.get("error"));
        }else{
            mav.addObject("usuario", new Usuario());
        }

        mav.addObject("action", "crear");
        return mav;        
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/crear")
    public RedirectView crear(Usuario usuario, RedirectAttributes atributos) { 
        RedirectView redireccion = new RedirectView("/emprendimientos");

        try {
            usuario.setRol(Rol.EMPRENDEDOR);
            usuarioServicio.crear(usuario);
            emprendimientoServicio.crear(usuarioServicio.obtenerPorEmail(usuario.getEmail()));
            atributos.addFlashAttribute("exito", "El emprendimiento se ha almacenado");
        } catch (IllegalArgumentException e) {
            atributos.addFlashAttribute("error", e.getMessage());
            atributos.addFlashAttribute("usuario", usuario);
            redireccion.setUrl("/emprendimientos/alta-emprendimiento");
        }
        
        return redireccion;
    }

    @PreAuthorize("hasRole('EMPRENDEDOR')")
    @GetMapping("/perfil/{id}")
    public ModelAndView obtenerFormularioActualizarPerfil(@PathVariable Integer id, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("emprendimientos/formulario");
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
            emprendimientoDTO.setInicioActividades(emprendimiento.getInicioActividades());
            emprendimientoDTO.setCategorias(emprendimiento.getCategorias());

            if(domicilio != null){

                emprendimientoDTO.setDomicilioId(domicilio.getId());
                emprendimientoDTO.setLocalidad(domicilio.getLocalidad());
                emprendimientoDTO.setCalle(domicilio.getCalle());
                emprendimientoDTO.setNumero(domicilio.getNumero());
                emprendimientoDTO.setCodPostal(domicilio.getCodPostal());
                emprendimientoDTO.setReferencia(domicilio.getReferencia());
            }

            mav.addObject("emprendimientoDTO", emprendimientoDTO);
        }
        mav.addObject("formasPagos", FormaPago.values());
        mav.addObject("categorias", categoriaServicio.obtenerTodos());
        mav.addObject("localidades", Localidad.values());
        mav.addObject("action", "actualizar-perfil");
        return mav;
    }

    @PreAuthorize("hasRole('EMPRENDEDOR')")
    @PostMapping("/actualizar-perfil")
    public RedirectView actualizarPerfil(EmprendimientoDTO emprendimientoDTO, RedirectAttributes atributos, @RequestParam(required = false) MultipartFile foto, @RequestParam Integer usuarioId) {
        
        
        RedirectView redireccion = new RedirectView("/emprendimientos/ver/"+usuarioId);
        //creo el objeto domicilio

        Domicilio domicilio = new Domicilio();
        if(emprendimientoDTO.getDomicilioId() != null){
            domicilio = domicilioServicio.obtenerPorId(emprendimientoDTO.getDomicilioId());
        }
            domicilio.setLocalidad(emprendimientoDTO.getLocalidad());
            domicilio.setCalle(emprendimientoDTO.getCalle());
            domicilio.setNumero(emprendimientoDTO.getNumero());
            domicilio.setCodPostal(emprendimientoDTO.getCodPostal());
            domicilio.setReferencia(emprendimientoDTO.getReferencia());

        //creo el objeto emprendimiento
        Emprendimiento emprendimiento = emprendimientoServicio.obtenerPorId(emprendimientoDTO.getEmprendimientoId());
        emprendimiento.setNombre(emprendimientoDTO.getNombre());
        emprendimiento.setDescripcion(emprendimientoDTO.getDescripcion());
        emprendimiento.setTelefono(emprendimientoDTO.getTelefono());
        emprendimiento.setHorario(emprendimientoDTO.getHorario());
        emprendimiento.setFormasPago(emprendimientoDTO.getFormasPago());
        emprendimiento.setInicioActividades(emprendimientoDTO.getInicioActividades());
        emprendimiento.setCategorias(emprendimientoDTO.getCategorias());
        
        if(domicilio.getLocalidad()!=null){
            emprendimiento.setDomicilio(domicilio);
        }
        
        try {
            emprendimientoServicio.actualizar(emprendimiento, foto);
            atributos.addFlashAttribute("exito", "El emprendimiento se ha modificado");
        } catch (IllegalArgumentException e) {
            atributos.addFlashAttribute("emprendimientoDTO", emprendimientoDTO);
            atributos.addFlashAttribute("error", e.getMessage());
            redireccion.setUrl("/emprendimientos/perfil/"+emprendimiento.getId());
        }
        return redireccion;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable Integer id, RedirectAttributes atributos) {
        RedirectView redireccion = new RedirectView("/emprendimientos");
        emprendimientoServicio.eliminarPorId(id);
        atributos.addFlashAttribute("exito", "Se ha eliminado el emprendimiento");
        return redireccion;
    }

    //ver un emprendimiento
    //@PreAuthorize("hasAnyRole('ADMIN', 'USUARIO', 'EMPRENDEDOR')")
    @GetMapping("/ver/{usuarioId}")
    public ModelAndView verEmprendimiento(@PathVariable Integer usuarioId){
        ModelAndView mav = new ModelAndView("emprendimientos/UnEmprendimiento.html");
        Emprendimiento emprendimiento = emprendimientoServicio.obtenerPorUsuario(usuarioId);
        mav.addObject("emprendimiento", emprendimientoServicio.obtenerPorId(emprendimiento.getId()));
        mav.addObject("articulos", articuloServicio.articulosDeUnEmprendimiento(emprendimiento.getId()));
        return mav;
    }

    //ver articulos del emprendimiento
    @PreAuthorize("hasAnyRole('EMPRENDEDOR', 'USUARIO')")
    @GetMapping("/ver-articulos/{id}")
    public ModelAndView verArticulos(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("articulos/index"); 
        mav.addObject("articulos", articuloServicio.articulosDeUnEmprendimiento(id));
        return mav;
    }

    @GetMapping("/activos")
    public ModelAndView obtenerEmprendimientosActivos(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("emprendimientos/index.html");                    //nombre de la vista
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) {
            if(inputFlashMap.containsKey("exito")){
                mav.addObject("exito", inputFlashMap.get("exito"));
            }
            if(inputFlashMap.containsKey("error")){
                mav.addObject("error", inputFlashMap.get("error"));
            }
        }
        mav.addObject("emprendimientos", emprendimientoServicio.obtenerTodosActivos());

        return mav;
    }
}