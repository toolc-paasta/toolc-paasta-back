package toolc.daycare.token.time;

import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
public class ConstantTime implements CurrentTimeServer {
  final Instant instant;

  @Override
  public Instant now() {
    return instant;
  }
}
