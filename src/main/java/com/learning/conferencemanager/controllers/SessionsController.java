package com.learning.conferencemanager.controllers;

import com.learning.conferencemanager.models.Session;
import com.learning.conferencemanager.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.String.format;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionsController {

    private SessionRepository sessionRepository;

    public SessionsController(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @GetMapping
    public List<Session> sessions() {
        return sessionRepository.findAll();
    }

    @GetMapping("/{id}")
    public Session getOne(@PathVariable Long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(format("could not find session with id [%d]", id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Session create(@RequestBody final Session session) {
        return sessionRepository.saveAndFlush(session);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Session put(@PathVariable Long id, @RequestBody final Session session) {
        final Session existingSession = sessionRepository.getOne(id);
        BeanUtils.copyProperties(session, existingSession, "session_id");
        return sessionRepository.saveAndFlush(existingSession);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        sessionRepository.deleteById(id);
    }


}
