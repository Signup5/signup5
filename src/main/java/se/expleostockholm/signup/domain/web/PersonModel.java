package se.expleostockholm.signup.domain.web;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PersonModel {
    private Long id;
    private String email;
    private String first_name;
    private String last_name;
}