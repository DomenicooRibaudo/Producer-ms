package com.wolfcoding.Producer_ms.controller;

import com.wolfcoding.Producer_ms.ObjectFactory;
import com.wolfcoding.Producer_ms.Person;
import com.wolfcoding.Producer_ms.service.PersonProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value ="person")
public class PersonController {
    private final ObjectFactory objectFactory;

    @Autowired
    private PersonProducer personProducer;

    @Autowired
    public PersonController(PersonProducer personProducer) {
        this.objectFactory = new ObjectFactory();
        this.personProducer = personProducer;
    }

    @PostMapping("/send")
    public String sendPersonToQueue(@RequestParam String name, @RequestParam String email, @RequestParam int id) {
        // Creazione di un oggetto Person
        Person person = objectFactory.createPerson();
        person.setId(id);
        person.setName(name);
        person.setEmail(email);

        // Invia il messaggio alla coda
        personProducer.sendPerson(person);

        return "Oggetto Person inviato alla coda test.person!";
    }
}
