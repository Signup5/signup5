package se.expleostockholm.signup.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.domain.Response;
import se.expleostockholm.signup.service.EmailService;
import se.expleostockholm.signup.service.EventService;
import se.expleostockholm.signup.service.InvitationService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@AllArgsConstructor
public class Mutation implements GraphQLMutationResolver {

    private final EventService eventService;
    private final InvitationService invitationService;
    private final EmailService emailService;


    /**
     * Method for updating an invited Guest's Attendance status.
     * <p>
     * Accepts an Attendance and Invitation Id arguments for updating the Attendance status and returns
     * a Response if the Attendance was successfully update or not.
     *
     * @param   attendance      new Attendance status to be updated
     * @param   invitation_id   Id for the Invitation
     */
    public Response setAttendance(Attendance attendance, Long invitation_id) {

        invitationService.setAttendance(attendance, invitation_id);

        if (attendance == Attendance.ATTENDING) {
            Invitation invitation = invitationService.getInvitationById(invitation_id);

            Event event = eventService.getEventById(invitation.getEvent_id());

            MimeMessage message = emailService.createAcceptanceEmail(invitation.getGuest().getEmail(), event);
            try {
                emailService.sendMail(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        return Response.builder()
                .message("Attendance was successfully updated!")
                .build();
    }

    /**
     * Method for creating an Event.
     *<p>
     * Accepts an Event as an argument from the frontend and returns a Response whether Event was successfully
     * saved or not.
     *
     * @param   event   an Event with values coming from the frontend
     * @return          a Response with info if Event was saved or not
     */
    public Response createEvent(Event event) {
        Event test = eventService.createNewEvent(event);


        event.getInvitations().forEach(invitation -> {
            MimeMessage message = emailService.createInvitationEmail(invitation.getGuest().getEmail(), test);
            try {
                emailService.sendMail(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });



        return Response.builder()
                .message("Event was successfully saved!")
                .id(event.getId())
                .build();
    }
}
