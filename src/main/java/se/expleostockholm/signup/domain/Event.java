package se.expleostockholm.signup.domain;

import lombok.*;
import lombok.Builder.Default;

import java.time.*;
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
    private Short duration;
    private String location;
    private Boolean isDraft;
    @Default
    private boolean isCanceled = Boolean.FALSE;

    public LocalDateTime toLocalDateTime() {
        return LocalDateTime.of(this.date_of_event, this.time_of_event);
    }
}