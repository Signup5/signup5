package se.expleostockholm.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import se.expleostockholm.signup.domain.Event;

public class HtmlEmailTemplate {

    @Autowired
    private Environment env;

    private final Event event;
    private String invitationEmail;
    private String acceptanceEmail;
    private final String HOST_URL = System.getenv("HOST_URL");

    public HtmlEmailTemplate(Event event) {
        this.event = event;
        invitationEmail();
        acceptanceEmail();
    }

    /**
     * Getter for invitation email.
     *
     * @return
     */
    public String getInvitationEmail() {
        return invitationEmail;
    }

    /**
     * Getter for acceptance email.
     *
     * @return
     */
    public String getAcceptanceEmail() {
        return acceptanceEmail;
    }

    /**
     * HTML-formatted Invitation email.
     */
    private void invitationEmail() {
        this.invitationEmail = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width\" />\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "    <title>Simple Transactional Email</title>\n" +
                "    <style>\n" +
                "      /* -------------------------------------\n" +
                "        INLINED WITH htmlemail.io/inline\n" +
                "    ------------------------------------- */\n" +
                "      /* -------------------------------------\n" +
                "        RESPONSIVE AND MOBILE FRIENDLY STYLES\n" +
                "    ------------------------------------- */\n" +
                "      @media only screen and (max-width: 620px) {\n" +
                "        table[class=\"body\"] h1 {\n" +
                "          font-size: 28px !important;\n" +
                "          margin-bottom: 10px !important;\n" +
                "        }\n" +
                "        table[class=\"body\"] p,\n" +
                "        table[class=\"body\"] ul,\n" +
                "        table[class=\"body\"] ol,\n" +
                "        table[class=\"body\"] td,\n" +
                "        table[class=\"body\"] span,\n" +
                "        table[class=\"body\"] a {\n" +
                "          font-size: 16px !important;\n" +
                "        }\n" +
                "        table[class=\"body\"] .wrapper,\n" +
                "        table[class=\"body\"] .article {\n" +
                "          padding: 10px !important;\n" +
                "        }\n" +
                "        table[class=\"body\"] .content {\n" +
                "          padding: 0 !important;\n" +
                "        }\n" +
                "        table[class=\"body\"] .container {\n" +
                "          padding: 0 !important;\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "        table[class=\"body\"] .main {\n" +
                "          border-left-width: 0 !important;\n" +
                "          border-radius: 0 !important;\n" +
                "          border-right-width: 0 !important;\n" +
                "        }\n" +
                "        table[class=\"body\"] .btn table {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "        table[class=\"body\"] .btn a {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "        table[class=\"body\"] .img-responsive {\n" +
                "          height: auto !important;\n" +
                "          max-width: 100% !important;\n" +
                "          width: auto !important;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      /* -------------------------------------\n" +
                "        PRESERVE THESE STYLES IN THE HEAD\n" +
                "    ------------------------------------- */\n" +
                "      @media all {\n" +
                "        .ExternalClass {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "        .ExternalClass,\n" +
                "        .ExternalClass p,\n" +
                "        .ExternalClass span,\n" +
                "        .ExternalClass font,\n" +
                "        .ExternalClass td,\n" +
                "        .ExternalClass div {\n" +
                "          line-height: 100%;\n" +
                "        }\n" +
                "        .apple-link a {\n" +
                "          color: inherit !important;\n" +
                "          font-family: inherit !important;\n" +
                "          font-size: inherit !important;\n" +
                "          font-weight: inherit !important;\n" +
                "          line-height: inherit !important;\n" +
                "          text-decoration: none !important;\n" +
                "        }\n" +
                "        #MessageViewBody a {\n" +
                "          color: inherit;\n" +
                "          text-decoration: none;\n" +
                "          font-size: inherit;\n" +
                "          font-family: inherit;\n" +
                "          font-weight: inherit;\n" +
                "          line-height: inherit;\n" +
                "        }\n" +
                "        .btn-primary table td:hover {\n" +
                "          background-color: #34495e !important;\n" +
                "        }\n" +
                "        .btn-primary a:hover {\n" +
                "          background-color: #34495e !important;\n" +
                "          border-color: #34495e !important;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body\n" +
                "    class=\"\"\n" +
                "    style=\"background-color: #f6f6f6; font-family: sans-serif; -webkit-font-smoothing: antialiased; font-size: 14px; line-height: 1.4; margin: 0; padding: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\"\n" +
                "  >\n" +
                "    <table\n" +
                "      border=\"0\"\n" +
                "      cellpadding=\"0\"\n" +
                "      cellspacing=\"0\"\n" +
                "      class=\"body\"\n" +
                "      style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background-color: #f6f6f6;\"\n" +
                "    >\n" +
                "      <tr>\n" +
                "        <td\n" +
                "          style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\"\n" +
                "        >\n" +
                "          &nbsp;\n" +
                "        </td>\n" +
                "        <td\n" +
                "          class=\"container\"\n" +
                "          style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; display: block; Margin: 0 auto; max-width: 580px; padding: 10px; width: 580px;\"\n" +
                "        >\n" +
                "          <div\n" +
                "            class=\"content\"\n" +
                "            style=\"box-sizing: border-box; display: block; Margin: 0 auto; max-width: 580px; padding: 10px;\"\n" +
                "          >\n" +
                "            <!-- START CENTERED WHITE CONTAINER -->\n" +
                "            <span\n" +
                "              class=\"preheader\"\n" +
                "              style=\"color: transparent; display: none; height: 0; max-height: 0; max-width: 0; opacity: 0; overflow: hidden; mso-hide: all; visibility: hidden; width: 0;\"\n" +
                "              >This is preheader text. Some clients will show this text as a\n" +
                "              preview.</span\n" +
                "            >\n" +
                "            <table\n" +
                "              class=\"main\"\n" +
                "              style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background: #ffffff; border-radius: 3px;\"\n" +
                "            >\n" +
                "              <!-- START MAIN CONTENT AREA -->\n" +
                "              <tr>\n" +
                "                <td\n" +
                "                  class=\"wrapper\"\n" +
                "                  style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; box-sizing: border-box; padding: 20px;\"\n" +
                "                >\n" +
                "                  <table\n" +
                "                    border=\"0\"\n" +
                "                    cellpadding=\"0\"\n" +
                "                    cellspacing=\"0\"\n" +
                "                    style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%;\"\n" +
                "                  >\n" +
                "                    <tr>\n" +
                "                      <td\n" +
                "                        style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\"\n" +
                "                      >\n" +
                "                        <h3\n" +
                "                          style=\"font-family: sans-serif; font-size: 18px; font-weight: bold; margin: 0; Margin-bottom: 15px;\"\n" +
                "                        >\n" +
                "                          " + event.getTitle() + "<!-- ---------Event Title----------- -->\n" +
                "                    </h3>\n" +
                "                        <p\n" +
                "                          style=\"font-family: sans-serif; font-size: 14px; font-weight: normal; margin: 0; Margin-bottom: 15px;\"\n" +
                "                        >\n" +
                "                        <b>" + event.getHost().getFirst_name() + " " + event.getHost().getLast_name() + "</b><!-- -----------------Host name--------------- -->  <b>(" + event.getHost().getEmail() + ")</b> has invited you to this event.<br/>\n" +
                "                        <br/>\n" +
                "\n" +
                "                         <b>Date of event: " + event.getDate_of_event() + "</b> <!-- ---------------date of event---------- --> <br/>\n" +
                "                         <b>Time of event: " + event.getTime_of_event() + "</b> <!-- ---------------time of event---------- --><br/>\n" +
                "                         <b>Description: " + event.getDescription() + "</b>  <!-- ---------------Description---------- --><br/>\n" +
                "                        </p>\n" +
                "                        <table\n" +
                "                          border=\"0\"\n" +
                "                          cellpadding=\"0\"\n" +
                "                          cellspacing=\"0\"\n" +
                "                          class=\"btn btn-primary\"\n" +
                "                          style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; box-sizing: border-box;\"\n" +
                "                        >\n" +
                "                          <tbody>\n" +
                "                            <tr>\n" +
                "                              <td\n" +
                "                                align=\"left\"\n" +
                "                                style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; padding-bottom: 15px;\"\n" +
                "                              >\n" +
                "                                <table\n" +
                "                                  border=\"0\"\n" +
                "                                  cellpadding=\"0\"\n" +
                "                                  cellspacing=\"0\"\n" +
                "                                  style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: auto;\"\n" +
                "                                >\n" +
                "                                  <tbody>\n" +
                "                                    <tr>\n" +
                "                                      <td\n" +
                "                                        style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; background-color: #3498db; border-radius: 5px; text-align: center;\"\n" +
                "                                      >\n" +
                "                                        <a\n" +
                "                                          href=\"" + HOST_URL + "\"\n" +
                "                                          target=\"_blank\"\n" +
                "                                          style=\"display: inline-block; color: #ffffff; background-color: #3498db; border: solid 1px #3498db; border-radius: 5px; box-sizing: border-box; cursor: pointer; text-decoration: none; font-size: 14px; font-weight: bold; margin: 0; padding: 12px 25px; text-transform: capitalize; border-color: #3498db;\"\n" +
                "                                          >Log in to accept invitation</a>\n" +
                "                                        \n" +
                "                                      </td>\n" +
                "                                    </tr>\n" +
                "                                  </tbody>\n" +
                "                                </table>\n" +
                "                              </td>\n" +
                "                            </tr>\n" +
                "                          </tbody>\n" +
                "                        </table>\n" +
                "                       \n" +
                "                       \n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "\n" +
                "              <!-- END MAIN CONTENT AREA -->\n" +
                "            </table>\n" +
                "\n" +
                "            <!-- START FOOTER -->\n" +
                "            <div\n" +
                "              class=\"footer\"\n" +
                "              style=\"clear: both; Margin-top: 10px; text-align: center; width: 100%;\"\n" +
                "            >\n" +
                "              <table\n" +
                "                border=\"0\"\n" +
                "                cellpadding=\"0\"\n" +
                "                cellspacing=\"0\"\n" +
                "                style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%;\"\n" +
                "              >\n" +
                "                <tr>\n" +
                "                  <td\n" +
                "                    class=\"content-block\"\n" +
                "                    style=\"font-family: sans-serif; vertical-align: top; padding-bottom: 10px; padding-top: 10px; font-size: 12px; color: #999999; text-align: center;\"\n" +
                "                  >\n" +
                "                    <span\n" +
                "                      class=\"apple-link\"\n" +
                "                      style=\"color: #999999; font-size: 12px; text-align: center;\"\n" +
                "                      >Signup5</span\n" +
                "                    >\n" +
                "                    <br />\n" +
                "                  \n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                  \n" +
                "                    \n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "              </table>\n" +
                "            </div>\n" +
                "            <!-- END FOOTER -->\n" +
                "\n" +
                "            <!-- END CENTERED WHITE CONTAINER -->\n" +
                "          </div>\n" +
                "        </td>\n" +
                "        <td\n" +
                "          style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\"\n" +
                "        >\n" +
                "          &nbsp;\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "    </table>\n" +
                "  </body>\n" +
                "</html>\n";
    }


    private void acceptanceEmail() {

        this.acceptanceEmail = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width\" />\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "    <title>Simple Transactional Email</title>\n" +
                "    <style>\n" +
                "      /* -------------------------------------\n" +
                "        INLINED WITH htmlemail.io/inline\n" +
                "    ------------------------------------- */\n" +
                "      /* -------------------------------------\n" +
                "        RESPONSIVE AND MOBILE FRIENDLY STYLES\n" +
                "    ------------------------------------- */\n" +
                "      @media only screen and (max-width: 620px) {\n" +
                "        table[class=\"body\"] h1 {\n" +
                "          font-size: 28px !important;\n" +
                "          margin-bottom: 10px !important;\n" +
                "        }\n" +
                "        table[class=\"body\"] p,\n" +
                "        table[class=\"body\"] ul,\n" +
                "        table[class=\"body\"] ol,\n" +
                "        table[class=\"body\"] td,\n" +
                "        table[class=\"body\"] span,\n" +
                "        table[class=\"body\"] a {\n" +
                "          font-size: 16px !important;\n" +
                "        }\n" +
                "        table[class=\"body\"] .wrapper,\n" +
                "        table[class=\"body\"] .article {\n" +
                "          padding: 10px !important;\n" +
                "        }\n" +
                "        table[class=\"body\"] .content {\n" +
                "          padding: 0 !important;\n" +
                "        }\n" +
                "        table[class=\"body\"] .container {\n" +
                "          padding: 0 !important;\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "        table[class=\"body\"] .main {\n" +
                "          border-left-width: 0 !important;\n" +
                "          border-radius: 0 !important;\n" +
                "          border-right-width: 0 !important;\n" +
                "        }\n" +
                "        table[class=\"body\"] .btn table {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "        table[class=\"body\"] .btn a {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "        table[class=\"body\"] .img-responsive {\n" +
                "          height: auto !important;\n" +
                "          max-width: 100% !important;\n" +
                "          width: auto !important;\n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      /* -------------------------------------\n" +
                "        PRESERVE THESE STYLES IN THE HEAD\n" +
                "    ------------------------------------- */\n" +
                "      @media all {\n" +
                "        .ExternalClass {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "        .ExternalClass,\n" +
                "        .ExternalClass p,\n" +
                "        .ExternalClass span,\n" +
                "        .ExternalClass font,\n" +
                "        .ExternalClass td,\n" +
                "        .ExternalClass div {\n" +
                "          line-height: 100%;\n" +
                "        }\n" +
                "        .apple-link a {\n" +
                "          color: inherit !important;\n" +
                "          font-family: inherit !important;\n" +
                "          font-size: inherit !important;\n" +
                "          font-weight: inherit !important;\n" +
                "          line-height: inherit !important;\n" +
                "          text-decoration: none !important;\n" +
                "        }\n" +
                "        #MessageViewBody a {\n" +
                "          color: inherit;\n" +
                "          text-decoration: none;\n" +
                "          font-size: inherit;\n" +
                "          font-family: inherit;\n" +
                "          font-weight: inherit;\n" +
                "          line-height: inherit;\n" +
                "        }\n" +
                "        .btn-primary table td:hover {\n" +
                "          background-color: #34495e !important;\n" +
                "        }\n" +
                "        .btn-primary a:hover {\n" +
                "          background-color: #34495e !important;\n" +
                "          border-color: #34495e !important;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body\n" +
                "    class=\"\"\n" +
                "    style=\"background-color: #f6f6f6; font-family: sans-serif; -webkit-font-smoothing: antialiased; font-size: 14px; line-height: 1.4; margin: 0; padding: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\"\n" +
                "  >\n" +
                "    <table\n" +
                "      border=\"0\"\n" +
                "      cellpadding=\"0\"\n" +
                "      cellspacing=\"0\"\n" +
                "      class=\"body\"\n" +
                "      style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background-color: #f6f6f6;\"\n" +
                "    >\n" +
                "      <tr>\n" +
                "        <td\n" +
                "          style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\"\n" +
                "        >\n" +
                "          &nbsp;\n" +
                "        </td>\n" +
                "        <td\n" +
                "          class=\"container\"\n" +
                "          style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; display: block; Margin: 0 auto; max-width: 580px; padding: 10px; width: 580px;\"\n" +
                "        >\n" +
                "          <div\n" +
                "            class=\"content\"\n" +
                "            style=\"box-sizing: border-box; display: block; Margin: 0 auto; max-width: 580px; padding: 10px;\"\n" +
                "          >\n" +
                "            <!-- START CENTERED WHITE CONTAINER -->\n" +
                "            <span\n" +
                "              class=\"preheader\"\n" +
                "              style=\"color: transparent; display: none; height: 0; max-height: 0; max-width: 0; opacity: 0; overflow: hidden; mso-hide: all; visibility: hidden; width: 0;\"\n" +
                "              >This is preheader text. Some clients will show this text as a\n" +
                "              preview.</span\n" +
                "            >\n" +
                "            <table\n" +
                "              class=\"main\"\n" +
                "              style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background: #ffffff; border-radius: 3px;\"\n" +
                "            >\n" +
                "              <!-- START MAIN CONTENT AREA -->\n" +
                "              <tr>\n" +
                "                <td\n" +
                "                  class=\"wrapper\"\n" +
                "                  style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; box-sizing: border-box; padding: 20px;\"\n" +
                "                >\n" +
                "                  <table\n" +
                "                    border=\"0\"\n" +
                "                    cellpadding=\"0\"\n" +
                "                    cellspacing=\"0\"\n" +
                "                    style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%;\"\n" +
                "                  >\n" +
                "                    <tr>\n" +
                "                      <td\n" +
                "                        style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\"\n" +
                "                      >\n" +
                "                        <h3\n" +
                "                          style=\"font-family: sans-serif; font-size: 18px; font-weight: bold; margin: 0; Margin-bottom: 15px;\"\n" +
                "                        >\n" +
                "                          " + event.getTitle() + "<!-- ---------Event Title----------- -->\n" +
                "                       </h3>\n" +
                "                        <p\n" +
                "                          style=\"font-family: sans-serif; font-size: 14px; font-weight: normal; margin: 0; Margin-bottom: 15px;\"\n" +
                "                        >\n" +
                "                        <b>" + " You have accepted the invitation to this event!<br/><br/>Open the attached invitation file to add the event to your personal calendar.\n" +
                "                        <br/><br/>\n" +
                "\n" +
                "                         <b>Date of event: " + event.getDate_of_event() + "</b> <!-- ---------------date of event---------- --> <br/>\n" +
                "                         <b>Time of event: " + event.getTime_of_event() + "</b> <!-- ---------------time of event---------- --><br/>\n" +
                "                         <b>Description: " + event.getDescription() + "</b>  <!-- ---------------Description---------- --><br/>\n" +
                "                         <br/>\n" +
                "                         <br/>\n" +
                "                        </p>\n" +
                "                        <table\n" +
                "                          border=\"0\"\n" +
                "                          cellpadding=\"0\"\n" +
                "                          cellspacing=\"0\"\n" +
                "                          class=\"btn btn-primary\"\n" +
                "                          style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; box-sizing: border-box;\"\n" +
                "                        >\n" +
                "                          <tbody>\n" +
                "                            <tr>\n" +
                "                              <td\n" +
                "                                align=\"left\"\n" +
                "                                style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; padding-bottom: 15px;\"\n" +
                "                              >\n" +
                "                                <table\n" +
                "                                  border=\"0\"\n" +
                "                                  cellpadding=\"0\"\n" +
                "                                  cellspacing=\"0\"\n" +
                "                                  style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: auto;\"\n" +
                "                                >\n" +
                "                                  <tbody>\n" +
                "                                    <tr>\n" +
                "                                      <td\n" +
                "                                        style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; background-color: #3498db; border-radius: 5px; text-align: center;\"\n" +
                "                                      >\n" +
                "                                      </td>\n" +
                "                                    </tr>\n" +
                "                                  </tbody>\n" +
                "                                </table>\n" +
                "                              </td>\n" +
                "                            </tr>\n" +
                "                          </tbody>\n" +
                "                        </table>\n" +
                "                       \n" +
                "                       \n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "\n" +
                "              <!-- END MAIN CONTENT AREA -->\n" +
                "            </table>\n" +
                "\n" +
                "            <!-- START FOOTER -->\n" +
                "            <div\n" +
                "              class=\"footer\"\n" +
                "              style=\"clear: both; Margin-top: 10px; text-align: center; width: 100%;\"\n" +
                "            >\n" +
                "              <table\n" +
                "                border=\"0\"\n" +
                "                cellpadding=\"0\"\n" +
                "                cellspacing=\"0\"\n" +
                "                style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%;\"\n" +
                "              >\n" +
                "                <tr>\n" +
                "                  <td\n" +
                "                    class=\"content-block\"\n" +
                "                    style=\"font-family: sans-serif; vertical-align: top; padding-bottom: 10px; padding-top: 10px; font-size: 12px; color: #999999; text-align: center;\"\n" +
                "                  >\n" +
                "                    <span\n" +
                "                      class=\"apple-link\"\n" +
                "                      style=\"color: #999999; font-size: 12px; text-align: center;\"\n" +
                "                      >Signup5</span\n" +
                "                    >\n" +
                "                    <br />\n" +
                "                  \n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                  \n" +
                "                    \n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "              </table>\n" +
                "            </div>\n" +
                "            <!-- END FOOTER -->\n" +
                "\n" +
                "            <!-- END CENTERED WHITE CONTAINER -->\n" +
                "          </div>\n" +
                "        </td>\n" +
                "        <td\n" +
                "          style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\"\n" +
                "        >\n" +
                "          &nbsp;\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "    </table>\n" +
                "  </body>\n" +
                "</html>\n";
    }
}
