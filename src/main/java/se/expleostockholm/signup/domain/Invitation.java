package se.expleostockholm.signup.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Invitation {
    private Long id;
    private Person guest;
    private Long event_id;
    @Builder.Default
    private Attendance attendance = Attendance.NO_RESPONSE;
}
