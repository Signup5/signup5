package se.expleostockholm.signup.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private String password;
}
