package service.models;

public class ApiResult<T> {

    private T result;

    private String Message ;
    private CustomResultType Status;
    private int total ;
    private int totalPages;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public CustomResultType getStatus() {
        return Status;
    }

    public void setStatus(CustomResultType status) {
        Status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}

