package com.frechousky.cvapi.repository;

import com.frechousky.cvapi.model.Company;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CompanyRestRepository extends PagingAndSortingRepository<Company, Integer> {
}
