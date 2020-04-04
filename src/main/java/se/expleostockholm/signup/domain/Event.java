package se.expleostockholm.signup.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private Boolean isCanceled;

    public LocalDateTime toLocalDateTime() {
        return LocalDateTime.of(this.date_of_event, this.time_of_event);
    }
}