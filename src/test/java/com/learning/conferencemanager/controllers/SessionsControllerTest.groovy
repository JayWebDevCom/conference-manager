package com.learning.conferencemanager.controllers

import com.learning.conferencemanager.models.Session
import com.learning.conferencemanager.repositories.SessionRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.mockito.BDDMockito.given
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner)
@WebMvcTest(SessionsController)
@AutoConfigureMockMvc
class SessionsControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    SessionRepository sessionRepository;

    @Test
    def "should get sessions"() {
        expect:
        mockMvc.perform(get("/api/v1/sessions"))
                .andExpect(status().isOk());
    }

    @Test
    def "should get a session"() {
        given:
        given(sessionRepository.getOne(5))
                .willReturn(new Session(5, "name", "description", 15));

        expect:
        mockMvc.perform(get("/api/v1/sessions/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.session_id').value(5))
                .andExpect(jsonPath('$.session_name').value("name"))
                .andExpect(jsonPath('$.session_description').value("description"))
                .andExpect(jsonPath('$.session_length').value(15))
    }
}
