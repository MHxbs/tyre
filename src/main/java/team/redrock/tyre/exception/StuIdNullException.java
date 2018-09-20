package team.redrock.tyre.exception;

import team.redrock.tyre.util.Exam;

public class StuIdNullException extends Exception {
        private boolean success;
        public StuIdNullException(boolean success,String message){
            super(message);
            this.success=success;
        }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
