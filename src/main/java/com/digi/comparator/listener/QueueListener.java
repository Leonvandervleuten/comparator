package com.digi.comparator.listener;

import com.digi.comparator.config.MqConfig;
import com.digi.comparator.domain.SearchElements;
import com.digi.comparator.repository.SearchElementsRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class QueueListener {

  @Autowired
  SearchElementsRepository searchElementsRepository;

  @RabbitListener(queues = MqConfig.QUEUE)
  public void listener(SearchElements message) {

    List<String> elementList = message.getList();

    for (String element : elementList) {
      SearchElements searchElements = new SearchElements();
      searchElements.setElement(element);
      searchElementsRepository.save(searchElements);
    }
  }
}
