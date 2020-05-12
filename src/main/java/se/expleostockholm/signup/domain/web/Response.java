package se.expleostockholm.signup.domain.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model for Response message.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    String message;
    Long id;
}
