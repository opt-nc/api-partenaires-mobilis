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
class ApiPartenairesTests {

    @Autowired
    private MockMvc rest;

    @Test
    void testGetAllPartenairesJSON() throws Exception {
        rest
            .perform(get("/api/partenaires").accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$", hasSize(20)))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].nom").value("BOURAIL ELECTRONIC"))
            .andExpect(jsonPath("$[0].ridet").value("0740001001"))
            .andExpect(jsonPath("$[0].quartier").isEmpty())
            .andExpect(jsonPath("$[0].position.x").value(165.49879))
            .andExpect(jsonPath("$[0].position.y").value(-21.56877))
            .andExpect(jsonPath("$[0].adresse").value("5 A rue Simone Dremon - Village"))
            .andExpect(jsonPath("$[0].codePostal").value("98870"))
            .andExpect(jsonPath("$[0].codeInsee").value("98803"))
            .andExpect(jsonPath("$[0].ville").value("Bourail"))
            .andExpect(jsonPath("$[0].telephone").value("44.33.55"))
            .andExpect(jsonPath("$[0].url_fb").value("https://fr-fr.facebook.com/BOURAIL.ELECTRONIC/"))
            .andExpect(jsonPath("$[0].url_gmaps").value("https://goo.gl/maps/nSZ25o1ogQWoFQjK7"));
    }

    @Test
    void testGetPartenaireByIdJSON() throws Exception {
        rest
            .perform(get("/api/partenaires/1").accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.nom").value("BOURAIL ELECTRONIC"))
            .andExpect(jsonPath("$.ridet").value("0740001001"))
            .andExpect(jsonPath("$.quartier").isEmpty())
            .andExpect(jsonPath("$.position.x").value(165.49879))
            .andExpect(jsonPath("$.position.y").value(-21.56877))
            .andExpect(jsonPath("$.adresse").value("5 A rue Simone Dremon - Village"))
            .andExpect(jsonPath("$.codePostal").value("98870"))
            .andExpect(jsonPath("$.codeInsee").value("98803"))
            .andExpect(jsonPath("$.ville").value("Bourail"))
            .andExpect(jsonPath("$.telephone").value("44.33.55"))
            .andExpect(jsonPath("$.url_fb").value("https://fr-fr.facebook.com/BOURAIL.ELECTRONIC/"))
            .andExpect(jsonPath("$.url_gmaps").value("https://goo.gl/maps/nSZ25o1ogQWoFQjK7"));
    }

    @Test
    void testGetAllPartenairesGeoJSON() throws Exception {
        rest
            .perform(get("/api/partenaires"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/geo+json"))
            .andExpect(jsonPath("$.type").value("FeatureCollection"))
            .andExpect(jsonPath("$.features", hasSize(20)))
            .andExpect(jsonPath("$.features[0].type").value("Feature"))
            .andExpect(jsonPath("$.features[0].geometry.type").value("Point"))
            .andExpect(jsonPath("$.features[0].geometry.coordinates", hasSize(2)))
            .andExpect(jsonPath("$.features[0].geometry.coordinates[0]").value(165.49879))
            .andExpect(jsonPath("$.features[0].geometry.coordinates[1]").value(-21.56877))
            .andExpect(jsonPath("$.features[0].properties.adresse").value("5 A rue Simone Dremon - Village"))
            .andExpect(jsonPath("$.features[0].properties.id").value(1))
            .andExpect(jsonPath("$.features[0].properties.nom").value("BOURAIL ELECTRONIC"))
            .andExpect(jsonPath("$.features[0].properties.ridet").value("0740001001"))
            .andExpect(jsonPath("$.features[0].properties.quartier").isEmpty())
            .andExpect(jsonPath("$.features[0].properties.code_postal").value("98870"))
            .andExpect(jsonPath("$.features[0].properties.code_insee").value("98803"))
            .andExpect(jsonPath("$.features[0].properties.ville").value("Bourail"))
            .andExpect(jsonPath("$.features[0].properties.telephone").value("44.33.55"))
            .andExpect(jsonPath("$.features[0].properties.url_fb").value("https://fr-fr.facebook.com/BOURAIL.ELECTRONIC/"))
            .andExpect(jsonPath("$.features[0].properties.url_gmaps").value("https://goo.gl/maps/nSZ25o1ogQWoFQjK7"));
    }

    @Test
    void testByGeoPosition() throws Exception {
        rest
            .perform(
                get("/api/partenaires?nearBy.lon=166.442412&nearBy.lat=-22.279575&nearBy.distance=1000")
                    .accept(MediaType.APPLICATION_JSON_VALUE)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].id").value(11))
            .andExpect(jsonPath("$[1].id").value(10))
            .andExpect(jsonPath("$[2].id").value(7));
    }

    @Test
    void testByCriteria() throws Exception {
        rest
            .perform(get("/api/partenaires?q=djilo&ville=nouméa&codePostal=98800&codeInsee=98818").accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id").value(11))
            .andExpect(jsonPath("$[1].id").value(16));
    }
}
