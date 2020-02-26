package se.expleostockholm.signup.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.exception.InvitationAlreadyExistException;
import se.expleostockholm.signup.exception.InvitationNotFoundException;
import se.expleostockholm.signup.exception.SetAttendanceException;
import se.expleostockholm.signup.repository.InvitationMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class InvitationService {

    private final InvitationMapper invitationMapper;
    private final PersonService personService;

    /**
     * Method for saving all Invitations, and the guests associated with each Invitation, for an Event.
     * <p>
     * Accepts two arguments; a collection of Invitations and a Long representing the Event Id.
     * <p>>
     *
     * @param   invitations   a list of Invitations with an associated Guest for each Invitation
     * @param   eventId       a Long value representing the Event Id that holds all Invitations
     */
    public void saveInvitations(List<Invitation> invitations, Long eventId) {
        invitations.forEach(invitation -> {
            invitation.setEvent_id(eventId);
            invitation.setGuest(personService.savePerson(invitation.getGuest()));
            invitationExists(invitation);
            invitationMapper.saveInvitation(invitation);
        });
    }

    /**
     * Method that checks if an Person already have an Invitation to a particular Event.
     * <p>
     * Takes an Invitation as an argument and checks if a Person (Guest) already has been invited to an Event.
     *
     * @param   invitation  an Invitation that has a Guest and an Event to be checked against the database
     */
    public void invitationExists(Invitation invitation) {
        if (invitationMapper.invitationExists(invitation) == 1) {
            throw new InvitationAlreadyExistException(invitation.getGuest().getEmail() + " has already been invited to this event!");
        }
    }

    /**
     * Method for updating an invited Guest's Attendance status.
     * <p>
     * Accepts an Attendance and Invitation Id arguments for updating the Attendance status.
     *
     * @param   attendance      new Attendance status to be updated
     * @param   invitation_id   Id for the Invitation
     */
    public void setAttendance(Attendance attendance, Long invitation_id) {
        if (invitationMapper.setAttendance(attendance, invitation_id) != 1) {
            throw new SetAttendanceException("Something went wrong while updating attendance.");
        }
    }

    public List<Invitation> getAllInvitations() {
        List<Invitation> invitations = invitationMapper.getAllInvitations();
        if (invitations.size() == 0) {
            throw new InvitationNotFoundException("No invitations found!");
        }
        return invitations;
    }

    /**
     * Method for retrieving an Invitation from the Database based on its Id.
     * <p>
     * Accepts a Long as an argument representing the Invitation Id in the database.
     *
     * @param   id  a Long value representing an Invitation Id
     * @return      an Invitation if Id was found in the database
     */
    public Invitation getInvitationById(Long id) {
        return invitationMapper.getInvitationById(id).orElseThrow(() -> new InvitationNotFoundException("No invitation found!"));
    }

    /**
     * Method for retrieving all Invitations for an Event based on the Event Id.
     * <p>
     * Accepts a Long as an argument representing the Event Id in the database.
     *
     * @param   id  a Long value representing an Event Id
     * @return      a list of Invitations if Event Id was found in the database
     */
    public List<Invitation> getInvitationsByEventId(Long id) {
        List<Invitation> invitations = invitationMapper.getInvitationsByEventId(id);
        if (invitations.size() == 0) {
            throw new InvitationNotFoundException("No invitations found for event");
        }
        return invitations;
    }
}
