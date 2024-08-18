package com.cadastra.people.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class MessageExceptionHandler {
    private String type;
    private String title;
    private Integer status;
    private String detail;
    private String instance;
    private Date timestamp;
}
