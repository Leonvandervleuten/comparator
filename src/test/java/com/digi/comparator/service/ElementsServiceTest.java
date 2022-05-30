package com.digi.comparator.service;

import com.digi.comparator.domain.SearchElements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@SpringBootTest
class ElementsServiceTest {

  @Autowired
  private ElementsService elementsService;

  @Test
  void addSearchElementsToH2DB() throws IOException {
    //Arrange
    FileInputStream inputFile = new FileInputStream("src/test/resources/TestCSV.csv");
    MockMultipartFile file = new MockMultipartFile("csv", "TestCSV.csv", "text/csv", inputFile);
    List<SearchElements> beforeAddingAllSearchElements = elementsService.findAllSearchElements();

    //Act
    elementsService.addSearchElementsToH2DB(file, 1L);
    List<SearchElements> afterAddingAllSearchElements = elementsService.findAllSearchElements();

    //Assert
    Assertions.assertEquals(afterAddingAllSearchElements.size(), beforeAddingAllSearchElements.size() + 61);

  }


}