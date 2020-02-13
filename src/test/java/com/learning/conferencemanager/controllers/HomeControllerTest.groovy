package com.learning.conferencemanager.controllers


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(HomeController)
@AutoConfigureMockMvc
class HomeControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HomeController homeController;

    def "should return app version"() {
        expect:
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        '{"app-version": 0.0.1}'
                ));
    }
}
