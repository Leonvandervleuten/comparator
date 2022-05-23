package com.digi.comparator.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CompareService {

  public void fromCsvToList(MultipartFile csv) throws IOException {
    byte[] bytes = csv.getBytes();
    String csvString = new String(bytes);
    List<String> elementList = new ArrayList<String>(Arrays.asList(csvString.split(";")));
    findDuplicatesFromList(elementList);
  }

  public void findDuplicatesFromList(List<String> input) {
    for (int i = 0; i < input.size(); i++) {
      if (input.get(i).contains("316")) {
        System.out.println("aanwezig");
      }
    }
  }
}

