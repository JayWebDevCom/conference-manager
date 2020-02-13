package com.learning.conferencemanager.controllers

import com.learning.conferencemanager.models.Speaker
import com.learning.conferencemanager.repositories.SpeakerRepository
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(SpeakersController)
@AutoConfigureMockMvc
class SpeakersControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc;

    @SpringBean
    private SpeakerRepository speakerRepository = Mock();

    def "should get speakers"() {
        expect:
        mockMvc.perform(get("/api/v1/speakers"))
                .andExpect(status().isOk());
    }

    def "should get a speaker"() {
        given:
        speakerRepository.getOne(5) >> new Speaker(
                1,
                "f_name",
                "l-name",
                "some-title",
                "company",
                "this is the bio")

        expect:
        mockMvc.perform(get("/api/v1/speakers/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.speaker_id').value(1));
    }
}
