package com.digi.comparator.repository;

import com.digi.comparator.domain.SearchElements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchElementsRepository extends JpaRepository<SearchElements, Long> {

  List<SearchElements> getSearchElementsByCsvFile_Id(Long id);
}