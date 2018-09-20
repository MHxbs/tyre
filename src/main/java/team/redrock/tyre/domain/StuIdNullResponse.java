package team.redrock.tyre.domain;

public class StuIdNullResponse {
    private boolean success;
    private String info;

    public StuIdNullResponse(boolean success, String message) {
        this.success=success;
        this.info=message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
