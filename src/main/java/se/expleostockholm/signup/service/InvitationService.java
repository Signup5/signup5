package se.expleostockholm.signup.service;

import org.springframework.stereotype.Service;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.exception.InvitationNotFoundException;
import se.expleostockholm.signup.repository.InvitationMapper;

@Service
public class InvitationService {

  private InvitationMapper invitationMapper;

  public InvitationService(InvitationMapper invitationMapper) {
    this.invitationMapper = invitationMapper;
  }

  public Invitation getInvitationById(Long id) {
    return invitationMapper.getInvitationById(id).orElseThrow(() -> new InvitationNotFoundException("No invitation found"));
  }
}