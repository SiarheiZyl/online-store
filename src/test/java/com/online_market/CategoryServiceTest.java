package com.online_market;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.online_market.dao.CategoryDaoImpl;
import com.online_market.entity.Category;
import com.online_market.service.CategoryServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for testing ${@link com.online_market.service.CategoryServiceImpl}
 * @author Siarhei
 * @version 1.0
 */
public class CategoryServiceTest {

    @Mock
    private CategoryDaoImpl categoryDaoMock;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Before
    public void setUp() throws Exception{

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testListOfCategories_returnsAllCategoties(){

        //expected
        List<Category> expected = new ArrayList<>();
        expected.add(new Category());
        expected.add(new Category());


        //mock
        when(categoryDaoMock.listCategories()).thenReturn(expected);

        //actual
        List<Category> actual = categoryService.listCategories();

        //assert
        assertEquals(expected, actual);
    }

    @Test
    public void testListOfCategories_returnsNull(){

        //expected
        List<Category> expected = null;


        //mock
        when(categoryDaoMock.listCategories()).thenReturn(expected);

        //actual
        List<Category> actual = categoryService.listCategories();

        //assert
        assertEquals(expected, actual);
    }

    @Test
    public void testSaveCategory_void(){

        String categName="category";

        categoryService.save(categName);


        verify(categoryDaoMock).saveOrUpdate(any(Category.class));
    }
}
