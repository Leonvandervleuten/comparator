package com.digi.comparator.repository;

import com.digi.comparator.domain.CsvFile;
import com.digi.comparator.domain.SearchElements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SearchElementsRepository extends JpaRepository<SearchElements, Long> {

  List<SearchElements> getSearchElementsByCsvFile_Id(Long id);

  SearchElements findByElement(String element);
}
