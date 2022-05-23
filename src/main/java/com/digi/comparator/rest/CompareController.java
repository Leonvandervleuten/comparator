package com.digi.comparator.rest;

import com.digi.comparator.service.CompareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


@RestController
public class CompareController {

  @Autowired
  private CompareService compareService;

  @PostMapping("/compare")
  public void compareCSVFile(@RequestParam("csv") MultipartFile csv) throws IOException {
    if (!csv.isEmpty()) {
      compareService.fromCsvToList(csv);
    }
  }
}
