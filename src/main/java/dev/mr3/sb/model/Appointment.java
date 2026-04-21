package dev.mr3.sb.model;

import jakarta.persistence.*;


@Entity
public class Appointment {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    @Enumerated(EnumType.STRING)
    private  Weekday weekday;
    @Column
    private String time;


    public Appointment() {
    }
    public Appointment(Weekday weekday, String time) {
        this.weekday = weekday;
        this.time = time;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Weekday getWeekday() {
        return weekday;
    }
    public void setWeekday(Weekday weekday) {
        this.weekday = weekday;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
}