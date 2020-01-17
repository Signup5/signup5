package se.expleostockholm.signup.domain;

import lombok.Data;

@Data
public class Invitation {
    private Long id;
    private Person person_id;
    private Person event_id;
    private Attendance attendance;
}
