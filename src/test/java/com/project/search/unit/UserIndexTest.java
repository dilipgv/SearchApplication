package com.project.search.unit;

import com.project.search.index.OrgIndex;
import com.project.search.index.UserIndex;
import com.project.search.model.Organization;
import com.project.search.model.User;
import com.project.search.utils.Generators;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserIndexTest {

    @BeforeEach
    void setUp(){

    }
//    Test set and get
    @Test
    void testContrsuctor(){
        UserIndex userIndex = new UserIndex();
        Assertions.assertEquals(userIndex.getIndexMap().size(),19);
    }

    @Test
    void testSet(){
        UserIndex userIndex = new UserIndex();
        User user = Generators.getUser();
        Assertions.assertDoesNotThrow( () -> {
            userIndex.set(user);
        });

        //Check couple of fields
        Assertions.assertEquals(userIndex.getIndexMap().get("_id").size(),1);
        Assertions.assertEquals(userIndex.getIndexMap().get("active").size(),1);
        Assertions.assertEquals(userIndex.getIndexMap().get("tags").size(),4);

    }

}
