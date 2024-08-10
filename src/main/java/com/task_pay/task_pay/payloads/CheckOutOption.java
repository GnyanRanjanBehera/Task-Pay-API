package com.task_pay.task_pay.payloads;

import com.google.type.DateTime;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CheckOutOption {
    private String name;
    private String description;
    private String key;
    private int amount;
    private String orderId;
    private String currency;
    private Date createdAt;
    private Prefill prefill;
}
