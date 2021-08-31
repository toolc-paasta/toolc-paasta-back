package toolc.daycare.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;
import toolc.daycare.ApiDocumentationTest;
import toolc.daycare.controller.member.DirectorController;
import toolc.daycare.domain.member.Director;
import toolc.daycare.domain.member.Sex;
import toolc.daycare.dto.member.request.director.DirectorSignupRequestDto;
import toolc.daycare.exception.NotCorrectRequestEnumException;
import toolc.daycare.exception.NotExistRequestValueException;
import toolc.daycare.service.member.DirectorService;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static toolc.daycare.utils.ApiDocumentUtils.getDocumentRequest;
import static toolc.daycare.utils.ApiDocumentUtils.getDocumentResponse;


//        (uriScheme = "https", uriHost = "docs.api.com")
//@Import(ApiDocumentUtils.class)


//방법 1 - beforeEach 사용
//@ExtendWith(RestDocumentationExtension.class)


//방법 2 - auto
//@CustomConfigMockMvc
//@AutoConfigureRestDocs

//공통
@WebMvcTest(DirectorController.class)
@MockBean(JpaMetamodelMappingContext.class)
@DisplayName("원장 회원 가입 테스트")
class DirectorControllerSignUpTest extends ApiDocumentationTest {

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
    @Order(1)
    @DisplayName("정상")
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
    @Order(2)
    @DisplayName("예외 - 입력 값 부족")
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
                .andDo(document("notExistRequestValue",
                        getDocumentRequest(),
                        getDocumentResponse()));

    }

    @Test
    @Order(3)
    @DisplayName("예외 - 잘못된 enum 값")
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
                .andDo(document("notCorrectEnumRequest",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }


    private Director getCorrectDirector() {
        return Director.builder()
                .name(NAME)
                .loginId(LOGIN_ID)
                .connectionNumber(CONNECTION_NUMBER)
                .sex(SEX)
                .build();


    }
}