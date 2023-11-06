package ma.projet.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import ma.projet.entities.Role;
import ma.projet.services.RoleService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/roles")

public class RoleController {
    @Autowired
    private RoleService Roleservice;


    @GetMapping("")
    public List<Role> getAllRole() {
        return Roleservice.findAll();
    }


    @GetMapping("/{id}")
    public Role getById(@PathVariable Long id) {
        return Roleservice.findById(id);

    }

    @PostMapping("")
    public Role createRole(@RequestBody Role Role) {
        return Roleservice.create(Role);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRole(@PathVariable Long id, @RequestBody Role Role) {

        if (Roleservice.findById(id) == null) {
            return new ResponseEntity<Object>("Role avec ID " + id + " n exite pas", HttpStatus.BAD_REQUEST);
        } else {
            Roleservice.update(Role);
            return ResponseEntity.ok("UPDATE AVEC SUCCES");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        Role Role = Roleservice.findById(id);

        if (Role == null) {
            return new ResponseEntity<Object>("Role avec ID " + id + " n exite pas", HttpStatus.BAD_REQUEST);
        } else {
            Roleservice.delete(Role);
            return ResponseEntity.ok(" supression avec succes ");

        }
    }
}
