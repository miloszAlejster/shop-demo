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

import java.util.ArrayList;

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
    public void IndexController_home() throws Exception {
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

    @Test
    public void IndexController_adminDashboard_NotLoggedInUser_RedirectsToLogin() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/admin"))
                .andExpect(MockMvcResultMatchers.status().is(302))
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
    }

    @Test
    public void IndexController_my_orders_NotLoggedInUser_RedirectsToLogin() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/my-orders"))
                .andExpect(MockMvcResultMatchers.status().is(302))
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
    }

    @Test
    public void IndexController_products() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("products"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("title"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("currentUserEmail"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("currentRole"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("products"))
                .andExpect(MockMvcResultMatchers.model().attribute("title", "Shop - Products"))
                .andExpect(MockMvcResultMatchers.model().attribute("currentUserEmail", "anonymousUser"))
                .andExpect(MockMvcResultMatchers.model().attribute("currentRole", "[ROLE_ANONYMOUS]"))
                .andExpect(MockMvcResultMatchers.model().attribute("products", new ArrayList<>()));
    }
}
