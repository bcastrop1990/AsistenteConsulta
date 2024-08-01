package com.senasa.bpm.ng.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PaymentToken {
  private String source_id;
  private String method;
  private Double amount;
  private String currency;
  private String description;
  private String order_id;
  private String device_session_id;
  private Customer customer;
}
