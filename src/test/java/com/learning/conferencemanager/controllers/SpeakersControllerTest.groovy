package com.learning.conferencemanager.controllers


import com.learning.conferencemanager.models.Speaker
import com.learning.conferencemanager.repositories.SpeakerRepository
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
@WebMvcTest(SpeakersController)
@AutoConfigureMockMvc
class SpeakersControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    SpeakerRepository speakerRepository;

    @Test
    def "should get speakers"() {
        expect:
        mockMvc.perform(get("/api/v1/speakers"))
                .andExpect(status().isOk());
    }

    @Test
    def "should get a speaker"() {
        given:
        given(speakerRepository.getOne(5))
                .willReturn(new Speaker(1, "f_name", "l-name", "some-title", "company", "this is the bio"));

        expect:
        mockMvc.perform(get("/api/v1/speakers/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.speaker_id').value(1));
    }
}
