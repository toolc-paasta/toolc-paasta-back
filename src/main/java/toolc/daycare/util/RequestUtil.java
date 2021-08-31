package toolc.daycare.util;

import org.springframework.stereotype.Component;
import toolc.daycare.exception.NotCorrectRequestEnumException;
import toolc.daycare.exception.NotExistRequestValueException;

@Component
public class RequestUtil {
    public static void checkNeedValue(Object... args) {
        for (Object arg : args) {
            if (arg == null) {
                throw new NotExistRequestValueException();
            }
        }
    }

    public static void checkCorrectEnum(Object... args){
        for (Object arg : args) {
            if (arg == null) {
                throw new NotCorrectRequestEnumException();
            }
        }
    }

//    public static void checkOwn(Object target, Object origin) {
//        if (!target.equals(origin)) {
//            throw new InvalidAccessException();
//        }
//    }
}
