package se.expleostockholm.signup.integrationtests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.repository.InvitationMapper;

import javax.annotation.Resource;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {DatabaseTests.Initializer.class})
class InvitationMapperTests extends DatabaseTests {

  @Resource
  private InvitationMapper invitationMapper;

  @Test
  void verifyGetInvitation(){
    Optional<Invitation> invitationOption = invitationMapper.getInvitationById(1L);
    assertTrue(invitationOption.isPresent());
    assertEquals(1L, invitationOption.get().getEvent_id());
  }

  @Test
  void verifySetAttendance(){
    invitationMapper.setAttendance(Attendance.MAYBE, 1L);
    Optional<Invitation> invitationOption = invitationMapper.getInvitationById(1L);
    assertTrue(invitationOption.isPresent());
    assertEquals(Attendance.MAYBE, invitationOption.get().getAttendance());
  }

}
