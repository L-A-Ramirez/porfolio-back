package mi.porfolio.controllers;

import mi.porfolio.entities.VistaEdad;
import mi.porfolio.services.edad.VistaEdadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/edad")
@CrossOrigin(origins = "https://porfolio-33a11.web.app")
public class VistaEdadController {
    @Autowired
    private VistaEdadService service;

    @GetMapping("/lista")
    public List<VistaEdad> getAll() {
        return service.getAll();
    }
}
