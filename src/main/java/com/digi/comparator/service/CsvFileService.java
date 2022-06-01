package com.digi.comparator.service;

import com.digi.comparator.domain.CsvFile;
import com.digi.comparator.repository.CsvFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CsvFileService {

  @Autowired
  private CsvFileRepository csvFileRepository;

  public Long addFileNameToH2DBAndReturnId(MultipartFile csv) {
    String fileName = csv.getOriginalFilename();
    CsvFile file = new CsvFile();
    file.setFileName(fileName);
    csvFileRepository.save(file);
    CsvFile rowFromDB = csvFileRepository.getCsvFileByFileName(fileName);
    return rowFromDB.getId();
  }

  public List<CsvFile> findAllCsvFiles(){
    return csvFileRepository.findAll();
  }

  public String deleteAllCsvFiles(){
    csvFileRepository.deleteAll();
    return "All CsvFiles deleted";
  }
}

