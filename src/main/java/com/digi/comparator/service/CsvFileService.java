package com.digi.comparator.service;

import com.digi.comparator.domain.CsvFile;
import com.digi.comparator.repository.CsvFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CsvFileService {

  @Autowired
  private CsvFileRepository csvFileRepository;

  public Long addFileNameToH2DB(MultipartFile csv) {
    String fileName = csv.getOriginalFilename();
    CsvFile file = new CsvFile();
    file.setFileName(fileName);
    csvFileRepository.save(file);
    CsvFile rowFromDB = csvFileRepository.getCsvFileByFileName(fileName);
    return rowFromDB.getId();
  }
}
