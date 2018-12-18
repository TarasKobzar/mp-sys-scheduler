package com.moex.mp.sys.scheduler.mpsysscheduler.exception;

public class AddressForMessageException extends RuntimeException {
    public AddressForMessageException() {
        super("Only the URL or topic of Kafka should be indicated, but in the correct form.");
    }
}
