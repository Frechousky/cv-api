package com.frechousky.cvapi.repository;

import com.frechousky.cvapi.model.Study;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface StudyRestRepository extends PagingAndSortingRepository<Study, Integer> {
}
