package com.senasa.bpm.ng.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ClinicaRequest {
  private String nombreClinica;
  private String ruc;
  private String ubi;
  private String imagen;
  private List<String> imagen_visual;
}
