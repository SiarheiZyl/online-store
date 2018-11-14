package com.online_market;

import com.online_market.dao.CategoryDaoImpl;
import com.online_market.dao.ItemDaoImpl;
import com.online_market.dao.ParamDaoImpl;
import com.online_market.entity.Category;
import com.online_market.entity.Item;
import com.online_market.entity.Param;
import com.online_market.service.ItemServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Class for testing ${@link com.online_market.service.ItemServiceImpl}
 * @author Siarhei
 * @version 1.0
 */
public class ItemServiceTest {

    @Mock
    private ItemDaoImpl itemDaoMock;

    @Mock
    private ParamDaoImpl paramDaoMock;

    @Mock
    private CategoryDaoImpl categoryDaoMock;

    @InjectMocks
    private ItemServiceImpl itemService;

    @Before
    public void setUp() throws Exception{

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testItemList_returnsListOfItems(){

        //expected
        List<Item> expected = new ArrayList<>();
        expected.add(new Item());
        expected.add(new Item());

        //mock
        when(itemDaoMock.getAll("Item")).thenReturn(expected);

        //actual
        List<Item> actual = itemService.itemList();

        //assert
        assertEquals(expected, actual);
    }

    @Test
    public void testGetItemById_returnsItem(){

        //expected
        int itemId = 1;
        Item expected = new Item();
        expected.setItemId(itemId);

        //mock
        when(itemDaoMock.getById(Item.class,itemId)).thenReturn(expected);

        //actual
        Item actual = itemService.getById(itemId);

        //assert
        assertEquals(expected, actual);
    }

    @Test
    public void testUpdateItem_void(){

        itemService.updateQuantity(new Item());

        verify(itemDaoMock).updateQuantity(any(Item.class));
    }

    @Test
    public void testAddNewItem_void(){

        //data
        String name = "name";
        int avalCount = 1;
        int price = 2000;
        String itemCateg = "Portrait";
        String author = "Banksy";
        String country = "Germany";
        int height = 100;
        int width = 120;

        Category category = new Category();
        category.setCategoryName(itemCateg);

        //mock
        when(categoryDaoMock.findByName(itemCateg)).thenReturn(category);

        //call
        itemService.addNewItem(name, avalCount, price, itemCateg, author, country, height, width);

        verify(paramDaoMock).save(any(Param.class));

        verify(itemDaoMock).save(any(Item.class));
    }

    @Test
    public void testGetOrderNotNullItems_returnsMap(){

        //expected
        int orderId = 1;
        Map<Item, Integer> expected = new HashMap<>();

        expected.put(new Item(), 1);
        expected.put(new Item(), 2);

        //mock
        when(itemDaoMock.getNotNullItemsInBucket(orderId)).thenReturn(expected);

        //actual
        Map<Item, Integer> actual = itemService.getOrderNotNullItems(orderId);

        //assert
        assertEquals(expected, actual);
    }

    @Test
    public void testFilterItemsByAuthor_returnsListOfItems(){

        //data
        List<Item> list = new ArrayList<>();

        Item item1 = new Item();
        Param param1 = new Param();
        param1.setAuthor("Right");
        item1.setParams(param1);

        Item item2 = new Item();
        Param param2 = new Param();
        param2.setAuthor("Wrong");
        item2.setParams(param2);

        Item item3 = new Item();
        Param param3 = new Param();
        param3.setAuthor("Right");
        item3.setParams(param3);

        Item item4 = new Item();
        Param param4 = new Param();
        param4.setAuthor("Wrong");
        item4.setParams(param4);

        list.add(item1);
        list.add(item2);
        list.add(item3);
        list.add(item4);

        //expected
        List<Item> expected = new ArrayList<>();
        expected.add(item1);
        expected.add(item3);

        //actual
        String author = "Right";
        List<Item> actual = itemService.getFilteredItemsByAuthor(list, author);

        //assert
        assertEquals(expected, actual);
    }

    @Test
    public void testFilterItemsByCountry_returnsListOfItems(){

        //data
        List<Item> list = new ArrayList<>();

        Item item1 = new Item();
        Param param1 = new Param();
        param1.setCountry("Right");
        item1.setParams(param1);

        Item item2 = new Item();
        Param param2 = new Param();
        param2.setCountry("Wrong");
        item2.setParams(param2);

        Item item3 = new Item();
        Param param3 = new Param();
        param3.setCountry("Right");
        item3.setParams(param3);

        Item item4 = new Item();
        Param param4 = new Param();
        param4.setCountry("Wrong");
        item4.setParams(param4);

        list.add(item1);
        list.add(item2);
        list.add(item3);
        list.add(item4);

        //expected
        List<Item> expected = new ArrayList<>();
        expected.add(item1);
        expected.add(item3);

        //actual
        String country = "Right";
        List<Item> actual = itemService.getFilteredItemsByCountry(list, country);

        //assert
        assertEquals(expected, actual);
    }

    @Test
    public void testFilterItemsByMaxHeight_returnsListOfItems(){

        //data
        List<Item> list = new ArrayList<>();

        Item item1 = new Item();
        Param param1 = new Param();
        param1.setHeight(100);
        item1.setParams(param1);

        Item item2 = new Item();
        Param param2 = new Param();
        param2.setHeight(120);
        item2.setParams(param2);

        Item item3 = new Item();
        Param param3 = new Param();
        param3.setHeight(90);
        item3.setParams(param3);

        Item item4 = new Item();
        Param param4 = new Param();
        param4.setHeight(200);
        item4.setParams(param4);

        list.add(item1);
        list.add(item2);
        list.add(item3);
        list.add(item4);

        //expected
        List<Item> expected = new ArrayList<>();
        expected.add(item1);
        expected.add(item3);

        //actual
        int maxHeight = 100;
        List<Item> actual = itemService.getFilteredItemsByMaxHeight(list, maxHeight);

        //assert
        assertEquals(expected, actual);
    }

    @Test
    public void testFilterItemsByMaxWidth_returnsListOfItems(){

        //data
        List<Item> list = new ArrayList<>();

        Item item1 = new Item();
        Param param1 = new Param();
        param1.setWidth(100);
        item1.setParams(param1);

        Item item2 = new Item();
        Param param2 = new Param();
        param2.setWidth(120);
        item2.setParams(param2);

        Item item3 = new Item();
        Param param3 = new Param();
        param3.setWidth(90);
        item3.setParams(param3);

        Item item4 = new Item();
        Param param4 = new Param();
        param4.setWidth(200);
        item4.setParams(param4);

        list.add(item1);
        list.add(item2);
        list.add(item3);
        list.add(item4);

        //expected
        List<Item> expected = new ArrayList<>();
        expected.add(item1);
        expected.add(item3);

        //actual
        int maxWidth = 100;
        List<Item> actual = itemService.getFilteredItemsByMaxWidth(list, maxWidth);

        //assert
        assertEquals(expected, actual);
    }

    @Test
    public void testFilterItemsByAllParams_returnsListOfItems(){

        //data
        List<Item> list = new ArrayList<>();

        Item item1 = new Item();
        Param param1 = new Param();
        param1.setAuthor("Right");
        param1.setCountry("right");
        param1.setHeight(100);
        param1.setWidth(100);
        item1.setParams(param1);

        Item item2 = new Item();
        Param param2 = new Param();
        param2.setAuthor("Right");
        param2.setCountry("right");
        param2.setHeight(100);
        param2.setWidth(120);
        item2.setParams(param2);

        Item item3 = new Item();
        Param param3 = new Param();
        param3.setAuthor("Right");
        param3.setCountry("right");
        param3.setHeight(120);
        param3.setWidth(90);
        item3.setParams(param3);

        Item item4 = new Item();
        Param param4 = new Param();
        param4.setAuthor("Right");
        param4.setCountry("wrong");
        param4.setHeight(100);
        param4.setWidth(100);
        item4.setParams(param4);

        Item item5 = new Item();
        Param param5 = new Param();
        param5.setAuthor("Wrong");
        param5.setCountry("right");
        param5.setHeight(100);
        param5.setWidth(100);
        item5.setParams(param5);

        Item item6 = new Item();
        Param param6 = new Param();
        param6.setAuthor("Right");
        param6.setCountry("right");
        param6.setHeight(80);
        param6.setWidth(90);
        item6.setParams(param6);

        list.add(item1);
        list.add(item2);
        list.add(item3);
        list.add(item4);
        list.add(item5);
        list.add(item6);

        //expected
        List<Item> expected = new ArrayList<>();
        expected.add(item1);
        expected.add(item6);

        when(itemDaoMock.getAll("Item")).thenReturn(list);

        //actual
        String author = "Right";
        String country = "right";
        int maxHeight = 100;
        int maxWidth = 100;
        List<Item> actual = itemService.getFilteredItemsByAllParams(author, country, maxWidth, maxHeight);

        //assert
        assertEquals(expected, actual);
    }

    @Test
    public void testFilterItemsByCategory_returnsListOfItems(){

        //data
        Category category1 = new Category();
        category1.setCategoryName("Right");

        Category category2 = new Category();
        category2.setCategoryName("Wrong");

        List list = new ArrayList();

        Item item1 = new Item();
        item1.setCategory(category1);

        Item item2 = new Item();
        item2.setCategory(category2);

        Item item3 = new Item();
        item3.setCategory(category2);

        Item item4 = new Item();
        item4.setCategory(category1);

        list.add(item1);
        list.add(item2);
        list.add(item3);
        list.add(item4);

        //expected
        List<Item> expected1 = new ArrayList<>();
        expected1.add(item1);
        expected1.add(item4);

        List<Item> expected2 = list;

        //actual
        String categoryName1 = "Right";
        String categoryName2 = "ALL";

        List<Item> actual1 = itemService.getFilteredItemsByCategory(list, categoryName1);
        List<Item> actual2 = itemService.getFilteredItemsByCategory(list, categoryName2);

        //assert
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }
}
