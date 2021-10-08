package toolc.daycare.service.fcm;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class FcmSendBody {

    private String title;
    private String body;
    private List<String> targetUserToken;
    private Map<String, Object> data;

    @Builder
    public FcmSendBody(String title, String body, List<String> targetUserToken, Map<String, Object> data) {
        this.title = title;
        this.body = body;
        this.targetUserToken = targetUserToken;
        this.data = data;
    }
}
