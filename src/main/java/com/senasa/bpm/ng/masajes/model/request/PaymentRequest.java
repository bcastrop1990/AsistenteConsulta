package com.senasa.bpm.ng.masajes.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.senasa.bpm.ng.masajes.model.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

//AAAAA
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PaymentRequest {
  @JsonProperty("source_id")
  private String sourceId;
  private String method;
  private BigDecimal amount;
  private String currency;
  private String description;
  @JsonProperty("order_id")
  private String orderId;
  @JsonProperty("device_session_id")
  private String deviceSessionId;
  private Customer customer;
}