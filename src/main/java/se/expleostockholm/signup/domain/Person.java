package se.expleostockholm.signup.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import se.expleostockholm.signup.domain.web.PersonModel;

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
    @ToString.Exclude
    private String password;


    public PersonModel asPersonModel() {
        return PersonModel.builder()
                .id(id)
                .email(email)
                .first_name(first_name)
                .last_name(last_name)
                .build();
    }
}
