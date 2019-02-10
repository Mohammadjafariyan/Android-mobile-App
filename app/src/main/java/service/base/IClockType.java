package service.base;

public interface IClockType {
    Object clockInAttempt() throws Exception;

    boolean isSuccess();

    String getMessage();
}
