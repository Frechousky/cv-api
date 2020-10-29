package com.frechousky.cvapi.repository;

import com.frechousky.cvapi.model.WorkExperience;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface WorkExperienceRestRepository extends PagingAndSortingRepository<WorkExperience, Integer> {
}
