package com.church.manager.Church_manage.dto;

import java.time.Instant;

import jakarta.validation.constraints.NotBlank;

public class EventParticipantRequest {

    @NotBlank
    private String eventId;

    @NotBlank
    private String memberId;

    private String role;

    private Instant registeredAt;

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public Instant getRegisteredAt() { return registeredAt; }
    public void setRegisteredAt(Instant registeredAt) { this.registeredAt = registeredAt; }
}
