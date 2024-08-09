package com.task_pay.task_pay.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckOutOption {
    private String key;
    private int amount;
    private String orderId;
    private String currency;
    private Prefill prefill;
}
