package com.butola.mydocsservice.property

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
class PropertyControllerTest extends Specification {

    @Autowired
    private MockMvc mvc;

    @Test
    def "should return #expectedStatus for #input"() {
        given:
        def expectedContentType = "application/pdf"
        def request = MockMvcRequestBuilders.get("/property/pdf")

        when:
        def response = mvc.perform(request)

        then:
        response.andExpect(status().isOk())
                .andExpect(content().contentType(expectedContentType))
                .andExpect(header().string("Content-Disposition", "attachment; filename=us.minnesota.edenprairie.yogi.tax.pdf"))

        and:
        def contentBytes = response.andReturn().response.contentAsByteArray
        assertThat(contentBytes).isNotEmpty()
    }

    @Test
    def "should return bad request when file is empty"() {
        given:
        def file = new MockMultipartFile("file", "", MediaType.TEXT_PLAIN_VALUE, new byte[0])
        MockMultipartHttpServletRequestBuilder multipartRequest =
                MockMvcRequestBuilders.multipart("/property/upload");

        when:
        def result = mvc.perform(multipartRequest.file(file))
                .andExpect(status().isBadRequest());

        then:
        result.andExpect(MockMvcResultMatchers.content().string("Please select a file to upload."))
    }

    @Test
    def "should upload file successfully"() {
        given:
        def file = new MockMultipartFile("file", "test.txt", "text/plain", "test".getBytes())
        MockMultipartHttpServletRequestBuilder multipartRequest =
                MockMvcRequestBuilders.multipart("/property/upload");

        when:
        def result = mvc.perform(multipartRequest.file(file))
                .andExpect(status().isOk());

        then:
        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("File uploaded successfully."))
                .andDo(MockMvcResultHandlers.print())
    }
}
