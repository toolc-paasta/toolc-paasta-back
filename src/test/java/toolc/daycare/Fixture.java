package toolc.daycare;

import io.jsonwebtoken.SignatureAlgorithm;
import toolc.daycare.authentication.AccessToken;
import toolc.daycare.authentication.JwtFormatter;
import toolc.daycare.authentication.config.JwtSetConfig;
import toolc.daycare.authentication.TokenParser;
import toolc.daycare.authentication.time.ConstantTime;
import toolc.daycare.authentication.time.CurrentTimeServer;
import toolc.daycare.authentication.time.RealTime;

import java.time.LocalDateTime;

import static java.time.ZoneOffset.UTC;

public class Fixture {
  static final String SECRET_KEY = "dfjklaefjlaejfdcDfejA123idjfW123oidfRtestHfdjaidfRRtest12T3413dfjladfaeDFADFAdfvdsAERdfAERad12ADFerDFAvEFDF";
  static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

  public static CurrentTimeServer time() {
    return new ConstantTime(LocalDateTime.of(1998, 02, 25, 12, 12).toInstant(UTC));
  }

  public static CurrentTimeServer realTime() {
    return new RealTime();
  }

  public static AccessToken accessToken(String loginId, CurrentTimeServer currentTimeServer) {
    return AccessToken.issue(loginId, currentTimeServer);
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
}
