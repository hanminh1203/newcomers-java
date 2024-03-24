package vn.elca.training.model.entity;

public class ErrorResponse {
    private String message;
    private String detail;

    public ErrorResponse(String message, String detail) {
        this.message = message;
        this.detail = detail;
    }

    public String getMessage() {
        return message;
    }

    public String getDetail() {
        return detail;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
