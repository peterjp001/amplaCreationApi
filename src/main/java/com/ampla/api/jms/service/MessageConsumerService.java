package com.ampla.api.jms.service;


import com.ampla.api.utilities.Constants;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumerService {

    @JmsListener(destination = Constants.QUEUE_NAME)
    public void Listener(String name){
        System.out.println("The name sent from the queue is:" + name);
    }
}
