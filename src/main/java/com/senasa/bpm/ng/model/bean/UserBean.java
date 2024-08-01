package com.senasa.bpm.ng.model.bean;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBean {
  private String email;
  private String nombres;
  private String ape_Paterno;
  private String ape_Materno;
  private String rol;
}
