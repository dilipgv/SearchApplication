package com.project.search.unit;

import com.project.search.adapter.data.FileDataLoad;
import com.project.search.common.IConstants;
import com.project.search.index.OrgIndex;
import com.project.search.index.TicketIndex;
import com.project.search.index.Tokens;
import com.project.search.index.UserIndex;
import com.project.search.initialize.DataLoad;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class DataLoadTest {
  @Mock FileDataLoad fileDataLoad;
  @Mock TicketIndex ticketIndex;
  @Mock OrgIndex orgIndex;
  @Mock UserIndex userIndex;
  @Mock Tokens tokens;
  @InjectMocks DataLoad dataLoad;
  //  @Test
  //  void contextLoads() {}

  @BeforeEach
  public void init() throws IOException {
    MockitoAnnotations.initMocks(this);
    Mockito.when(fileDataLoad.load(IConstants.USER))
        .thenReturn(
            "[{\n"
                + "    \"_id\": 1,\n"
                + "    \"url\": \"http://initech.zendesk.com/api/v2/users/1.json\",\n"
                + "    \"external_id\": \"74341f74-9c79-49d5-9611-87ef9b6eb75f\",\n"
                + "    \"name\": \"Francisca Rasmussen\",\n"
                + "    \"alias\": \"Miss Coffey\",\n"
                + "    \"created_at\": \"2016-04-15T05:19:46 -10:00\",\n"
                + "    \"active\": true,\n"
                + "    \"verified\": true,\n"
                + "    \"shared\": false,\n"
                + "    \"locale\": \"en-AU\",\n"
                + "    \"timezone\": \"Sri Lanka\",\n"
                + "    \"last_login_at\": \"2013-08-04T01:03:27 -10:00\",\n"
                + "    \"email\": \"coffeyrasmussen@flotonic.com\",\n"
                + "    \"phone\": \"8335-422-718\",\n"
                + "    \"signature\": \"Don't Worry Be Happy!\",\n"
                + "    \"organization_id\": 119,\n"
                + "    \"tags\": [\n"
                + "      \"Springville\",\n"
                + "      \"Sutton\",\n"
                + "      \"Hartsville/Hartley\",\n"
                + "      \"Diaperville\"\n"
                + "    ],\n"
                + "    \"suspended\": true,\n"
                + "    \"role\": \"admin\"\n"
                + "  }]");

    Mockito.when(fileDataLoad.load(IConstants.TICKET))
        .thenReturn(
            "[ {\n"
                + "    \"_id\": \"436bf9b0-1147-4c0a-8439-6f79833bff5b\",\n"
                + "    \"url\": \"http://initech.zendesk.com/api/v2/tickets/436bf9b0-1147-4c0a-8439-6f79833bff5b.json\",\n"
                + "    \"external_id\": \"9210cdc9-4bee-485f-a078-35396cd74063\",\n"
                + "    \"created_at\": \"2016-04-28T11:19:34 -10:00\",\n"
                + "    \"type\": \"incident\",\n"
                + "    \"subject\": \"A Catastrophe in Korea (North)\",\n"
                + "    \"description\": \"Nostrud ad sit velit cupidatat laboris ipsum nisi amet laboris ex exercitation amet et proident. Ipsum fugiat aute dolore tempor nostrud velit ipsum.\",\n"
                + "    \"priority\": \"high\",\n"
                + "    \"status\": \"pending\",\n"
                + "    \"submitter_id\": 38,\n"
                + "    \"assignee_id\": 24,\n"
                + "    \"organization_id\": 116,\n"
                + "    \"tags\": [\n"
                + "      \"Ohio\",\n"
                + "      \"Pennsylvania\",\n"
                + "      \"American Samoa\",\n"
                + "      \"Northern Mariana Islands\"\n"
                + "    ],\n"
                + "    \"has_incidents\": false,\n"
                + "    \"due_at\": \"2016-07-31T02:37:50 -10:00\",\n"
                + "    \"via\": \"web\"\n"
                + "  }]");
    Mockito.when(fileDataLoad.load(IConstants.ORGANIZATION))
        .thenReturn(
            "[ {\n"
                + "    \"_id\": 101,\n"
                + "    \"url\": \"http://initech.zendesk.com/api/v2/organizations/101.json\",\n"
                + "    \"external_id\": \"9270ed79-35eb-4a38-a46f-35725197ea8d\",\n"
                + "    \"name\": \"Enthaze\",\n"
                + "    \"domain_names\": [\n"
                + "      \"kage.com\",\n"
                + "      \"ecratic.com\",\n"
                + "      \"endipin.com\",\n"
                + "      \"zentix.com\"\n"
                + "    ],\n"
                + "    \"created_at\": \"2016-05-21T11:10:28 -10:00\",\n"
                + "    \"details\": \"MegaCorp\",\n"
                + "    \"shared_tickets\": false,\n"
                + "    \"tags\": [\n"
                + "      \"Fulton\",\n"
                + "      \"West\",\n"
                + "      \"Rodriguez\",\n"
                + "      \"Farley\"\n"
                + "    ]\n"
                + "  }]");
  }

  @Test
  void dataLoad() {
    Assertions.assertDoesNotThrow(
        () -> {
          dataLoad.init();
        });
  }
}
