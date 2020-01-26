package se.expleostockholm.signup.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Event {
    private Long id;
    private Long host_id;
    private String title;
    private String description;
    private LocalDate date_of_event;
    private String location;
}
