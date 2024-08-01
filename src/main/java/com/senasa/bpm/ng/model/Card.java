package com.senasa.bpm.ng.model;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Card {
  private String type;
  private String brand;
  private String address;  // Si necesitas manejar direcciones, considera crear una clase separada Address.
  private String card_number;
  private String holder_name;
  private String expiration_year;
  private String expiration_month;
  private boolean allows_charges;
  private boolean allows_payouts;
  private String bank_name;
  private String points_type;
  private boolean points_card;
  private String bank_code;
}
