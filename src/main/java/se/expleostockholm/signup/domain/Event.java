package se.expleostockholm.signup.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    private Long id;
    private Person host;
    private List<Invitation> invitations;
    private String title;
    private String description;
    private LocalDate date_of_event;
    private LocalTime time_of_event;
    private String location;
}
