package com.church.manager.Church_manage.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.church.manager.Church_manage.entities.EventParticipant;

public interface EventParticipantRepository extends JpaRepository<EventParticipant, String> {
    List<EventParticipant> findByEventId(String eventId);
}
