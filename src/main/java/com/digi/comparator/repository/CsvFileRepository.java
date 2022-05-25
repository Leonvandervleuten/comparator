package com.digi.comparator.repository;

import com.digi.comparator.domain.CsvFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CsvFileRepository extends JpaRepository<CsvFile, Long> {

  CsvFile getCsvFileByFileName(String s);
}
