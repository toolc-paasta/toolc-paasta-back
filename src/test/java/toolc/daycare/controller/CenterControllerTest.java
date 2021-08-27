package toolc.daycare.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import toolc.daycare.controller.member.DirectorController;
import toolc.daycare.service.member.DirectorService;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(DirectorController.class)
class CenterControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DirectorService directorService;

    @Test
    @DisplayName("테스트 연습")
    void test() throws Exception {
//        given(directorService.findDirectorByLoginId(ArgumentMatchers.any())).willReturn(new Director(any(),any(),any(),any(),any()));
        this.mockMvc.perform(post("/api/member/director/test"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("hello"));
    }

    @Test
    @DisplayName("원장 회원 가입 - 정상")
    void signUp() throws Exception {
//        given(directorService.findDirectorByLoginId(ArgumentMatchers.any())).willReturn(new Director(any(),any(),any(),any(),any()));
        mockMvc.perform(post("/api/member/director/signup"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("hello"));
    }
}