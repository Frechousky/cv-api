package com.frechousky.cvapi.repository;

import com.frechousky.cvapi.model.Skill;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SkillRestRepository extends PagingAndSortingRepository<Skill, Integer> {
}
