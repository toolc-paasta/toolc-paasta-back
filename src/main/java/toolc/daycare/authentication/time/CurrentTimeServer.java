package toolc.daycare.authentication.time;

import java.time.Instant;

@FunctionalInterface
public interface CurrentTimeServer {
  Instant now();
}
