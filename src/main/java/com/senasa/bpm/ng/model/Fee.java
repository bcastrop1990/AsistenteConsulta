package com.senasa.bpm.ng.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Fee {
  private double amount;
  private double tax;
  private String currency;
}
