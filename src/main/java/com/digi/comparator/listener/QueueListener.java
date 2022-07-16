package com.digi.comparator.listener;

import com.digi.comparator.config.MqConfig;
import com.digi.comparator.domain.CsvFile;
import com.digi.comparator.domain.CustomMessage;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONObject;
import org.json.JSONString;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;

@Component
public class QueueListener {


  @RabbitListener(queues = MqConfig.QUEUE)
  public void listener(CustomMessage message){
    System.out.println(message);

  }
}
