package com.senasa.bpm.ng.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GrabarSolRequest {
  private String pcentrotramite;
  private String pcanal;
  private String ppersonaid;
  private String pprocedimientotupa;
  private String puserid;
  private String prepresentanteid;
  private String pcodsolicitud;
  private String pcodexpediente;
  private String pcodigostddoc;
  private String pcodorden;
}
