package team.redrock.tyre.exception;


/**
 * 自己定义的异常
 */
public class StuidValidException extends Exception {

    private int code;

    public StuidValidException(int code,String message){
        super(message);
        this.code=code;

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
