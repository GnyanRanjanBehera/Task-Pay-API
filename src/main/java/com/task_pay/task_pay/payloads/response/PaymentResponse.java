package com.task_pay.task_pay.payloads.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;


public class PaymentResponse extends JSONObject {
    private int amount;
    private String order_Id;
    private String description;
    private String name;
    private String key;
    private int timeOut;
}
