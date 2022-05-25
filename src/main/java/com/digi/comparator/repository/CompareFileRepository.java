package com.digi.comparator.repository;

import com.digi.comparator.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompareFileRepository extends JpaRepository<File, Long> {
}
