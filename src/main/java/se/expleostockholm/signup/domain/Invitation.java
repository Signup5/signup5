package se.expleostockholm.signup.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class Invitation {
    private Long id;
    private Long person_id;
    private Long event_id;
    private Attendance attendance;
}
