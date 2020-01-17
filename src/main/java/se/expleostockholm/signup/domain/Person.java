package se.expleostockholm.signup.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Person {

    private Long id;
    private String email;
    private String first_name;
    private String last_name;
}
