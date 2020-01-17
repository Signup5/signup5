package se.expleostockholm.signup.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.sql.Date;

@Data
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Event {
    private Long id;
    private Long host_id;
    private String title;
    private String description;
    private Date datetime;
    private String location;
}
