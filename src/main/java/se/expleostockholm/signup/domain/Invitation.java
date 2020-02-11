package se.expleostockholm.signup.domain;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Invitation {
    private Long id;
    private Person guest;
    private Event event;
    private Attendance attendance;
}
