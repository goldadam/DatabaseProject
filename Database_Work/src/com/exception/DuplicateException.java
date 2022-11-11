package com.exception;

public class DuplicateException extends Exception{

    @Override
    public String toString(){
        return super.toString() + " 데이터 중복이 발생하였습니다.";
    }
}
