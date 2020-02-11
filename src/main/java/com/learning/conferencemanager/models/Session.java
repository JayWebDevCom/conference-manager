package com.learning.conferencemanager.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long session_id;
    private String session_name;
    private String session_description;
    private int session_length;

    @ManyToMany
    @JoinTable(
            name = "session_speakers",
            joinColumns = @JoinColumn(name = "session_id"),
            inverseJoinColumns = @JoinColumn(name = "speaker_id")
    )
    private List<Speaker> speakers;

    public Session(
            @NotNull Long session_id,
            @NotNull String session_name,
            @NotNull String session_description,
            @NotNull int session_length) {
        this.session_id = session_id;
        this.session_name = session_name;
        this.session_description = session_description;
        this.session_length = session_length;
    }

    public Session() {
    }

    public Long getSession_id() {
        return session_id;
    }

    public void setSession_id(Long session_id) {
        this.session_id = session_id;
    }

    public String getSession_name() {
        return session_name;
    }

    public void setSession_name(String session_name) {
        this.session_name = session_name;
    }

    public String getSession_description() {
        return session_description;
    }

    public void setSession_description(String session_description) {
        this.session_description = session_description;
    }

    public int getSession_length() {
        return session_length;
    }

    public void setSession_length(int session_length) {
        this.session_length = session_length;
    }

    public List<Speaker> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<Speaker> speakers) {
        this.speakers = speakers;
    }
}
