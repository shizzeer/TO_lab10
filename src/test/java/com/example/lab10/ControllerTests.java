package com.example.lab10;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ControllerTests {
    private MockMvc mockMvc;
    private String book;
    @Autowired
    private Controller controller;
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void shouldReturnBadRequestWhenAddingBookWithBlankTitle() throws Exception {
        book = "{\"title\":\"\",\"author\":\"Author\",\"year\":2022}";
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(book))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenAddingBookWithBlankAuthor() throws Exception {
        book = "{\"title\":\"Book\",\"author\":\"\",\"year\":2022}";
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(book))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenAddingBookWithNegativeYear() throws Exception {
        book = "{\"title\":\"Book\",\"author\":\"Author\",\"year\":-5}";
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(book))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenAddingBookWithZeroYear() throws Exception {
        book = "{\"title\":\"Book\",\"author\":\"Author\",\"year\":0}";
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(book))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenAddingInvalidBook() throws Exception {
        book = "{\"titl\":}";
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(book))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnEmptyListWhenNoBooksAdded() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }
    @Test
    public void shouldAddBookAndReturnOK() throws Exception {
        String book = "{\"title\":\"Book\",\"author\":\"Author\",\"year\":2022}";
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(book))
                .andExpect(status().isOk());
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Book")))
                .andExpect(jsonPath("$[0].author", is("Author")))
                .andExpect(jsonPath("$[0].year", is(2022)));
    }
}
