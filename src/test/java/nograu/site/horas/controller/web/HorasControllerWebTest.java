package nograu.site.horas.controller.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class HorasControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCalcularHoras() throws Exception {
        mockMvc.perform(post("/horas/calcular")
                .param("horaEntrada", "08:00")
                .param("horaAlmoco", "12:00")
                .param("horaRetornoAlmoco", "13:00")
                .param("cargaHoraria", "08:00"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/horas"))
                .andExpect(flash().attributeExists("horaSaida"))
                .andExpect(flash().attribute("horaSaida", LocalTime.of(17, 00)));
    }
}