package se.expleostockholm.signup.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Invitation {
    private Long id;
    private Long person_id;
    private Long event_id;
    private Attendance attendance;
}
