package com.digi.comparator.service;

import com.digi.comparator.domain.CsvFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;


@SpringBootTest
class CsvFileServiceTest {

  @Autowired
  private CsvFileService csvFileService;

  @Test
  public void addFileNameToH2DBAndReturnId() throws IOException {
    //Arrange
    FileInputStream inputFile = new FileInputStream("src/test/resources/TestCSV.csv");
    MockMultipartFile file = new MockMultipartFile("csv", "TestCSV.csv", "text/csv", inputFile);
    List<CsvFile> listBeforeAdding = csvFileService.findAllCsvFiles();

    //Act
    Long id = csvFileService.addFileNameToH2DBAndReturnId(file);

    //Assert
    Assertions.assertEquals(id, listBeforeAdding.size() + 1);
  }
}