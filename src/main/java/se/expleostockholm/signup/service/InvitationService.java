package se.expleostockholm.signup.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.exception.AttendanceException;
import se.expleostockholm.signup.exception.EventException;
import se.expleostockholm.signup.exception.InvitationException;
import se.expleostockholm.signup.repository.EventMapper;
import se.expleostockholm.signup.repository.InvitationMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class InvitationService {

    private final InvitationMapper invitationMapper;
    private final EventMapper eventMapper;
    private final PersonService personService;
    private final EmailService emailService;


    /**
     * Method for saving all Invitations, and the guests associated with each Invitation, for an Event.
     * <p>
     * Accepts two arguments; a collection of Invitations and a Long representing the Event Id.
     * <p>>
     *
     * @param invitations a list of Invitations with an associated Guest for each Invitation
     * @param eventId     a Long value representing the Event Id that holds all Invitations
     */
    public void saveInvitations(List<Invitation> invitations, Long eventId) {
        invitations.forEach(invitation -> {
            invitation.setEvent_id(eventId);
            invitation.setGuest(personService.savePerson(invitation.getGuest()));
            invitationExists(invitation);
            if (invitationMapper.saveInvitation(invitation) == 0)
                throw new InvitationException("Something went wrong while saving invitation");
        });
    }

    /**
     * Method that checks if an Person already have an Invitation to a particular Event.
     * <p>
     * Takes an Invitation as an argument and checks if a Person (Guest) already has been invited to an Event.
     *
     * @param invitation an Invitation that has a Guest and an Event to be checked against the database
     */
    public void invitationExists(Invitation invitation) {
        if (invitationMapper.invitationExists(invitation) == 1)
            throw new InvitationException(invitation.getGuest().getEmail() + " has already been invited to this event!");
    }

    /**
     * Method for updating an invited Guest's Attendance status.
     * <p>
     * Accepts an Attendance and Invitation Id arguments for updating the Attendance status.
     * <p>
     * If Guest sets their attendance to "ATTENDING" an email is sent out with a ICS Calendar attachment
     * for the recipient to update their own calendar.
     *
     * @param attendance    new Attendance status to be updated
     * @param invitation_id Id for the Invitation
     */
    public void setAttendance(Attendance attendance, Long invitation_id) {

        if (invitationMapper.setAttendance(attendance, invitation_id) != 1)
            throw new AttendanceException("Something went wrong while updating attendance.");

        Invitation invitation = getInvitationById(invitation_id);
        Event event = eventMapper.getEventById(invitation.getEvent_id()).orElseThrow(() -> new EventException("No event found!"));

        if (attendance == Attendance.ATTENDING)
            emailService.sendEmailWithCalendarAttachment(invitation.getGuest().getEmail(), event);
    }


    public List<Invitation> getAllInvitations() {
        return invitationMapper.getAllInvitations();
    }

    /**
     * Method for retrieving an Invitation from the Database based on its Id.
     * <p>
     * Accepts a Long as an argument representing the Invitation Id in the database.
     *
     * @param id a Long value representing an Invitation Id
     * @return an Invitation if Id was found in the database
     */
    public Invitation getInvitationById(Long id) {
        return invitationMapper.getInvitationById(id).orElseThrow(() -> new InvitationException("No invitation found!"));
    }

    /**
     * Method for retrieving all Invitations for an Event based on the Event Id.
     * <p>
     * Accepts a Long as an argument representing the Event Id in the database.
     *
     * @param id a Long value representing an Event Id
     * @return a list of Invitations if Event Id was found in the database
     */
    public List<Invitation> getInvitationsByEventId(Long id) {
        return invitationMapper.getInvitationsByEventId(id);
    }

    /**
     * Fectching all Invitations from Guest.
     *
     * @param id
     * @return list of Invitations
     */
    public List<Invitation> getInvitationsByGuestId(Long id) {
        return invitationMapper.getInvitationsByGuestId(id);
    }

    /**
     * Fetch upcoming and unreplied Invitations for Guest.
     *
     * @param id
     * @return list of Invitations
     */
    public List<Invitation> getUpcomingUnRepliedInvitationsByGuestId(Long id) {
        return invitationMapper.getUpcomingUnRepliedInvitationsByGuestId(id);
    }

    /**
     * Delete one or more Invitations
     *
     * @param invitationsToRemove
     */
    public void deleteInvitations(List<Invitation> invitationsToRemove) {
        try {
            invitationsToRemove.forEach(i -> invitationMapper.removeInvitationById(i.getId()));
        } catch (Exception ex) {
            throw new InvitationException("Deleting invitation failed!");
        }

    }
}