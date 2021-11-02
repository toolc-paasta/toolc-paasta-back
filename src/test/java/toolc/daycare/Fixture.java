package toolc.daycare;

import io.jsonwebtoken.SignatureAlgorithm;
import toolc.daycare.authentication.AccessToken;
import toolc.daycare.authentication.JwtFormatter;
import toolc.daycare.authentication.config.JwtSetConfig;
import toolc.daycare.authentication.TokenParser;
import toolc.daycare.authentication.time.ConstantTime;
import toolc.daycare.authentication.time.CurrentTimeServer;
import toolc.daycare.authentication.time.RealTime;
import toolc.daycare.domain.group.Center;
import toolc.daycare.domain.member.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.ZoneOffset.UTC;

public class Fixture {
  static final String SECRET_KEY = "dfjklaefjlaejfdcDfejA123idjfW123oidfRtestHfdjaidfRRtest12T3413dfjladfaeDFADFAdfvdsAERdfAERad12ADFerDFAvEFDF";
  static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;
  public static final Authority AUTHORITY = Authority.ADMIN;

  public static CurrentTimeServer time() {
    return new ConstantTime(LocalDateTime.of(1998, 02, 25, 12, 12).toInstant(UTC));
  }

  public static CurrentTimeServer realTime() {
    return new RealTime();
  }

  public static AccessToken accessToken(String loginId, CurrentTimeServer currentTimeServer) {
    return AccessToken.issue(loginId, currentTimeServer, AUTHORITY);
  }

  public static JwtSetConfig jwtSetConfig() {
    return new JwtSetConfig(SECRET_KEY, SIGNATURE_ALGORITHM);
  }

  public static JwtFormatter jwtFormatter() {
    return new JwtFormatter(jwtSetConfig());
  }

  public static TokenParser tokenParser() {
    return new TokenParser(jwtSetConfig());
  }

  public static Director.DirectorBuilder director() {
    return Director.builder()
      .name("director001")
      .sex(Sex.MAN)
      .connectionNumber("010-0000-1111")
      .password("password001")
      .token("token001")
      .loginId("director001");
  }

  public static Center.CenterBuilder center() {
    return Center.builder()
      .star(5L)
      .foundationDate(LocalDate.of(1998, 2, 25))
      .address("집")
      .name("center001");
  }

  public static Teacher.TeacherBuilder teacher() {
    return Teacher.builder()
      .connectionNumber("010-1111-2222")
      .loginId("teacher001")
      .name("teacher")
      .password("password001")
      .sex(Sex.MAN)
      .token("token001");
  }

  public static Student.StudentBuilder student() {
    return Student.builder()
      .name("student001")
      .sex(Sex.MAN)
      .age(10)
      .connectionNumber("010-0000-0000");
  }
}
