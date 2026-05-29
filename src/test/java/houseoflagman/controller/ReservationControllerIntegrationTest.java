package houseoflagman.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReservationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void reservierenPage_returnsOk() throws Exception {
        mockMvc.perform(get("/reservieren"))
                .andExpect(status().isOk())
                .andExpect(view().name("reservieren"));
    }

    @Test
    void submitReservation_withInvalidData_returnsFormWithErrors() throws Exception {
        mockMvc.perform(post("/reservieren")
                        .param("name", "")
                        .param("email", "invalid-email")
                        .param("phone", "123")
                        .param("persons", "2")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("reservieren"));}
}