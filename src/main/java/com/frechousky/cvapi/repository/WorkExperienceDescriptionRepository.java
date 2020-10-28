package com.frechousky.cvapi.repository;

import com.frechousky.cvapi.model.WorkExperienceDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkExperienceDescriptionRepository extends JpaRepository<WorkExperienceDescription, Integer> {
}
