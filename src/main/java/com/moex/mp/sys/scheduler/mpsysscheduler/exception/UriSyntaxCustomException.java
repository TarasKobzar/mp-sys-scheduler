package com.moex.mp.sys.scheduler.mpsysscheduler.exception;

public class UriSyntaxCustomException extends RuntimeException {
    public UriSyntaxCustomException() {
        super("The URI is invalid.");
    }
}
