package com.senasa.bpm.ng.masajes.model.response;


import com.senasa.bpm.ng.masajes.model.Card;
import com.senasa.bpm.ng.masajes.model.Customer;
import com.senasa.bpm.ng.masajes.model.Fee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PaymentResponse {
    private String id;
    private String authorization;
    private String operation_type;
    private String transaction_type;
    private String status;
    private boolean conciliated;
    private String creation_date;
    private String operation_date;
    private String description;
    private String error_message;
    private String order_id;
    private Card card;
    private double amount;
    private Customer customer;
    private Fee fee;
    private String currency;
    private String method;
}
