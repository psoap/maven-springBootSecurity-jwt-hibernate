package net.psoap.newsportal.controller;

import net.psoap.newsportal.TestUtils;
import net.psoap.newsportal.model.form.UserForm;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@TestPropertySource(properties = {
//        "rest.api.uri.user-controller=api/v1/users/"
//})
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private static String requestMapping;
    private static RestTemplate restTemplate;
//    @Autowired
//    private static WebApplicationContext webApplicationContext;

    @BeforeAll
    static void initControllerRequestMapping(@Value("${rest.api.uri}") String restUri){
        requestMapping = restUri+"users/";
        restTemplate = new RestTemplate();
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(webApplicationContext)
//                .apply(springSecurity())
//                .build();
    }

    @Test
    void registration() throws Exception {
        UserForm userForm = new UserForm();
        userForm.setEmail("junit@mail.com");
        userForm.setPassword("testpass");

//        User res = restTemplate.postForObject("http://localhost:9090/"+requestMapping+"reg", User.class);

        mockMvc.perform(post(requestMapping+"reg").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(userForm))
        ).andExpect(status().isOk());
    }

    @Test
    void login() throws Exception {
        mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk());
    }
}