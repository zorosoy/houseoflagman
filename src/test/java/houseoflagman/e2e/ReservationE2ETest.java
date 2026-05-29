package houseoflagman.e2e;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationE2ETest {

    @LocalServerPort
    private int port;

    private WebClient webClient;

    @BeforeEach
    void setUp() {
        webClient = new WebClient();
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
    }

    @AfterEach
    void tearDown() {
        webClient.close();
    }

    @Test
    void startseite_isAccessible() throws Exception {
        HtmlPage page = webClient.getPage("http://localhost:" + port + "/");
        assertEquals(200, page.getWebResponse().getStatusCode());
        assertTrue(page.getTitleText().contains("House of Lagman"));
    }

    @Test
    void reservierenSeite_isAccessible() throws Exception {
        HtmlPage page = webClient.getPage("http://localhost:" + port + "/reservieren");
        assertEquals(200, page.getWebResponse().getStatusCode());
        assertTrue(page.getTitleText().contains("Reservieren"));
    }
}