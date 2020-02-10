package se.expleostockholm.signup.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Invitation {
    private Long id;
    private Person guest;
    private Event event;
    private Attendance attendance;
}
