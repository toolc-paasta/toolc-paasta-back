package toolc.daycare.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;
import toolc.daycare.ApiDocumentationTest;
import toolc.daycare.controller.member.DirectorController;
import toolc.daycare.controller.member.ParentsController;
import toolc.daycare.domain.member.Director;
import toolc.daycare.domain.member.Parents;
import toolc.daycare.domain.member.Sex;
import toolc.daycare.dto.member.request.LoginRequestDto;
import toolc.daycare.dto.member.request.director.DirectorSignupRequestDto;
import toolc.daycare.exception.NotCorrectRequestEnumException;
import toolc.daycare.exception.NotExistMemberException;
import toolc.daycare.exception.NotExistRequestValueException;
import toolc.daycare.service.member.DirectorService;
import toolc.daycare.service.member.ParentsService;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static toolc.daycare.utils.ApiDocumentUtils.getDocumentRequest;
import static toolc.daycare.utils.ApiDocumentUtils.getDocumentResponse;



//??????
@WebMvcTest(ParentsController.class)
@MockBean(JpaMetamodelMappingContext.class)
@DisplayName("?????? ????????? ?????????")
class ParentsControllerLoginTest extends ApiDocumentationTest {

    //mockBean ???
    @MockBean
    private ParentsService parentsService;

    //?????? ?????? ??????
    private String NAME = "correctName";
    private String LOGIN_ID = "correctLoginId";
    private String PASSWORD = "correctPassword";
    private String CONNECTION_NUMBER = "010-1234-1234";
    private Sex SEX = Sex.WOMAN;
    private String CHILD_NAME = "correctC`1hildName";
    private LocalDate CHILD_BIRTHDAY = LocalDate.of(2008, 7, 23);
    private Sex CHILD_SEX = Sex.MAN;


    @Test
    @Order(1)
    @DisplayName("??????")
    void correctDirectorLogin() throws Exception { // TODO: ?????? ????????? ?????? -> ?????? ??????
        //given
        LoginRequestDto requestDto = LoginRequestDto.builder()
                .loginId(LOGIN_ID)
                .password(PASSWORD)
                .build();

//        given(parentsService.login(any(), any(), any())).willReturn(getCorrectDirector());


        //when
        ResultActions actions = mockMvc.perform(post("/api/member/parents/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));


        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(equalTo(true)))
                .andExpect(jsonPath("$.error").value(equalTo(null)))
                .andExpect(jsonPath("$.response.loginId").value(equalTo(LOGIN_ID)))
                .andExpect(jsonPath("$.response.name").value(equalTo(NAME)))
                .andExpect(jsonPath("$.response.connectionNumber").value(equalTo(CONNECTION_NUMBER)))
                .andExpect(jsonPath("$.response.sex").value(equalTo("??????")))
                .andExpect(jsonPath("$.response.childName").value(equalTo(CHILD_NAME)))
                .andExpect(jsonPath("$.response.childBirthday").value(equalTo(CHILD_BIRTHDAY.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))))
                .andExpect(jsonPath("$.response.childSex").value(equalTo("??????")))
        ;

        //verify
        verify(parentsService).login(any(), any(), any());

        //restDoc ??????
        actions
                .andDo(document("correctParentsLogin",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("loginId").type(JsonFieldType.STRING).description("????????? ?????????"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("????????????")
                        )
                ));
    }

//
//    @Test
//    @Order(2)
//    @DisplayName("?????? - ?????? ??? ??????")
//    void notExistRequestValueDirectorLogin() throws Exception {
//        //given
//        LoginRequestDto requestDto = LoginRequestDto.builder()
//                .loginId(LOGIN_ID)
//                //??? ?????? .password(PASSWORD)
//                .build();
//
//        //when
//        ResultActions actions = mockMvc.perform(post("/api/member/director/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(requestDto)));
//
//
//        //then
//        actions
//                .andExpect(status().isBadRequest())
//                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotExistRequestValueException))
//                .andExpect(result -> assertEquals("???????????? ????????????.", result.getResolvedException().getMessage()))
//
//                //responseBody
//                .andExpect(jsonPath("$.success").value(equalTo(false)))
//                .andExpect(jsonPath("$.response").value(equalTo(null)))
//                .andExpect(jsonPath("$.status").value(equalTo(400)))
//                .andExpect(jsonPath("$.message").value(equalTo("???????????? ????????????.")));
//
//
//        //verify
//        verify(directorService, never()).login(any(), any());
//
//        //restDoc ??????
//        actions
//                .andDo(document("notExistRequestValue",
//                        getDocumentRequest(),
//                        getDocumentResponse()));
//
//    }
//
//    @Test
//    @Order(3)
//    @DisplayName("?????? - ?????? ?????????")
//    void notCorrectEnumRequestDirectorSignUp() throws Exception {
//        //given
//        LoginRequestDto requestDto = LoginRequestDto.builder()
//                .loginId(LOGIN_ID)
//                .password("InvalidPassword")
//                .build();
//
//        given(directorService.login(any(), any())).willReturn(null);
//
//        //when
//        ResultActions actions = mockMvc.perform(post("/api/member/director/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(requestDto)));
//
//        //then
//        actions
//                .andExpect(status().isBadRequest())
//                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotExistMemberException))
//                .andExpect(result -> assertEquals("????????? ?????? ?????????????????????.", result.getResolvedException().getMessage()))
//
//                //responseBody
//                .andExpect(jsonPath("$.success").value(equalTo(false)))
//                .andExpect(jsonPath("$.response").value(equalTo(null)))
//                .andExpect(jsonPath("$.status").value(equalTo(400)))
//                .andExpect(jsonPath("$.message").value(equalTo("????????? ?????? ?????????????????????.")));
//
//
//        //verify
//        verify(directorService, never()).signUp(any(), any(), any(), any(), any());
//
//        actions
//                .andDo(document("notCorrectEnumRequestDirectorSignUp",
//                        getDocumentRequest(),
//                        getDocumentResponse()));
//    }


    private Parents getCorrectDirector() {
        return Parents.builder()
                .name(NAME)
                .loginId(LOGIN_ID)
                .connectionNumber(CONNECTION_NUMBER)
                .sex(SEX)
                .childName("childName")
                .childBirthday(CHILD_BIRTHDAY)
                .childSex(CHILD_SEX)
                .build();

    }
}