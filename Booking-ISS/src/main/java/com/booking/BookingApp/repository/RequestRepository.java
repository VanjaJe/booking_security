package com.booking.BookingApp.repository;

import com.booking.BookingApp.domain.*;
import com.booking.BookingApp.domain.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    Collection<Request> findByStatus(RequestStatus status);

    Collection<Request> findAllByStatusAndGuest_Id(RequestStatus status, Long id);

    Collection<Request> findAllByStatusAndAccommodation_Host_IdAndGuest_Id(RequestStatus status, Long hostId, Long guestId);

    Collection<Request> findByStatusAndAccommodation_Name(RequestStatus status, String accommodationName);

    Collection<Request> findByStatusAndAccommodation_NameAndTimeSlot_StartDateLessThanEqualAndTimeSlot_EndDateGreaterThanEqual(RequestStatus status,String accommodationName,LocalDate end,LocalDate begin);

    Collection<Request> findByAccommodation_Host_Id(Long id);

    @Query("SELECT r FROM Request r WHERE r.status = 'ACCEPTED' AND r.timeSlot.startDate > :currentDateTime AND r.guest = :guest")
    Collection<Request> findActiveReservationsForGuest(@Param("currentDateTime") LocalDate currentDateTime, @Param("guest") Guest guest);

    @Query("SELECT r FROM Request r WHERE r.status = 'ACCEPTED' AND r.timeSlot.startDate > :currentDateTime AND r.accommodation = :accommodation")
    Collection<Request> findAllActiveReservationsForAccommodation(@Param("currentDateTime") LocalDate currentDateTime, @Param("accommodation") Accommodation accommodation);

    @Query("SELECT r FROM Request r WHERE r.timeSlot.startDate > :currentDateTime AND r.guest = :guest")
    Collection<Request> findFutureRequestsForGuest(@Param("currentDateTime") LocalDate currentDateTime, @Param("guest") Guest guest);

    @Query("SELECT r FROM Request r JOIN r.accommodation a JOIN a.host h WHERE r.status = 'ACCEPTED' AND r.timeSlot.startDate > :currentDateTime AND h = :host")
    Collection<Request> findActiveReservationsForHost(@Param("currentDateTime") LocalDate currentDateTime, @Param("host") Host host);

    Collection<Request> findByAccommodation_Host(Host host);

    Collection<Request> findByAccommodation_Id(Long id);

    Collection<Request> findByGuest_Id(Long id);
  
    Collection<Request> findByStatusAndAccommodation_Id(RequestStatus status, Long id);

    @Query("SELECT r FROM Request r WHERE " +
            "(:hostId is null or r.accommodation.host.id = :hostId) " +
            "and (:status is null or r.status = :status) " +
            "and (COALESCE(:begin, r.timeSlot.startDate) <= r.timeSlot.endDate) " +
            "and (COALESCE(:end, r.timeSlot.endDate) >= r.timeSlot.startDate) " +
            "and (:accommodationName is null or r.accommodation.name like %:accommodationName%)")
    Collection<Request> findAllForHost(
            @Param("hostId") Long hostId,
            @Param("status") RequestStatus status,
            @Param("begin") LocalDate begin,
            @Param("end") LocalDate end,
            @Param("accommodationName") String accommodationName
    );

    @Query("SELECT r FROM Request r WHERE " +
            "(:guestId is null or r.guest.id = :guestId) " +
            "and (:status is null or r.status = :status) " +
            "and (COALESCE(:begin, r.timeSlot.startDate) <= r.timeSlot.endDate) " +
            "and (COALESCE(:end, r.timeSlot.endDate) >= r.timeSlot.startDate) " +
            "and (:accommodationName is null or r.accommodation.name like %:accommodationName%)")
    Collection<Request> findAllForGuest(
            @Param("guestId") Long guestId,
            @Param("status") RequestStatus status,
            @Param("begin") LocalDate begin,
            @Param("end") LocalDate end,
            @Param("accommodationName") String accommodationName
    );

    @Query("SELECT r FROM Request r " +
            "WHERE r.accommodation.name like %:accommodationName% " +
            "AND r.status = 'ACCEPTED' " +
            "AND (YEAR(r.timeSlot.startDate) = :year OR YEAR(r.timeSlot.endDate) = :year)")
    Collection<Request> findAllByAccommodationNameAndYear(
            @Param("accommodationName") String accommodationName,
            @Param("year") int year
    );

    @Query("SELECT r FROM Request r " +
            "WHERE r.accommodation = :accommodation " +
            "AND (" +
            "   r.timeSlot.startDate BETWEEN :startDate AND :endDate " +
            "   OR r.timeSlot.endDate BETWEEN :startDate AND :endDate " +
            "   OR :startDate BETWEEN r.timeSlot.startDate AND r.timeSlot.endDate " +
            "   OR :endDate BETWEEN r.timeSlot.startDate AND r.timeSlot.endDate" +
            ")")
    Collection<Request> findAllByAccommodationAndTimeSlot(
            @Param("accommodation") Accommodation accommodation,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

}