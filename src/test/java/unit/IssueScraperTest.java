package unit;

import io.kuy.infozilla.githubscraper.IssueScraper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

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
        String url3 = "www.github.com/gerardofesta";
        String url4 = "www.github.com/gerardofesta/infozilla";
        String url5 = "www.github.com/gerardofesta/infozilla/issues";
        assertThrows(IOException.class, () -> issueScraper.runScraper(url));
        assertThrows(IOException.class, () -> issueScraper.runScraper(url1));
        assertThrows(IOException.class, () -> issueScraper.runScraper(url2));
        assertThrows(IOException.class, () -> issueScraper.runScraper(url3));
        assertThrows(IOException.class, () -> issueScraper.runScraper(url4));
        assertThrows(IOException.class, () -> issueScraper.runScraper(url5));
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

//TESTS FOR REPO SCRAPER
    @Test
    void testInvalidDateRange1() {
        String url = "https://github.com/gerardofesta/infozilla";
        String openedAfterStr = "2023-01-01";
        String openedBeforeStr = "2022-01-01";
        assertThrows(IOException.class, () -> issueScraper.runRepoScraper(url, openedBeforeStr, openedAfterStr, null, null, null, null, null));

    }
    @Test
    void testInvalidDateRange2() {
        String url = "https://github.com/gerardofesta/infozilla";
        String closedAfterStr = "2023-01-01";
        String closedBeforeStr = "2022-01-01";
        assertThrows(IOException.class,() -> issueScraper.runRepoScraper(url, null, null, closedBeforeStr, closedAfterStr, null, null, null));

    }
    @Test
    void testInvalidDateRange3() {
        String url = "https://github.com/gerardofesta/infozilla";
        String openedAfterStr = "2023-01-01";
        String closedBeforeStr = "2022-01-01";
        assertThrows(IOException.class, () -> issueScraper.runRepoScraper(url, null , openedAfterStr, closedBeforeStr, null, null, null, null));

    }


    @Test
    void testValidDateRange1(){
        String url = "https://github.com/gerardofesta/infozilla";
        String openedAfterStr = "2022-01-01";
        String openedBeforeStr = "2023-01-01";
        String[] paths = null;
        try{
            paths= issueScraper.runRepoScraper(url, openedBeforeStr, openedAfterStr, null, null, null, null, null);
        }catch(Exception e){
            fail();
        }
        assertNotNull(paths);
        assertTrue(paths.length == 0);
    }

    @Test
    void testValidDateRange2(){
        String url = "https://github.com/gerardofesta/infozilla";
        String openedAfterStr = "2023-08-26";
        String openedBeforeStr = "2023-09-19";
        String[] paths = null;
        try{
            paths= issueScraper.runRepoScraper(url, openedBeforeStr, openedAfterStr, null, null, null, null, null);
        }catch(Exception e){
            System.out.println(e);
            fail();
        }
        assertNotNull(paths);
        assertTrue(paths.length == 4);
    }

    @Test
    void testValidDateRange3(){
        String url = "https://github.com/gerardofesta/infozilla";
        String closedAfterStr = "2023-08-25";
        String closedBeforeStr = "2023-09-24";
        String[] paths = null;
        try{
            paths= issueScraper.runRepoScraper(url, null, null, closedBeforeStr, closedAfterStr, null, null, null);
        }catch(Exception e){
            System.out.println(e);
            fail();
        }
        System.out.println(paths.length);
        assertNotNull(paths);
        assertTrue(paths.length == 6);
    }


    @Test
    void testInvalidDateFormat(){
        String url = "https://github.com/gerardofesta/infozilla";
        String closedBeforeStr = "01/01/2023";
        String[] paths = null;

        assertThrows(IOException.class, () -> issueScraper.runRepoScraper(url, null, null,closedBeforeStr, null , null, null, null));

    }


    @Test
    void testInvalidAssignee() {
        String url = "https://github.com/GerardoFesta/infozilla";
        String assignee = "abcdef";
        String[] paths = new String[0];
        try {
            paths = issueScraper.runRepoScraper(url, null, null, null, null, assignee, null, null);
        } catch (IOException e) {
            System.out.println(e);
            fail();
        }
        assertNotNull(paths);
        assertEquals(0, paths.length);
    }

    @Test
    void testValidAssignee() {

        String url = "https://github.com/gerardofesta/infozilla";
        String assignee = "gerardofesta";
        String[] paths = new String[0];
        try {
            paths = issueScraper.runRepoScraper(url, null, null, null, null, assignee, null, null);
        } catch (IOException e) {
            System.out.println(e);
            fail();
        }
        assertNotNull(paths);
        assertTrue(paths.length > 0);
    }

    @Test
    void testGetClosedIssues() {

        String url = "https://github.com/gerardofesta/infozilla";
        String state = "closed";
        String[] paths = new String[0];
        try {
            paths = issueScraper.runRepoScraper(url, null, null, null, null, null, null, state);
        } catch (IOException e) {
            System.out.println(e);
            fail();
        }
        assertNotNull(paths);
        assertTrue(paths.length > 0);
    }

    @Test
    void testGetOpenIssues() {

        String url = "https://github.com/gerardofesta/infozilla";
        String state = "open";
        String[] paths = new String[0];
        try {
            paths = issueScraper.runRepoScraper(url, null, null, null, null, null, null, state);
        } catch (IOException e) {
            System.out.println(e);
            fail();
        }
        assertNotNull(paths);
    }

    @Test
    void testGetIssuesBySingleLabel(){
        String url = "github.com/gerardofesta/infozilla";
        String[] labels = {"bug"};
        String[] paths = new String[0];
        try {
            paths = issueScraper.runRepoScraper(url, null, null, null, null, null, labels, null);
        } catch (IOException e) {
            System.out.println(e);
            fail();
        }
        assertNotNull(paths);
        assertTrue(paths.length >0);
        assertTrue(Arrays.stream(paths).anyMatch(s -> s.contains("gerardofesta-infozilla-10Issue.txt")));
        assertTrue(Arrays.stream(paths).anyMatch(s -> s.contains("gerardofesta-infozilla-12Issue.txt")));
        assertTrue(Arrays.stream(paths).anyMatch(s -> s.contains("gerardofesta-infozilla-1Issue.txt")));
        assertFalse(Arrays.stream(paths).anyMatch(s -> s.contains("gerardofesta-infozilla-2Issue.txt")));
    }

    @Test
    void testGetIssuesByMultipleLabels(){
        String url = "github.com/gerardofesta/infozilla";
        String[] labels = {"bug", "enhancement"};
        String[] paths = new String[0];
        try {
            paths = issueScraper.runRepoScraper(url, null, null, null, null, null, labels, null);
        } catch (IOException e) {
            System.out.println(e);
            fail();
        }
        assertNotNull(paths);
        assertTrue(paths.length >0);
        assertTrue(Arrays.stream(paths).anyMatch(s -> s.contains("gerardofesta-infozilla-3Issue.txt")));
        assertTrue(Arrays.stream(paths).anyMatch(s -> s.contains("gerardofesta-infozilla-4Issue.txt")));
        assertTrue(Arrays.stream(paths).anyMatch(s -> s.contains("gerardofesta-infozilla-5Issue.txt")));
        assertTrue(Arrays.stream(paths).anyMatch(s -> s.contains("gerardofesta-infozilla-9Issue.txt")));
        assertTrue(Arrays.stream(paths).anyMatch(s -> s.contains("gerardofesta-infozilla-11Issue.txt")));
        assertTrue(Arrays.stream(paths).anyMatch(s -> s.contains("gerardofesta-infozilla-13Issue.txt")));
        assertTrue(Arrays.stream(paths).anyMatch(s -> s.contains("gerardofesta-infozilla-14Issue.txt")));
    }










    @AfterAll
    static void tearDown() {
        for (int i=1; i<=30; i++) {
            File file = new File("gerardofesta-infozilla-"+i+"Issue.txt");
            file.delete();
        }

    }



}
