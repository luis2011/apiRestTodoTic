package pe.todotic.taskflow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pe.todotic.taskflow.controlador.dto.LoginDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pe.todotic.taskflow.TestUtils.asJsonString;

@AutoConfigureMockMvc
@SpringBootTest
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void dadoLasCredencialesCorrectas_retorna_elToken() throws Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("admin@gmail.com");
        loginDTO.setClave("12345");

        mockMvc.perform(
                post("/api/auth/token")
                        .content(asJsonString(loginDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().exists(HttpHeaders.AUTHORIZATION))
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void dadoLasCredencialesIncorrectas_retorna_401() throws Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("admin@gmail.com");
        loginDTO.setClave("bad_password");

        mockMvc.perform(
                post("/api/auth/token")
                        .content(asJsonString(loginDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isUnauthorized())
                .andExpect(header().doesNotExist(HttpHeaders.AUTHORIZATION));
    }


}
