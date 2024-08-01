package com.senasa.bpm.ng.model.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiExceptionResponse {
    private String code;
    private String message;
}
