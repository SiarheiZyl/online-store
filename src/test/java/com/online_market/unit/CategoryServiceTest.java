package com.online_market;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.online_market.dao.CategoryDaoImpl;
import com.online_market.dao.ItemDaoImpl;
import com.online_market.entity.Category;
import com.online_market.entity.Item;
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
 *
 * @author Siarhei
 * @version 1.0
 */
public class CategoryServiceTest {

    @Mock
    private CategoryDaoImpl categoryDaoMock;

    @Mock
    private ItemDaoImpl itemDaoMock;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testListOfCategories_returnsAllCategoties() {

        //expected
        List<Category> expected = new ArrayList<>();
        expected.add(new Category());
        expected.add(new Category());


        //mock
        when(categoryDaoMock.getAll("Category")).thenReturn(expected);

        //actual
        List<Category> actual = categoryService.listCategories();

        //assert
        assertEquals(expected, actual);
    }

    @Test
    public void testListOfCategories_returnsNull() {

        //expected
        List<Category> expected = null;

        //mock
        when(categoryDaoMock.getAll("Category")).thenReturn(expected);

        //actual
        List<Category> actual = categoryService.listCategories();

        //assert
        assertEquals(expected, actual);
    }

    @Test
    public void testSaveCategory_void() {

        String categName = "category";

        categoryService.save(categName);

        verify(categoryDaoMock).saveOrUpdate(any(Category.class));
    }

    @Test
    public void testChangeVisibilityOfItem_void() {

        //data
        Category category = new Category();
        String categName = "Category";
        category.setCategoryName(categName);

        List<Item> itemList = new ArrayList<>();
        Item item = new Item();
        item.setCategory(category);
        itemList.add(item);

        when(categoryDaoMock.findByName(categName)).thenReturn(category);
        when(itemDaoMock.getAll("Item")).thenReturn(itemList);

        categoryService.changeVisibilityOfCategory(categName);

        verify(categoryDaoMock).update(category);
    }
}
