package edu.egg.loquebusques.controladores;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import edu.egg.loquebusques.dto.ArticuloDTO;
import edu.egg.loquebusques.entidades.Articulo;
import edu.egg.loquebusques.entidades.Demora;
import edu.egg.loquebusques.entidades.UnidadTiempo;
import edu.egg.loquebusques.servicios.ArticuloServicio;
import edu.egg.loquebusques.servicios.CategoriaServicio;
import edu.egg.loquebusques.servicios.DemoraServicio;

@Controller
@RequestMapping("/articulos")
public class ArticuloControlador {

    @Autowired
    private ArticuloServicio articuloServicio;
    @Autowired
    private DemoraServicio demoraServicio;
    @Autowired
    private CategoriaServicio categoriaServicio;

    @GetMapping
    public ModelAndView obtenerArticulos(HttpServletRequest request) {
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
        mav.addObject("articulos", articuloServicio.obtenerTodos());

        return mav;
    }

    @GetMapping("/formulario")
    public ModelAndView obtenerFormulario(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("");                //nombre de la vista
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null){
            mav.addObject("articuloDTO", inputFlashMap.get("articuloDTO"));
            mav.addObject("error", inputFlashMap.get("error"));
        }else{
            mav.addObject("articuloDTO", new ArticuloDTO());
        }
        mav.addObject("categorias", categoriaServicio.obtenerTodos());
        mav.addObject("unidadesTiempo", UnidadTiempo.values());
        mav.addObject("action", "crear");
        return mav;        
    }

    @PostMapping("/crear")
    public RedirectView crear(ArticuloDTO articuloDTO, RedirectAttributes atributos) { // @RequestParam(required = false) MultipartFile foto
        RedirectView redireccion = new RedirectView("/articulos");

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
        articulo.setDemora(demora);
        articulo.setCategoria(articuloDTO.getCategoria());
        articulo.setEmprendimiento(articuloDTO.getEmprendimiento()); //Cómo capturar el emprendimiento que está cargando el articulo?

        try {
            demoraServicio.crear(demora);
            articuloServicio.crear(articulo);
            atributos.addFlashAttribute("exito", "El articulo se ha almacenado");
        } catch (IllegalArgumentException e) {
            atributos.addFlashAttribute("articuloDTO", articuloDTO);
            atributos.addFlashAttribute("error", e.getMessage());
            redireccion.setUrl("/articulos/formulario");
        }
        
        return redireccion;
    }

    @GetMapping("/formulario/{id}")
    public ModelAndView obtenerFormularioActualizar(@PathVariable Integer id, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("");            //nombre formulario
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
            articuloDTO.setDemoraId(demora.getId());
            articuloDTO.setCantidad(demora.getCantidad());
            articuloDTO.setUnidadTiempo(demora.getUnidadTiempo());

            mav.addObject("articuloDTO", articuloDTO);
        }
        mav.addObject("categorias", categoriaServicio.obtenerTodos());
        mav.addObject("unidadesTiempo", UnidadTiempo.values());
        mav.addObject("action", "actualizar");
        return mav;
    }

    @PostMapping("/actualizar")
    public RedirectView actualizar(ArticuloDTO articuloDTO, RedirectAttributes atributos) {
        RedirectView redireccion = new RedirectView("/articulos");
        //creo el objeto demora
        Demora demora = demoraServicio.obtenerPorId(articuloDTO.getDemoraId());
        demora.setCantidad(articuloDTO.getCantidad());
        demora.setUnidadTiempo(articuloDTO.getUnidadTiempo());
        //creo el objeto articulo
        Articulo articulo = articuloServicio.obtenerPorId(articuloDTO.getArticuloId());
        articulo.setNombre(articuloDTO.getNombre());
        articulo.setDescripcion(articuloDTO.getDescripcion());
        articulo.setPrecio(articuloDTO.getPrecio());
        articulo.setEnvioADomicilio(articuloDTO.getEnvioADomicilio());
        articulo.setDemora(demora);
        articulo.setCategoria(articuloDTO.getCategoria());
        articulo.setEmprendimiento(articuloDTO.getEmprendimiento());
        
        try {
            demoraServicio.actualizar(demora);
            articuloServicio.actualizar(articulo);
            atributos.addFlashAttribute("exito", "El articulo se ha modificado");
        } catch (IllegalArgumentException e) {
            atributos.addFlashAttribute("articuloDTO", articuloDTO);
            atributos.addFlashAttribute("error", e.getMessage());
            redireccion.setUrl("/articulos/formulario");
        }
        return redireccion;
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable Integer id, RedirectAttributes atributos) {
        RedirectView redireccion = new RedirectView("/articulos");
        articuloServicio.eliminarPorId(id);
        atributos.addFlashAttribute("exito", "Se ha eliminado el libro");
        return redireccion;
    }

    //ver un articulo
    @GetMapping("/ver/{id}")
    public ModelAndView verArticulo(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("");                //nombre vista
        mav.addObject("articulo", articuloServicio.obtenerPorId(id));
        return mav;
    }
}
