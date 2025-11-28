package com.church.manager.Church_manage.dto;

import java.time.Instant;

public class EventParticipantResponse {
    private String id;
    private String eventId;
    private String memberId;
    private String role;
    private Instant registeredAt;
    private Instant createdAt;
    private Instant updatedAt;

    public EventParticipantResponse() {}

    public EventParticipantResponse(String id, String eventId, String memberId, String role, Instant registeredAt, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.eventId = eventId;
        this.memberId = memberId;
        this.role = role;
        this.registeredAt = registeredAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public Instant getRegisteredAt() { return registeredAt; }
    public void setRegisteredAt(Instant registeredAt) { this.registeredAt = registeredAt; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
