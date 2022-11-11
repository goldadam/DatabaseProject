package com.exception;

public class DataNotFoundException extends Exception {

    @Override
    public String toString(){
        return super.toString() + " : 데이터가 현재 발견되지 않았습니다.";
    }
}
