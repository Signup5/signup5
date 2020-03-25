package se.expleostockholm.signup.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Person {
    private Long id;
    private String email;
    private String first_name;
    private String last_name;
}
