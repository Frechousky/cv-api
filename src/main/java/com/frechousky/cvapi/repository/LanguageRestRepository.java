package com.frechousky.cvapi.repository;

import com.frechousky.cvapi.model.Language;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface LanguageRestRepository extends PagingAndSortingRepository<Language, Integer> {
}
