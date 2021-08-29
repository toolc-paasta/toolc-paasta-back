package toolc.daycare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import toolc.daycare.controller.member.DirectorController;
import toolc.daycare.domain.member.Director;
import toolc.daycare.domain.member.Sex;
import toolc.daycare.dto.member.request.director.DirectorSignupRequestDto;
import toolc.daycare.exception.NotCorrectRequestEnumException;
import toolc.daycare.exception.NotExistRequestValueException;
import toolc.daycare.service.member.DirectorService;
import toolc.daycare.testconfig.ApiDocumentUtils;
import toolc.daycare.testconfig.CustomConfigMockMvc;
import toolc.daycare.util.RequestUtil;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;
import static toolc.daycare.testconfig.ApiDocumentUtils.getDocumentRequest;
import static toolc.daycare.testconfig.ApiDocumentUtils.getDocumentResponse;


//        (uriScheme = "https", uriHost = "docs.api.com")

//방법 1 - beforeEach 사용
@ExtendWith(RestDocumentationExtension.class)


//방법 2 - auto
//@CustomConfigMockMvc
//@AutoConfigureRestDocs
//@Import(ApiDocumentUtils.class)

//공통
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(DirectorController.class)
@DisplayName("센터 컨트롤러 테스트")
class DirectorControllerTest {

    //mock 설정
//    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext ctx;

    @BeforeEach
    public void setup(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .apply(documentationConfiguration(restDocumentation))
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
    private String CONNECTION_NUMBER = "010-1234-1234";
    private Sex SEX = Sex.WOMAN;


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

        given(directorService.signUp(any(), any(), any(), any(), any())).willReturn(getCorrectDirector());


        //when
        ResultActions actions = mockMvc.perform(post("/api/member/director/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));


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

        //restDoc 생성
        actions
                .andDo(document("correctDirectorSignUp",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("loginId").type(JsonFieldType.STRING).description("로그인 아이디"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("connectionNumber").type(JsonFieldType.STRING).description("연락처"),
                                fieldWithPath("sex").type("성별").description("\"남성\",  \"여성\"")
                        )
//                        responseFields(
//                                fieldWithPath("id").type(JsonFieldType.STRING).description("로그인 아이디")
//                        )
                ));
    }


    @Test
    @DisplayName("원장 회원 가입 - 예외 - 입력 값 부족")
    void notExistRequestValueDirectorSignUp() throws Exception {
        //given
        DirectorSignupRequestDto requestDto = DirectorSignupRequestDto.builder()
//                .name(NAME)
                .loginId(LOGIN_ID)
                .password(PASSWORD)
                .connectionNumber(CONNECTION_NUMBER)
                .sex(SEX)
                .build();


        //when
        ResultActions actions = mockMvc.perform(post("/api/member/director/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));


        //then
        actions
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotExistRequestValueException))
                .andExpect(result -> assertEquals("필요값이 없습니다.", result.getResolvedException().getMessage()))

                //responseBody
                .andExpect(jsonPath("$.success").value(equalTo(false)))
                .andExpect(jsonPath("$.response").value(equalTo(null)))
                .andExpect(jsonPath("$.status").value(equalTo(400)))
                .andExpect(jsonPath("$.message").value(equalTo("필요값이 없습니다.")));


        //verify
        verify(directorService, never()).signUp(any(), any(), any(), any(), any());

        //restDoc 생성
        actions
                .andDo(document("notExistRequestValueDirectorSignUp",
                        getDocumentRequest(),
                        getDocumentResponse()));

    }

    @Test
    @DisplayName("원장 회원 가입 - 예외 - 잘못된 enum 값")
    void notCorrectEnumRequestDirectorSignUp() throws Exception {
        //given
        String requestDto = "{\n" +
                "    \"loginId\" : \"directorLoginId1\",  \n" +
                "    \"password\" : \"password1234\",\n" +
                "    \"name\": \"directorName\",\n" +
                "    \"connectionNumber\": \"010-1234-5678\",\n" +
                "    \"sex\": \"잘못된 성별 값\"\n" +
                "}";
        //when
        ResultActions actions = mockMvc.perform(post("/api/member/director/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestDto));


        //then
        actions
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotCorrectRequestEnumException))
                .andExpect(result -> assertEquals("열거형 값이 잘못되었습니다.", result.getResolvedException().getMessage()))

                //responseBody
                .andExpect(jsonPath("$.success").value(equalTo(false)))
                .andExpect(jsonPath("$.response").value(equalTo(null)))
                .andExpect(jsonPath("$.status").value(equalTo(400)))
                .andExpect(jsonPath("$.message").value(equalTo("열거형 값이 잘못되었습니다.")));


        //verify
        verify(directorService, never()).signUp(any(), any(), any(), any(), any());

        actions
                .andDo(document("notCorrectEnumRequestDirectorSignUp",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }


    private Director getCorrectDirector() {
        return Director.builder()
                .name(NAME)
                .loginId(LOGIN_ID)
                .password(PASSWORD)
                .connectionNumber(CONNECTION_NUMBER)
                .sex(SEX)
                .build();


    }
}