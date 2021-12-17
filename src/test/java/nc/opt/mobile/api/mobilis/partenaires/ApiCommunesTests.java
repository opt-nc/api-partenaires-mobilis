package nc.opt.mobile.api.mobilis.partenaires;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ApiCommunesTests {

    @Autowired
    private MockMvc rest;

    @Test
    void testGetAllCommunesJSON() throws Exception {
        rest
            .perform(get("/api/communes").accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$", hasSize(6)))
            .andExpect(jsonPath("$[0].codePostal").value("98870"))
            .andExpect(jsonPath("$[0].codeInsee").value("98803"))
            .andExpect(jsonPath("$[0].nom").value("Bourail"));
    }

    @Test
    void testGetPartenaireByCodeInseeJSON() throws Exception {
        rest
            .perform(get("/api/communes/98803").accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.codePostal").value("98870"))
            .andExpect(jsonPath("$.codeInsee").value("98803"))
            .andExpect(jsonPath("$.nom").value("Bourail"));
    }

    @Test
    void testGetAllCommunesGeoJSON() throws Exception {
        rest
            .perform(get("/api/communes"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/geo+json"))
            .andExpect(jsonPath("$.type").value("FeatureCollection"))
            .andExpect(jsonPath("$.features", hasSize(6)))
            .andExpect(jsonPath("$.features[0].type").value("Feature"))
            .andExpect(jsonPath("$.features[0].geometry.type").value("Point"))
            .andExpect(jsonPath("$.features[0].geometry.coordinates", hasSize(2)))
            .andExpect(jsonPath("$.features[0].geometry.coordinates[0]").value(165.494))
            .andExpect(jsonPath("$.features[0].geometry.coordinates[1]").value(-21.5689))
            .andExpect(jsonPath("$.features[0].properties.code_postal").value("98870"))
            .andExpect(jsonPath("$.features[0].properties.code_insee").value("98803"))
            .andExpect(jsonPath("$.features[0].properties.nom").value("Bourail"));
    }

    @Test
    void testByCodePostal() throws Exception {
        rest
            .perform(get("/api/communes?codePostal=98800").accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].codeInsee").value("98818"));
    }

    @Test
    void testByNom() throws Exception {
        rest
            .perform(get("/api/communes?nom=nouméa").accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].codeInsee").value("98818"));
    }
}
