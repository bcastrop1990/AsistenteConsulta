package com.senasa.bpm.ng.masajes.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
