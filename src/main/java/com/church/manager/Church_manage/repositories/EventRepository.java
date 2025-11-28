package com.church.manager.Church_manage.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.church.manager.Church_manage.entities.Event;

public interface EventRepository extends JpaRepository<Event, String>, JpaSpecificationExecutor<Event> {

    @Query("select distinct e from Event e left join e.participants p where e.createdBy = :userId or p.memberId = :userId")
    List<Event> findEventsForUser(@Param("userId") String userId);
}
