package com.frechousky.cvapi.repository;

import com.frechousky.cvapi.model.ContactInformation;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ContactInformationRestRepository extends PagingAndSortingRepository<ContactInformation, Integer> {
}
