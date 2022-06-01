package com.digi.comparator.service;

import com.digi.comparator.domain.CsvFile;
import com.digi.comparator.domain.SearchElements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@SpringBootTest
class SearchElementsServiceTest {

  @Autowired
  private SearchElementsService searchElementsService;

  @Test
  void addSearchElementsToH2DB() throws IOException {
    //Arrange
    FileInputStream inputFile = new FileInputStream("src/test/resources/TestCSV.csv");
    MockMultipartFile file = new MockMultipartFile("csv", "TestCSV.csv", "text/csv", inputFile);
    List<SearchElements> beforeAddingAllSearchElements = searchElementsService.findAllSearchElements();

    //Act
    searchElementsService.addSearchElementsToH2DB(file, 1L);
    List<SearchElements> afterAddingAllSearchElements = searchElementsService.findAllSearchElements();

    //Assert
    Assertions.assertEquals(afterAddingAllSearchElements.size(), beforeAddingAllSearchElements.size() + 61);

  }

  @Test
  void compareElementsFromDB() {
    //Arrange
    SearchElements assertElement = new SearchElements();
    assertElement.setElement("0612345678");
    assertElement.setId(1L);
    CsvFile csvFile = new CsvFile();
    csvFile.setFileName("filenaam1.csv");
    csvFile.setId(1L);
    assertElement.setCsvFile(csvFile);

    //Act
    HashSet<SearchElements> matches = searchElementsService.compareElementsFromDB();
    ArrayList<SearchElements> arrayMatches = new ArrayList<>(matches);
    SearchElements elementResult = arrayMatches.get(0);

    //Assert
    Assertions.assertEquals(assertElement.getElement(), elementResult.getElement());

  }
}