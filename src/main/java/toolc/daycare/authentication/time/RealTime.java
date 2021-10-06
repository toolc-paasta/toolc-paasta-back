package toolc.daycare.authentication.time;

import java.time.Instant;

public class RealTime implements CurrentTimeServer {
  @Override
  public Instant now() {
    return Instant.now();
  }
}
