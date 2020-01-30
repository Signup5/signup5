package se.expleostockholm.signup.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Person {
    private Long id;
    private String email;
    private String first_name;
    private String last_name;
}
