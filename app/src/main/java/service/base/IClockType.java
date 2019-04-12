package service.base;

import service.CallbackListener;

public interface IClockType {
    Object clockInAttempt(CallbackListener callbackListener) throws Exception;

    boolean isSuccess();

    String getMessage();
}
