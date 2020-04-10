package se.expleostockholm.signup.domain.web;

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
public class PersonModel {
    private Long id;
    private String email;
    private String first_name;
    private String last_name;
}