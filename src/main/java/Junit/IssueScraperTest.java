package Junit;

import io.kuy.infozilla.githubscraper.IssueScraper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class IssueScraperTest {

    private IssueScraper issueScraper;

    @BeforeEach
    void setUp() {
        issueScraper = new IssueScraper();
    }

    @Test
    void testValidRepoAndIssueURL() {
        String url = "https://github.com/gerardofesta/infozilla/issues/1";
        assertDoesNotThrow(() -> issueScraper.runScraper(url));
        assertTrue(new File("gerardofesta-infozilla-1Issue.txt").exists());
    }

    @Test
    void testInvalidURLFormats() {
        String url = "https://example.com";
        String url1 = "https://github.com";
        String url2 = "https://github.com/issues/";
        assertThrows(IOException.class, () -> issueScraper.runScraper(url));
        assertThrows(IOException.class, () -> issueScraper.runScraper(url1));
        assertThrows(IOException.class, () -> issueScraper.runScraper(url2));
    }


    @Test
    void testAcceptedURLFormats() {
        String url = "https://www.github.com/gerardofesta/infozilla/issues/1";
        String url1 = "http://www.github.com/gerardofesta/infozilla/issues/3";
        String url2 = "www.github.com/gerardofesta/infozilla/issues/14";
        String url3 = "github.com/gerardofesta/infozilla/issues/13";
        assertDoesNotThrow(() -> issueScraper.runScraper(url));
        assertDoesNotThrow(() -> issueScraper.runScraper(url1));
        assertDoesNotThrow(() -> issueScraper.runScraper(url2));
        assertDoesNotThrow(() -> issueScraper.runScraper(url3));
        assertTrue(new File("gerardofesta-infozilla-1Issue.txt").exists());
        assertTrue(new File("gerardofesta-infozilla-3Issue.txt").exists());
        assertTrue(new File("gerardofesta-infozilla-14Issue.txt").exists());
        assertTrue(new File("gerardofesta-infozilla-13Issue.txt").exists());
    }

    @Test
    void testGetClosedIssue() {

        String url = "https://github.com/gerardofesta/infozilla/issues/1";

        String path= null;
        try {
            path = issueScraper.runScraper(url);
        } catch (IOException e) {
            fail();
        }
        assertNotNull(path);

    }

    @AfterAll
    static void tearDown() {
        File file = new File("gerardofesta-infozilla-1Issue.txt");
        file.delete();
        file = new File("gerardofesta-infozilla-3Issue.txt");
        file.delete();
        file = new File("gerardofesta-infozilla-14Issue.txt");
        file.delete();
        file = new File("gerardofesta-infozilla-13Issue.txt");
        file.delete();
    }
}
