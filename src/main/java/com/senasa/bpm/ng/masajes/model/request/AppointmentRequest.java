package com.senasa.bpm.ng.masajes.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AppointmentRequest {
  private Date startTime;
  private Date endTime;
  private String summary;
  private String description;
  private String email;
}
