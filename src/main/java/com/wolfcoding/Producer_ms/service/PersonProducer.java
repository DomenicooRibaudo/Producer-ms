package com.wolfcoding.Producer_ms.service;

import com.google.gson.Gson;
import com.wolfcoding.Producer_ms.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

public class PersonProducer {
    private static final String QUEUE_PERSON = "test.person";

    private static final Logger logger = LoggerFactory.getLogger(PersonProducer.class); // Logger SLF4J

    private JmsTemplate jmsTemplate;
    private final Gson gson;


    public PersonProducer(JmsTemplate jmsTemplate, Gson gson) {
        this.jmsTemplate = jmsTemplate;
        this.gson = new Gson();
    }

    public void sendPerson(Person person) {
        try {
            // Log informazioni sul messaggio che verrà inviato
            logger.info("Preparazione per inviare l'oggetto Person alla coda {}: {}", QUEUE_PERSON, person);
            String personJson = gson.toJson(person);
            // Invia il messaggio JMS
            jmsTemplate.convertAndSend(QUEUE_PERSON, personJson);
            // Log conferma dell'invio
            logger.info("Oggetto Person inviato con successo alla coda {}: {}", QUEUE_PERSON, person);
        } catch (Exception e) {
            // Log in caso di errore
            logger.error("Errore durante l'invio dell'oggetto Person alla coda {}: {}", QUEUE_PERSON, e.getMessage(), e);
        }
    }
}
