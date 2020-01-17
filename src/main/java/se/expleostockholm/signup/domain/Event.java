package se.expleostockholm.signup.domain;

import lombok.Data;

import java.sql.Date;

@Data
public class Event {
    private Long id;
    private Long host_id;
    private String title;
    private String description;
    private Date datetime;
    private String location;
}
