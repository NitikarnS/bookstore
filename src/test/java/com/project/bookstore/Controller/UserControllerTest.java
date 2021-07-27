package com.project.bookstore.Controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bookstore.UpdateBookTask;
import com.project.bookstore.Exception.UsernameAlreadyExistException;
import com.project.bookstore.Model.Book;
import com.project.bookstore.Model.User;
import com.project.bookstore.Service.impl.OrderService;
import com.project.bookstore.Service.impl.UserService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(value = UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean(name = "inMemoryUserDetailsManager")
    UserService userService;

    @MockBean
    OrderService orderService;

    @MockBean
    UpdateBookTask updateBookTask;

    @MockBean
    BCryptPasswordEncoder passwordEncoder;

    private User getMockUser() {
        return new User(1L, "john.doe", "thisismysecret", "15/01/1999");
    }

    @Test
    public void loginSuccess() throws Exception {
        Mockito.when(userService.login(Mockito.anyString(), Mockito.anyString())).thenReturn(getMockUser());
        
        String jsonLogin = "{\"username\":\"john.doe\",\"password\":\"thisismysecret\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonLogin)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void loginWrongUsernamePassword() throws Exception {
        Mockito.when(userService.login(Mockito.anyString(), Mockito.anyString()))
                .thenThrow(new BadCredentialsException("BadCredentialsException"));
        
        String jsonLogin = "{\"username\":\"john.doe\",\"password\":\"thisismysecret\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonLogin)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getUserWithoutAuthentication() throws Exception {
        User mockUser = getMockUser();
        mockUser.setOrders(new ArrayList<>(Arrays.asList(
            new Book(3L, "Ant & Bee", "Tita", new BigDecimal(50), false), 
            new Book(5L, "Animal world", "Jackson", new BigDecimal(120), false))));
        Mockito.when(userService.getCurrentUser()).thenReturn(mockUser);

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser()
    public void getUserWithAuthentication() throws Exception {
        User mockUser = getMockUser();
        mockUser.setOrders(new ArrayList<>(Arrays.asList(
            new Book(3L, "Ant & Bee", "Tita", new BigDecimal(50), false), 
            new Book(5L, "Animal world", "Jackson", new BigDecimal(120), false))));
        Mockito.when(userService.getCurrentUser()).thenReturn(mockUser);

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is("john")))
                .andExpect(jsonPath("surname", is("doe")))
                .andExpect(jsonPath("date_of_birth", is("15/01/1999")))
                .andExpect(jsonPath("books", hasSize(2)))
                .andExpect(jsonPath("books[0]", is(3)));
    }

    @Test
    @WithMockUser()
    public void getUserWithAuthenticationNoSurname() throws Exception {
        User mockUser = getMockUser();
        mockUser.setUsername("john");
        mockUser.setOrders(new ArrayList<>(Arrays.asList(
            new Book(3L, "Ant & Bee", "Tita", new BigDecimal(50), false), 
            new Book(5L, "Animal world", "Jackson", new BigDecimal(120), false))));
        Mockito.when(userService.getCurrentUser()).thenReturn(mockUser);

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("surname", is("")));
    }

    @Test
    public void deleteUserWithoutAuthentication() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser()
    public void deleteUserWithAuthentication() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users"))
                .andExpect(status().isOk());
    }

    @Test
    public void postUserSuccess() throws Exception {
        User mockUser = getMockUser();
        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(mockUser);

        String jsonPostUser = "{\"username\":\"john.doe\",\"password\":\"thisismysecret\",\"date_of_birth\":\"02\\/11\\/1985\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonPostUser)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void postUserDuplicateUsername() throws Exception {
        Mockito.when(userService.createUser(Mockito.any(User.class)))
                .thenThrow(new UsernameAlreadyExistException("UsernameAlreadyExistException"));

        String jsonPostUser = "{\"username\":\"john.doe\",\"password\":\"thisismysecret\",\"date_of_birth\":\"02\\/11\\/1985\"}";
     
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonPostUser)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void orderBookWithoutAuthentication() throws Exception {
        User mockUser = getMockUser();
        mockUser.setOrders(new ArrayList<>(Arrays.asList(
            new Book(3L, "Ant & Bee", "Tita", new BigDecimal(50), false), 
            new Book(5L, "Animal world", "Jackson", new BigDecimal(120), false))));

        Mockito.when(orderService.order(Mockito.anyLong(), Arrays.asList(Mockito.anyLong())))
                .thenReturn(mockUser);

        String jsonOrderBook = "{\"orders\":[10]}";
                
        mockMvc.perform(MockMvcRequestBuilders.post("/users/orders")
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonOrderBook)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser()
    public void orderBookWithAuthentication() throws Exception {
        User mockUser = getMockUser();
        mockUser.setOrders(new ArrayList<>(Arrays.asList(
            new Book(3L, "Ant & Bee", "Tita", new BigDecimal(50), false), 
            new Book(5L, "Animal world", "Jackson", new BigDecimal(120), false))));

        Mockito.when(userService.getCurrentUser()).thenReturn(mockUser);
        Mockito.when(orderService.order(1L, Arrays.asList(3L, 5L)))
                .thenReturn(mockUser);

        String jsonOrderBook = "{\"orders\":[3,5]}";
                
        mockMvc.perform(MockMvcRequestBuilders.post("/users/orders")
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonOrderBook)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("price", is(170)));
    }


}
