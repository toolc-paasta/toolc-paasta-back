package toolc.daycare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import toolc.daycare.controller.member.DirectorController;
import toolc.daycare.domain.member.Director;
import toolc.daycare.domain.member.Sex;
import toolc.daycare.dto.member.request.director.DirectorSignupRequestDto;
import toolc.daycare.service.member.DirectorService;
import toolc.daycare.util.RequestUtil;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;


@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(DirectorController.class)
class CenterControllerTest {

    //mock 설정
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext ctx;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    //x같은 잭슨에서 오브젝트 매퍼
    @Autowired
    private ObjectMapper objectMapper;

    //mockBean 들
    @MockBean
    private DirectorService directorService;

    //기본 원장 빌드
    private String NAME = "correctName";
    private String LOGIN_ID = "correctLoginId";
    private String PASSWORD = "correctPassword";
    private String CONNECTION_NUMBER = "010-0000-0000";
    private Sex SEX = Sex.WOMAN;

    Director correctDirector = Director.builder()
            .name(NAME)
            .loginId(LOGIN_ID)
            .password(PASSWORD)
            .connectionNumber(CONNECTION_NUMBER)
            .sex(SEX)
            .build();

    //    @Test
//    @DisplayName("테스트 연습")
    void test() throws Exception {
        this.mockMvc.perform(post("/api/member/director/test"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("hello"));
    }

    @Test
    @DisplayName("원장 회원 가입 - 정상")
    void correctDirectorSignUp() throws Exception {
        //given
        DirectorSignupRequestDto requestDto = DirectorSignupRequestDto.builder()
                .name(NAME)
                .loginId(LOGIN_ID)
                .password(PASSWORD)
                .connectionNumber(CONNECTION_NUMBER)
                .sex(SEX)
                .build();

        given(directorService.signUp(any(), any(), any(), any(), any())).willReturn(correctDirector);


        //when
        ResultActions actions = mockMvc.perform(post("/api/member/director/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));

        System.out.println(objectMapper.writeValueAsString(requestDto));

        //then
        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(equalTo(true)))
                .andExpect(jsonPath("$.error").value(equalTo(null)))
                .andExpect(jsonPath("$.response.loginId").value(equalTo(LOGIN_ID)))
                .andExpect(jsonPath("$.response.name").value(equalTo(NAME)))
                .andExpect(jsonPath("$.response.connectionNumber").value(equalTo(CONNECTION_NUMBER)))
                .andExpect(jsonPath("$.response.sex").value(equalTo("여성")));

        //verify
        verify(directorService).signUp(any(), any(), any(), any(), any());
    }
    
    
    //아직 실패
    @Test
    @DisplayName("원장 회원 가입 - 예외 - 입력 값 부족")
    void notExistRequestDirectorSignUp() throws Exception {
        //given
        DirectorSignupRequestDto requestDto = DirectorSignupRequestDto.builder()
//                .name(NAME)
                .loginId(LOGIN_ID)
                .password(PASSWORD)
                .connectionNumber(CONNECTION_NUMBER)
                .sex(SEX)
                .build();

        given(directorService.signUp(any(), any(), any(), any(), any())).willReturn(correctDirector);


        //when
        ResultActions actions = mockMvc.perform(post("/api/member/director/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));

        System.out.println(objectMapper.writeValueAsString(requestDto));

        //then
        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(equalTo(true)))
                .andExpect(jsonPath("$.error").value(equalTo(null)))
                .andExpect(jsonPath("$.response.loginId").value(equalTo(LOGIN_ID)))
                .andExpect(jsonPath("$.response.name").value(equalTo(NAME)))
                .andExpect(jsonPath("$.response.connectionNumber").value(equalTo(CONNECTION_NUMBER)))
                .andExpect(jsonPath("$.response.sex").value(equalTo("여성")));

        //verify
        verify(directorService).signUp(any(), any(), any(), any(), any());
    }

//    private Director
}