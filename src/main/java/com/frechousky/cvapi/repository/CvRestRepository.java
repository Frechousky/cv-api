package com.frechousky.cvapi.repository;

import com.frechousky.cvapi.model.Cv;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CvRestRepository extends PagingAndSortingRepository<Cv, Integer> {
}
