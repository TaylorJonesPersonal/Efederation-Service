package com.efederation.Repository;

import com.efederation.Model.ImageSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageSetRepository extends JpaRepository<ImageSet, Long> {

}
