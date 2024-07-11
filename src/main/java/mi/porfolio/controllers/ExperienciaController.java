package mi.porfolio.controllers;

import mi.porfolio.entities.Experiencia;
import mi.porfolio.services.experiencia.I_ExperienciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/experiencias")
@CrossOrigin(origins = "https://porfolio-33a11.web.app")
public class ExperienciaController {

    @Autowired
    I_ExperienciaService service;

    @GetMapping("/lista")
    public List<Experiencia> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Experiencia getById(@PathVariable String id) {
        return service.getById(Integer.parseInt(id));
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void remove(@PathVariable String id) {
        service.remove(Integer.parseInt(id));
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public void save(@RequestBody Experiencia experiencia) {
        service.save(experiencia);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update")
    public void update(@RequestBody Experiencia experiencia) {
        service.save(experiencia);
    }

}