package toolc.daycare;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import toolc.daycare.authentication.AccessToken;

import java.time.Duration;
import java.time.Instant;

public class Matchers {
  public static Matcher<AccessToken> isAfter30MinutesFrom(Instant baseTime) {
    return new TypeSafeDiagnosingMatcher<AccessToken>() {
      @Override
      protected boolean matchesSafely(AccessToken item, Description mismatchDescription) {
        final Instant expected = baseTime.plus(Duration.ofDays(30));
        if (item.getExpirationAt().equals(expected)) {
          return true;
        }

        mismatchDescription
          .appendValue(item)
          .appendText("이가 기대하는 값")
          .appendValue(expected)
          .appendText("와 다릅니다. 시간이 맞지 않아요....")
          .appendText(String.format("[%s] 정도 시간이 차이가 납니다.", Duration.between(expected, item.getExpirationAt())));
        return false;
      }

      @Override
      public void describeTo(Description description) {
      }
    };
  }
}
