package se.expleostockholm.signup.domain;

import lombok.*;
import lombok.Builder.Default;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Event {
    private Long id;
    private Person host;
    @Default
    private List<Invitation> invitations = new ArrayList<>();
    private String title;
    private String description;
    private LocalDate date_of_event;
    private LocalTime time_of_event;
    private String location;
}
