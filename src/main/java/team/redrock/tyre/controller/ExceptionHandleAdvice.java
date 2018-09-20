package team.redrock.tyre.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import team.redrock.tyre.domain.ErrorResponse;
import team.redrock.tyre.domain.StuIdNullResponse;
import team.redrock.tyre.exception.StuIdNullException;
import team.redrock.tyre.exception.StuidValidException;

@ControllerAdvice
@ResponseBody
public class ExceptionHandleAdvice {

    @ExceptionHandler(StuidValidException.class)
    public ErrorResponse handleException(StuidValidException e){
        e.printStackTrace();
        return new ErrorResponse(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(StuIdNullException.class)
    public StuIdNullResponse handleStuIdNull(StuIdNullException e){
        e.printStackTrace();
        return new StuIdNullResponse(e.isSuccess(),e.getMessage());
    }
}
