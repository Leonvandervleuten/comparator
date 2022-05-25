package com.digi.comparator.rest;

import com.digi.comparator.service.CsvFileService;
import com.digi.comparator.service.ElementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class CsvFileController {

  @Autowired
  private CsvFileService csvFileService;

  @Autowired
  private ElementsService elementsService;

  @PostMapping("/compare")
  public void compareCSVFile(@RequestParam("csv") MultipartFile csv) throws IOException {
    if (!csv.isEmpty()) {
      Long csvId = csvFileService.addFileNameToH2DB(csv);
      elementsService.addSearchElementsToH2DB(csv, csvId);

    }
  }
}
