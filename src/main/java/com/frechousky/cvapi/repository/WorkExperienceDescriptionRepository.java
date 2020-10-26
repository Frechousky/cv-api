package com.frechousky.cvapi.repository;

import com.frechousky.cvapi.model.WorkExperience;
import com.frechousky.cvapi.model.WorkExperienceDescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkExperienceDescriptionRepository extends JpaRepository<WorkExperienceDescription, Integer> {
}
