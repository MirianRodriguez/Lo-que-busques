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

import edu.egg.loquebusques.dto.ArticuloDTO;
import edu.egg.loquebusques.entidades.Articulo;
import edu.egg.loquebusques.entidades.Demora;
import edu.egg.loquebusques.entidades.Emprendimiento;
import edu.egg.loquebusques.entidades.UnidadTiempo;
import edu.egg.loquebusques.servicios.ArticuloServicio;
import edu.egg.loquebusques.servicios.DemoraServicio;
import edu.egg.loquebusques.servicios.EmprendimientoServicio;

@Controller
@RequestMapping("/articulos")
public class ArticuloControlador {

    @Autowired
    private ArticuloServicio articuloServicio;
    @Autowired
    private DemoraServicio demoraServicio;
    @Autowired
    private EmprendimientoServicio emprendimientoServicio;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ModelAndView obtenerArticulos(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("articulos/index.html");  
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) {
            if(inputFlashMap.containsKey("exito")){
                mav.addObject("exito", inputFlashMap.get("exito"));
            }
            if(inputFlashMap.containsKey("error")){
                mav.addObject("error", inputFlashMap.get("error"));
            }
        }
        mav.addObject("articulos", articuloServicio.obtenerTodos());
        return mav;
    }

    @PreAuthorize("hasRole('EMPRENDEDOR')")
    @PostMapping("/formulario")
    public ModelAndView obtenerFormulario(HttpServletRequest request, @RequestParam Integer usuarioId) {
        ModelAndView mav = new ModelAndView("articulos/formulario");               
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null){
            mav.addObject("articuloDTO", inputFlashMap.get("articuloDTO"));
            mav.addObject("error", inputFlashMap.get("error"));
        }else{
            mav.addObject("articuloDTO", new ArticuloDTO());
        }
        Emprendimiento emprendimiento = emprendimientoServicio.obtenerPorUsuario(usuarioId);
        mav.addObject("categorias", emprendimiento.getCategorias());
        mav.addObject("unidadesTiempo", UnidadTiempo.values());
        mav.addObject("action", "crear");
        return mav;        
    }

    @PreAuthorize("hasRole('EMPRENDEDOR')")
    @PostMapping("/crear")
    public RedirectView crear(ArticuloDTO articuloDTO, RedirectAttributes atributos, @RequestParam(required = false) MultipartFile foto, @RequestParam Integer usuarioId) {
        
        Emprendimiento emprendimiento = emprendimientoServicio.obtenerPorUsuario(usuarioId);
        RedirectView redireccion = new RedirectView("/articulos/emprendimiento/"+usuarioId);
        
        //creo el objeto demora
        Demora demora = new Demora();
        demora.setCantidad(articuloDTO.getCantidad());
        demora.setUnidadTiempo(articuloDTO.getUnidadTiempo());
        //creo el objeto articulo
        Articulo articulo = new Articulo();
        articulo.setNombre(articuloDTO.getNombre());
        articulo.setDescripcion(articuloDTO.getDescripcion());
        articulo.setPrecio(articuloDTO.getPrecio());
        articulo.setEnvioADomicilio(articuloDTO.getEnvioADomicilio());
        if(demora.getCantidad() != null && demora.getUnidadTiempo() != null){
            articulo.setDemora(demora);
        }
        articulo.setCategoria(articuloDTO.getCategoria());
        articulo.setEmprendimiento(emprendimiento);

        try {
            articuloServicio.crear(articulo, foto);
            atributos.addFlashAttribute("exito", "El artículo se ha almacenado");
        } catch (IllegalArgumentException e) {
            atributos.addFlashAttribute("articuloDTO", articuloDTO);
            atributos.addFlashAttribute("error", e.getMessage());
            redireccion.setUrl("/articulos/formulario");
        }
        
        return redireccion;
    }

    @PreAuthorize("hasRole('EMPRENDEDOR')")
    @PostMapping("/formulario/{id}")
    public ModelAndView obtenerFormularioActualizar(@PathVariable Integer id, HttpServletRequest request, @RequestParam Integer usuarioId) {
        ModelAndView mav = new ModelAndView("articulos/formulario");  
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null){
            mav.addObject("articuloDTO", inputFlashMap.get("articuloDTO"));
            mav.addObject("error", inputFlashMap.get("error"));
        }else{
            ArticuloDTO articuloDTO = new ArticuloDTO();
            Articulo articulo = articuloServicio.obtenerPorId(id);
            Demora demora = articulo.getDemora();
            articuloDTO.setArticuloId(articulo.getId());
            articuloDTO.setNombre(articulo.getNombre());
            articuloDTO.setDescripcion(articulo.getDescripcion());
            articuloDTO.setPrecio(articulo.getPrecio());
            articuloDTO.setEnvioADomicilio(articulo.getEnvioADomicilio());
            articuloDTO.setCategoria(articulo.getCategoria());
            articuloDTO.setEmprendimiento(articulo.getEmprendimiento());
            if(demora!=null){
                articuloDTO.setDemoraId(demora.getId());
                articuloDTO.setCantidad(demora.getCantidad());
                articuloDTO.setUnidadTiempo(demora.getUnidadTiempo());
            }

            mav.addObject("articuloDTO", articuloDTO);
        }
        Emprendimiento emprendimiento = emprendimientoServicio.obtenerPorUsuario(usuarioId);
        mav.addObject("categorias", emprendimiento.getCategorias());
        mav.addObject("unidadesTiempo", UnidadTiempo.values());
        mav.addObject("action", "actualizar");
        return mav;
    }

    @PreAuthorize("hasRole('EMPRENDEDOR')")
    @PostMapping("/actualizar")
    public RedirectView actualizar(ArticuloDTO articuloDTO, RedirectAttributes atributos, @RequestParam(required = false) MultipartFile foto, @RequestParam Integer usuarioId) {
        Emprendimiento emprendimiento = emprendimientoServicio.obtenerPorUsuario(usuarioId);
        RedirectView redireccion = new RedirectView("/articulos/emprendimiento/"+usuarioId);
        //creo el objeto demora
        Demora demora = new Demora();
        if(articuloDTO.getDemoraId()!=null){
            demora = demoraServicio.obtenerPorId(articuloDTO.getDemoraId());
        }
        demora.setCantidad(articuloDTO.getCantidad());
        demora.setUnidadTiempo(articuloDTO.getUnidadTiempo());
        //creo el objeto articulo
        Articulo articulo = articuloServicio.obtenerPorId(articuloDTO.getArticuloId());
        articulo.setNombre(articuloDTO.getNombre());
        articulo.setDescripcion(articuloDTO.getDescripcion());
        articulo.setPrecio(articuloDTO.getPrecio());
        articulo.setEnvioADomicilio(articuloDTO.getEnvioADomicilio());
        if(demora.getCantidad() != null && demora.getUnidadTiempo() != null){
            articulo.setDemora(demora);
        }else{ //verificar si antes tenia, si tenia eliminar la demora asociada al articulo
            if(articulo.getDemora()!= null){
                demoraServicio.eliminarPorId(articulo.getDemora().getId());
                articulo.setDemora(null);
            }
        }
        articulo.setCategoria(articuloDTO.getCategoria());
        articulo.setEmprendimiento(emprendimiento);
        
        try {
            articuloServicio.actualizar(articulo, foto);
            atributos.addFlashAttribute("exito", "El artículo se ha modificado");
        } catch (IllegalArgumentException e) {
            atributos.addFlashAttribute("articuloDTO", articuloDTO);
            atributos.addFlashAttribute("error", e.getMessage());
            redireccion.setUrl("/articulos/formulario");
        }
        return redireccion;
    }
    
    @PreAuthorize("hasRole('EMPRENDEDOR')")
    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable Integer id, RedirectAttributes atributos) {
        Articulo articulo = articuloServicio.obtenerPorId(id);
        Emprendimiento emprendimiento = articulo.getEmprendimiento();
        RedirectView redireccion = new RedirectView("/articulos/emprendimiento/"+emprendimiento.getUsuario().getId());
        articuloServicio.eliminarPorId(id);
        atributos.addFlashAttribute("exito", "Se ha eliminado el artículo");
        return redireccion;
    }

    //ver un articulo
    //@PreAuthorize("hasAnyRole('USUARIO', 'EMPRENDEDOR')")
    @GetMapping("/ver/{id}")
    public ModelAndView verArticulo(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("articulos/un-articulo.html");
        mav.addObject("articulo", articuloServicio.obtenerPorId(id));
        return mav;
    }

    //ver un articulo
    @PreAuthorize("hasAnyRole('EMPRENDEDOR')")
    @GetMapping("/emprendimiento/{usuarioId}")
    public ModelAndView verArticulosDeUnEmprendimiento(@PathVariable Integer usuarioId, HttpServletRequest request){
        ModelAndView mav = new ModelAndView("articulos/index.html");  
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) {
            if(inputFlashMap.containsKey("exito")){
                mav.addObject("exito", inputFlashMap.get("exito"));
            }
            if(inputFlashMap.containsKey("error")){
                mav.addObject("error", inputFlashMap.get("error"));
            }
        }
        Emprendimiento emprendimiento = emprendimientoServicio.obtenerPorUsuario(usuarioId);
        mav.addObject("articulos", articuloServicio.articulosDeUnEmprendimiento(emprendimiento.getId()));
        return mav;
    }
    //ver articulo/os de una determinada categoria
    @GetMapping("/categoria/{id}")
    public ModelAndView verArticuloDeUnaCategoria(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("articulos/articulos-por-categoria.html");
        mav.addObject("articulos", articuloServicio.buscarPorCategoria(id));
        return mav;
    }
}
