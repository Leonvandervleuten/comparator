package com.digi.comparator.rest;

import com.digi.comparator.config.MqConfig;
import com.digi.comparator.domain.CustomMessage;
import com.digi.comparator.domain.SearchElements;
import com.digi.comparator.service.CsvFileService;
import com.digi.comparator.service.SearchElementsService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@RestController
public class CsvFileController {

  @Autowired
  private CsvFileService csvFileService;

  @Autowired
  private SearchElementsService searchElementsService;

  @Autowired
  private RabbitTemplate template;

  //  @CrossOrigin
  //  @PostMapping("/compare")
  //  public String publishMessage(@RequestBody CustomMessage message) {
  //    message.setMessageId(UUID.randomUUID().toString());
  //    message.setMessageDate(new Date());
  //    message.setMessage(message.getMessage());
  //    template.convertAndSend(MqConfig.EXCHANGE,
  //        MqConfig.ROUTING_KEY, message);
  //
  //    return "Message Published";
  //  }

  @CrossOrigin
  @PostMapping("/compare")
  public void saveCsvToDB(@RequestParam("csv") MultipartFile csv) throws IOException {
    SearchElements searchElements = new SearchElements();
    List<String> stringList = searchElementsService.fromCsvToList(csv);
    searchElements.setList(stringList);

        template.convertAndSend(MqConfig.EXCHANGE,
            MqConfig.ROUTING_KEY, searchElements);

    //    if (!csv.isEmpty()) {
    //      Long csvId = csvFileService.addFileNameToH2DBAndReturnId(csv);
    //      searchElementsService.addSearchElementsToH2DB(csv, csvId);
    //    }
  }

  @CrossOrigin
  @GetMapping("/compare")
  public HashSet<SearchElements> compareFilesFromDb() {
    return searchElementsService.compareElementsFromDB();
  }

  @CrossOrigin
  @GetMapping("compare/delete")
  public void deleteDB() {
    searchElementsService.deleteAllElements();
    csvFileService.deleteAllCsvFiles();
  }

  @CrossOrigin
  @GetMapping("compare/all")
  public List<SearchElements> getAllElements() {
    return searchElementsService.findAllSearchElements();
  }

}
