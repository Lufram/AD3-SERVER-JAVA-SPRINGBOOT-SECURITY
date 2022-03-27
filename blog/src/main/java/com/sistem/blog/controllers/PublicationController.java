package com.sistem.blog.controllers;

import com.sistem.blog.DTO.PublicationDTO;
import com.sistem.blog.services.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publications")
public class PublicationController {

    @Autowired
    private PublicationService publicationService;

    @GetMapping
    public List<PublicationDTO> listPublications(){
        return publicationService. getAllPublications();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicationDTO>  getPublicationById(@PathVariable (name = "id") long id){
        return ResponseEntity.ok(publicationService.getPublicationById(id));
    }

    @PostMapping
    public ResponseEntity<PublicationDTO>  savePublication(@RequestBody PublicationDTO publicationDTO ){
        return new ResponseEntity<>(publicationService.addPublication(publicationDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicationDTO> updatePublication(@RequestBody PublicationDTO publicationDTO,
                                                            @PathVariable(name = "id") long id){
        PublicationDTO publicationResponse = publicationService.updatePublication(publicationDTO, id);
        return new ResponseEntity<>(publicationResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePublication(@PathVariable (name = "id") long id){
        publicationService.deletePublication(id);
        return new ResponseEntity<>("Publicacion eliminada con exito", HttpStatus.OK);
    }
}