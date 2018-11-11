package com.online_market;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.online_market.dao.AddressDaoImpl;
import com.online_market.entity.Address;
import com.online_market.service.AddressServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for testing ${@link com.online_market.service.AddressService}
 * @author Siarhei
 * @version 1.0
 */
public class AddressServiceTest {

    @Mock
    private AddressDaoImpl addressDaoMock;

    @InjectMocks
    private AddressServiceImpl addressService;

    @Before
    public void setUp() throws Exception{

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void TestSaveAddress_void(){

        addressService.save(new Address());

        verify(addressDaoMock).save(any(Address.class));
    }

    @Test
    public void TestUpdateAddress_void(){

        addressService.save(new Address());

        verify(addressDaoMock).save(any(Address.class));
    }

    @Test
    public void testGettingAddressById_returnsUser(){

        //expected
        int id = 8;
        Address expected = new Address();
        expected.setAddressId(id);
        //mock
        when(addressDaoMock.getById(id)).thenReturn(expected);

        //call
        Address actual = addressService.getById(id);

        //assert
        assertEquals(actual, expected);
    }


    @Test
    public void testGettingUserById_returnsNullUser(){

        //expected
        int id = 1;
        Address expected = null;
        //mock
        when(addressDaoMock.getById(id)).thenReturn(expected);

        //call
        Address actual = addressService.getById(id);

        //assert
        assertEquals(actual, expected);
    }
}
