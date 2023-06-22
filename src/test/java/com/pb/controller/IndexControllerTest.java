package com.pb.controller;

import com.pb.config.SecurityConfig;
import com.pb.model.User;
import com.pb.repository.OrderRepository;
import com.pb.repository.ProductRepository;
import com.pb.repository.UserRepository;
import com.pb.service.OrderService;
import com.pb.service.ProductService;
import com.pb.service.UserService;
import com.pb.service.impl.OrderServiceImpl;
import com.pb.service.impl.ProductServiceImpl;
import com.pb.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Import(SecurityConfig.class)
@WebMvcTest(IndexController.class)
public class IndexControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private ProductServiceImpl productService;

    @MockBean
    private OrderServiceImpl orderService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private OrderRepository orderRepository;

    @Test
    public void IndexController_home_NotLoggedInUser() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("home"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("title"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("principal"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("currentUserEmail"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("currentRole"))
                .andExpect(MockMvcResultMatchers.model().attribute("title", "Shop"))
                .andExpect(MockMvcResultMatchers.model().attribute("principal", "anonymousUser"))
                .andExpect(MockMvcResultMatchers.model().attribute("currentUserEmail", "anonymousUser"))
                .andExpect(MockMvcResultMatchers.model().attribute("currentRole", "[ROLE_ANONYMOUS]"));
    }

    @Test
    public void IndexController_login() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("title"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("currentUserEmail"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("currentRole"))
                .andExpect(MockMvcResultMatchers.model().attribute("title", "Shop - Login"))
                .andExpect(MockMvcResultMatchers.model().attribute("currentUserEmail", "anonymousUser"))
                .andExpect(MockMvcResultMatchers.model().attribute("currentRole", "[ROLE_ANONYMOUS]"));
    }

    @Test
    public void IndexController_register() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/register"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("register"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("title"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("currentUserEmail"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("currentRole"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.model().attribute("title", "Shop - Register"))
                .andExpect(MockMvcResultMatchers.model().attribute("currentUserEmail", "anonymousUser"))
                .andExpect(MockMvcResultMatchers.model().attribute("currentRole", "[ROLE_ANONYMOUS]"))
                .andExpect(MockMvcResultMatchers.model().attribute("user", new User()));
    }
}
