package com.project.search.unit;

import com.project.search.index.OrgIndex;
import com.project.search.index.TicketIndex;
import com.project.search.model.Organization;
import com.project.search.model.Ticket;
import com.project.search.utils.Generators;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrgIndexTest {

    @BeforeEach
    void setUp(){

    }
//    Test set and get
    @Test
    void testContrsuctor(){
        OrgIndex OrgIndex = new OrgIndex();
        Assertions.assertEquals(OrgIndex.getIndexMap().size(),9);
    }

    @Test
    void testSet(){
        OrgIndex OrgIndex = new OrgIndex();
        Organization org = Generators.getOrg();
        Assertions.assertDoesNotThrow( () -> {
            OrgIndex.set(org);
        });

        //Check couple of fields
        Assertions.assertEquals(OrgIndex.getIndexMap().get("_id").size(),1);
        Assertions.assertEquals(OrgIndex.getIndexMap().get("details").size(),1);
        Assertions.assertEquals(OrgIndex.getIndexMap().get("tags").size(),4);
        Assertions.assertEquals(OrgIndex.getIndexMap().get("domain_names").size(),4);

    }

}
