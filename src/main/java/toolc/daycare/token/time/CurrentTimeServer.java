package toolc.daycare.token.time;

import java.time.Instant;

@FunctionalInterface
public interface CurrentTimeServer {
  Instant now();
}
