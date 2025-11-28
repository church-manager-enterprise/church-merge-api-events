package com.church.manager.Church_manage.controllers;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.church.manager.Church_manage.dto.EventParticipantRequest;
import com.church.manager.Church_manage.dto.EventParticipantResponse;
import com.church.manager.Church_manage.dto.EventRequest;
import com.church.manager.Church_manage.entities.Event;
import com.church.manager.Church_manage.entities.EventParticipant;
import com.church.manager.Church_manage.repositories.EventParticipantRepository;
import com.church.manager.Church_manage.repositories.EventRepository;

import jakarta.persistence.criteria.Predicate;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventRepository eventRepository;
    private final EventParticipantRepository participantRepository;

    public EventController(EventRepository eventRepository,
            EventParticipantRepository participantRepository) {
        this.eventRepository = eventRepository;
        this.participantRepository = participantRepository;
    }

    @GetMapping
    public ResponseEntity<List<Event>> listEvents(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant createdAfter,
            @RequestParam(required = false) String churchId) {
        Specification<Event> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (createdAfter != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), createdAfter));
            }
            if (churchId != null && !churchId.isEmpty()) {
                predicates.add(cb.equal(root.get("churchId"), churchId));
            }
            return cb.and(predicates.toArray(Predicate[]::new));
        };

        List<Event> events = eventRepository.findAll(spec);
        return ResponseEntity.ok(events);
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody EventRequest request) {
        Event event = new Event();
        event.setChurchId(request.getChurchId());
        event.setName(request.getName());
        event.setDescription(request.getDescription());
        event.setStartDatetime(request.getStartDatetime());
        event.setEndDatetime(request.getEndDatetime());
        event.setLocation(request.getLocation());
        event.setCreatedBy(request.getCreatedBy());

        Event saved = eventRepository.save(event);
        URI location = URI.create(String.format("/api/events/%s", saved.getId()));
        return ResponseEntity.created(location).body(saved);
    }

    @PostMapping("/participants")
    public ResponseEntity<EventParticipant> addParticipant(@Valid @RequestBody EventParticipantRequest request) {
        var eventOpt = eventRepository.findById(request.getEventId());
        if (eventOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var participant = new EventParticipant();
        participant.setEvent(eventOpt.get());
        participant.setMemberId(request.getMemberId());
        participant.setRole(request.getRole());
        if (request.getRegisteredAt() != null)
            participant.setRegisteredAt(request.getRegisteredAt());

        var saved = participantRepository.save(participant);
        java.net.URI location = java.net.URI
                .create(String.format("/api/events/%s/participants/%s", request.getEventId(), saved.getId()));
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping("/{eventId}/participants")
    public ResponseEntity<List<EventParticipantResponse>> listParticipants(
           @PathVariable String eventId) {
        List<EventParticipant> participants = participantRepository.findByEventId(eventId);
        List<EventParticipantResponse> response = new ArrayList<>();
        for (EventParticipant p : participants) {
            response.add(new EventParticipantResponse(
                    p.getId(),
                    p.getEvent() != null ? p.getEvent().getId() : null,
                    p.getMemberId(),
                    p.getRole(),
                    p.getRegisteredAt(),
                    p.getCreatedAt(),
                    p.getUpdatedAt()
            ));
        }   
        return ResponseEntity.ok(new ArrayList<>());  
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Event>> listEventsForUser(@PathVariable String userId) {
        List<Event> events = eventRepository.findEventsForUser(userId);
        return ResponseEntity.ok(events);
    }
}
