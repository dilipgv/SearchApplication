package com.project.search.unit;

import com.project.search.index.TicketIndex;
import com.project.search.model.Ticket;
import com.project.search.utils.Generators;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TicketIndexTest {

    @BeforeEach
    void setUp(){

    }
//    Test set and get
    @Test
    void testContrsuctor(){
        TicketIndex ticketIndex = new TicketIndex();
        Assertions.assertEquals(ticketIndex.getIndexMap().size(),16);
    }

    @Test
    void testSet(){
        TicketIndex ticketIndex = new TicketIndex();
        Ticket ticket = Generators.getTicket();
        Assertions.assertDoesNotThrow( () -> {
            ticketIndex.set(ticket);
        });

        //Check couple of fields
        Assertions.assertEquals(ticketIndex.getIndexMap().get("_id").size(),1);
        Assertions.assertEquals(ticketIndex.getIndexMap().get("tags").size(),4);

    }

}
