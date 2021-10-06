package toolc.daycare;

import io.jsonwebtoken.SignatureAlgorithm;
import toolc.daycare.token.AccessToken;
import toolc.daycare.token.JwtFormatter;
import toolc.daycare.token.JwtSetConfig;
import toolc.daycare.token.TokenParser;
import toolc.daycare.token.time.ConstantTime;
import toolc.daycare.token.time.CurrentTimeServer;
import toolc.daycare.token.time.RealTime;

import java.time.Instant;
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
