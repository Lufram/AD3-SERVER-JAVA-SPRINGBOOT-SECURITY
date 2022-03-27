package com.sistem.blog.services;

import com.sistem.blog.DTO.PublicationDTO;
import com.sistem.blog.exceptions.ResourceNotFoundException;
import com.sistem.blog.model.Publication;
import com.sistem.blog.repository.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicationServiceImpl implements PublicationService{

    @Autowired
    private PublicationRepository publicationRepository;
    @Override
    public PublicationDTO addPublication(PublicationDTO publicationDTO ){
        Publication publication = mapEntity(publicationDTO);
        Publication newPublication = publicationRepository.save(publication);
        PublicationDTO publicationResponse = mapDTO(newPublication);
        return publicationResponse;
    }

    @Override
    public List<PublicationDTO> getAllPublications(){
        List<Publication> publications = publicationRepository.findAll();
        return publications.stream().map(publication -> mapDTO(publication)).collect(Collectors.toList());
    }

    @Override
    public PublicationDTO getPublicationById(long id){
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));
        return mapDTO(publication);
    }

    @Override
    public PublicationDTO updatePublication(PublicationDTO publicationDTO, long id){
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publication","id",id));
        publication.setTitle(publicationDTO.getTitle());
        publication.setDescription(publicationDTO.getDescription());
        publication.setContent(publicationDTO.getContent());
        Publication updatedPublication = publicationRepository.save(publication);
        return mapDTO(updatedPublication);
    }

    @Override
    public void deletePublication(long id){
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publication","id",id));
        publicationRepository.delete(publication);
    }

    // Convertimos entidad a DTO
    private PublicationDTO mapDTO(Publication publication){
        PublicationDTO publicationDTO = new PublicationDTO();
        publicationDTO.setId(publication.getId());
        publicationDTO.setTitle(publication.getTitle());
        publicationDTO.setDescription(publication.getDescription());
        publicationDTO.setContent(publication.getContent());
        return publicationDTO;
    }

    // Convertimos entidad a DTO
    private Publication mapEntity(PublicationDTO publicationDTO){
        Publication publication = new Publication();
        publication.setId(publicationDTO.getId());
        publication.setTitle(publicationDTO.getTitle());
        publication.setDescription(publicationDTO.getDescription());
        publication.setContent(publicationDTO.getContent());
        return publication;
    }
}