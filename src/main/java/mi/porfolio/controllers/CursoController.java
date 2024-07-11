package mi.porfolio.controllers;

import mi.porfolio.entities.Curso;
import mi.porfolio.services.curso.I_CursoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/cursos")
@CrossOrigin(origins = "https://porfolio-33a11.web.app")
public class CursoController {

    @Autowired
    private I_CursoService service;

    @GetMapping("/lista")
    public List<Curso> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Curso getById(@PathVariable String id) {
        return service.getById(Integer.parseInt(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("delete/{id}")
    public void remove(@PathVariable String id) {
        service.remove(Integer.parseInt(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public void save(@RequestBody Curso curso) {
        service.save(curso);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update")
    public void update(@RequestBody Curso curso) {
        service.save(curso);
    }
}