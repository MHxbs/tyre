package team.redrock.tyre.domain;

/**
 * 统一异常处理的返回bean
 */
public class ErrorResponse {
    private int success;
    private String info;

    public ErrorResponse(int success, String info) {
        this.success=success;
        this.info=info;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
