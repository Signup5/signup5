package se.expleostockholm.signup.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    String message;
    Long id;
}
