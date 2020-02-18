package com.learning.conferencemanager.controllers

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.learning.conferencemanager.models.Session
import com.learning.conferencemanager.repositories.SessionRepository
import config.TestPersistenceConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.util.NestedServletException
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThatExceptionOfType
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(SessionsController)
@AutoConfigureMockMvc
@Import(TestPersistenceConfiguration.class)
class SessionsControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SessionRepository sessionRepository

    private ObjectMapper objectMapper = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    def 'should get all sessions'() {
        expect:
        mockMvc.perform(get('/api/v1/sessions'))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));
    }

    def 'should get a single session'() {
        given:
        Session expectedSession = new Session(
                5, 'Spring Integration Quick Start', '', 60);

        expect:
        mockMvc.perform(get('/api/v1/sessions/5'))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedSession)))
                .andExpect(content().contentType(APPLICATION_JSON));
    }

    def 'should throw if that session does not exist'() {
        expect:
        assertThatExceptionOfType(NestedServletException)
                .isThrownBy({ mockMvc.perform(get('/api/v1/sessions/0')) })
                .withCause(new IllegalArgumentException('could not find session with id [0]'));
    }
}
