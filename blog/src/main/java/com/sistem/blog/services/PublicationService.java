package com.sistem.blog.services;

import com.sistem.blog.DTO.PublicationDTO;

import java.util.List;

public interface PublicationService {
    public PublicationDTO addPublication(PublicationDTO publicationDTO );
    public List<PublicationDTO> getAllPublications();
    public PublicationDTO getPublicationById(long id);
    public PublicationDTO updatePublication(PublicationDTO publicationDTO, long id);
    public void deletePublication(long id);
}