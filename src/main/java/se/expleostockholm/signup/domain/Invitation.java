package se.expleostockholm.signup.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Invitation {
    private Long id;
    private Person person_id;
    private Person event_id;
    private Attendance attendance;
}
