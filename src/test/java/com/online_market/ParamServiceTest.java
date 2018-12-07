package com.online_market;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.online_market.dao.ParamDaoImpl;
import com.online_market.service.ParamServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class for testing ${@link com.online_market.entity.Param}
 *
 * @author Siarhei
 * @version 1.0
 */
public class ParamServiceTest {

    @Mock
    ParamDaoImpl paramDaoMock;

    @InjectMocks
    ParamServiceImpl paramService;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllCountries_returnsSetOfCountries() {

        //expected
        Set<String> expected = new HashSet<>();
        expected.add("Russia");
        expected.add("Belarus");

        //mock
        when(paramDaoMock.getAllCountries()).thenReturn(expected);

        //actual
        Set<String> actual = paramService.getAllCountries();

        //assert
        assertEquals(expected, actual);
    }

    @Test
    public void testGetAllAuthors_returnsSetOfCountries() {

        //expected
        Set<String> expected = new HashSet<>();
        expected.add("Van Gogh");
        expected.add("Malevich");

        //mock
        when(paramDaoMock.getAllAuthors()).thenReturn(expected);

        //actual
        Set<String> actual = paramService.getAllAuthors();

        //assert
        assertEquals(expected, actual);
    }
}
