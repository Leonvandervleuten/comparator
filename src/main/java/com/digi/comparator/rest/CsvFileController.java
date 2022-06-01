package com.digi.comparator.rest;

import com.digi.comparator.domain.SearchElements;
import com.digi.comparator.service.CsvFileService;
import com.digi.comparator.service.SearchElementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;

@RestController
public class CsvFileController {

  @Autowired
  private CsvFileService csvFileService;

  @Autowired
  private SearchElementsService searchElementsService;

  @PostMapping("/compare")
  public void saveCsvToDB(@RequestParam("csv") MultipartFile csv) throws IOException {
    if (!csv.isEmpty()) {
      Long csvId = csvFileService.addFileNameToH2DBAndReturnId(csv);
      searchElementsService.addSearchElementsToH2DB(csv, csvId);
    }
  }

  @GetMapping("/compare")
  public HashSet<SearchElements> compareFilesFromDb() {
    return searchElementsService.compareElementsFromDB();
  }

  @GetMapping("compare/delete")
  public void deleteDB(){
    searchElementsService.deleteAllElements();
    csvFileService.deleteAllCsvFiles();
  }

}
