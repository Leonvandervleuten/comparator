package com.digi.comparator.service;

import com.digi.comparator.domain.CsvFile;
import com.digi.comparator.domain.SearchElements;
import com.digi.comparator.repository.SearchElementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SearchElementsService {

  @Autowired
  private SearchElementsRepository searchElementsRepository;

  private List<String> fromCsvToList(MultipartFile csv) throws IOException {
    byte[] bytes = csv.getBytes();
    String csvString = new String(bytes);
    return new ArrayList<>(Arrays.asList(csvString.split(";")));
  }

  public void addSearchElementsToH2DB(MultipartFile csv, Long csvId) throws IOException {
    List<String> elementList = fromCsvToList(csv);
    CsvFile csvFile = new CsvFile();
    csvFile.setId(csvId);

    for (String element : elementList) {
      if (!element.isEmpty()) {
        SearchElements searchElements = new SearchElements();
        searchElements.setElement(element);
        searchElements.setCsvFile(csvFile);
        searchElementsRepository.save(searchElements);
      }
    }
  }

  public List<SearchElements> findAllSearchElements(){
    return searchElementsRepository.findAll();
  }

  public void getSearchElementsByCSVFileId(){
    List<SearchElements> listje = searchElementsRepository.getSearchElementsByCsvFile_Id(1L);
  }
}
