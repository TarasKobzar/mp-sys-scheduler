package com.moex.mp.sys.scheduler.mpsysscheduler.exception;

public class CronExpressionIsInvalidException extends RuntimeException {
    public CronExpressionIsInvalidException() {
        super("The cron expression is invalid");
    }
}
