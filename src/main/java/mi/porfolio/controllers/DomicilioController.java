package mi.porfolio.controllers;

import mi.porfolio.entities.Domicilio;
import mi.porfolio.services.domicilio.I_DomicilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/domicilios")
@CrossOrigin(origins = "https://porfolio-33a11.web.app")
public class DomicilioController {

    @Autowired
    I_DomicilioService service;

    @GetMapping("/lista")
    public List<Domicilio> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Domicilio getById(@PathVariable String id) {
        return service.getById(Integer.parseInt(id));
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void remove(@PathVariable String id) {
        service.remove(Integer.parseInt(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public void save(@RequestBody Domicilio domicilio) {
        service.save(domicilio);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update")
    public void update(@RequestBody Domicilio domicilio) {
        service.save(domicilio);
    }
}