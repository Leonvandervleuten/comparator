package com.digi.comparator.service;

import com.digi.comparator.domain.CsvFile;
import com.digi.comparator.domain.SearchElements;
import com.digi.comparator.repository.CsvFileRepository;
import com.digi.comparator.repository.SearchElementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SearchElementsService {

  @Autowired
  private SearchElementsRepository searchElementsRepository;

  @Autowired
  private CsvFileRepository csvFileRepository;

  private HashSet<SearchElements> matchList = new HashSet<>();

  private List<String> fromCsvToList(MultipartFile csv) throws IOException {
    byte[] bytes = csv.getBytes();
    String csvString = new String(bytes);
    String adjustedCsvString = csvString.replaceAll("\r\n", ";");
    return new ArrayList<>(Arrays.asList(adjustedCsvString.split(";")));
  }

  public void addSearchElementsToH2DB(MultipartFile csv, Long csvId) throws IOException {
    List<String> elementList = fromCsvToList(csv);
    CsvFile csvFile = new CsvFile();
    csvFile.setId(csvId);

    for (String element : elementList) {
      if (!element.isEmpty()) {
        SearchElements testje = searchElementsRepository.findByElement(element);
        if (testje != null) {
          Set<CsvFile> csvFileList = new HashSet<>();
          for (CsvFile existedCsv : testje.getCsvFile()){
            csvFileList.add(existedCsv);
          }
          csvFileList.add(csvFile);
          testje.setCsvFile(csvFileList);
          searchElementsRepository.save(testje);
        }
        else {
          SearchElements searchElements = new SearchElements();
          Set<CsvFile> csvFileList = new HashSet<>();
          csvFileList.add(csvFile);
          searchElements.setElement(element);
          searchElements.setCsvFile(csvFileList);
          searchElementsRepository.save(searchElements);
        }

      }
    }
  }

  public List<SearchElements> findAllSearchElements() {
    return searchElementsRepository.findAll();
  }

  public HashSet<SearchElements> compareElementsFromDB() {
    int totalCsvFiles = csvFileRepository.findAll().size();
    for (int i = 1; i <= totalCsvFiles; i++) {
      List<SearchElements> searchElementsHead = getSearchElementsByCSVFileId((long) i);

      for (int y = 1; y <= totalCsvFiles; y++) {
        if (y != i) {
          List<SearchElements> searchElementsSlave = getSearchElementsByCSVFileId((long) y);

          for (SearchElements headElement : searchElementsHead) {
            for (SearchElements slaveElement : searchElementsSlave) {
              if (headElement.getElement().contains(slaveElement.getElement())) {
                matchMaker(slaveElement);
              }
            }
          }
        }
      }
    }
    return matchList;
  }

  private List<SearchElements> getSearchElementsByCSVFileId(Long id) {
    return searchElementsRepository.getSearchElementsByCsvFile_Id(id);
  }

  private void matchMaker(SearchElements element) {
    if (!(element.getElement().length() < 4 || element.getElement().length() > 25)) {
      matchList.add(element);
    }
  }

  public String deleteAllElements() {
    searchElementsRepository.deleteAll();
    return "All Search Elements Deleted";
  }
}