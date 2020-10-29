package com.frechousky.cvapi.repository;

import com.frechousky.cvapi.model.Project;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProjectRestRepository extends PagingAndSortingRepository<Project, Integer> {
}
