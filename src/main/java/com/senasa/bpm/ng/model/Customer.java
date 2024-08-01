package com.senasa.bpm.ng.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Customer {
  private String name;
  private String email;
}


