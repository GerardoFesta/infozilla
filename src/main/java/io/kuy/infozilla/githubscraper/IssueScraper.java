package io.kuy.infozilla.githubscraper;

import org.eclipse.egit.github.core.*;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.GitHubService;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import io.github.cdimascio.dotenv.Dotenv;


public class IssueScraper {
    Dotenv dotenv = Dotenv.load();
    private String GITHUB_ACCESS_TOKEN = dotenv.get("GITHUB_ACCESS_TOKEN");
    private GitHubClient client;

    public IssueScraper() {
        client = new GitHubClient();
        if (GITHUB_ACCESS_TOKEN != null && !GITHUB_ACCESS_TOKEN.isEmpty()) {
            client.setOAuth2Token(GITHUB_ACCESS_TOKEN);
        }else{
            System.out.println("You are not authenticated, you will have a 60 reqs/hour limit\n if you want to increase it, please set the GITHUB_ACCESS_TOKEN environment variable. Check the README for more info.");
        }



    }

    public String runScraper(String url) throws IOException {
        String repo_id = getRepoIdFromUrl(url);
        String issue_id = getIssueIdFromUrl(url);
        if(repo_id == null || issue_id == null){
            throw new IOException("Invalid URL");
        }
        String base_file_name=repo_id.replace("/","-")+"-"+issue_id+"Issue.txt";
        return scrapeIssue(repo_id, issue_id, base_file_name);

    }

    private String scrapeIssue(String repo_id, String issue_id, String file_name) throws IOException {

        RepositoryId repoId = RepositoryId.createFromId(repo_id);
        IssueService issueService = new IssueService(client);
        RepositoryService repoService = new RepositoryService(client);

        // Get the repository information to check if it is public
        Repository repository = repoService.getRepository(repoId);
        boolean isPublic = repository.isPrivate() == false;

        if (isPublic) {

            Issue issue = issueService.getIssue(repository, issue_id);
            String issueText = issue.getBody();
            List<Comment> comments = issueService.getComments(repoId, issue.getNumber());
            StringBuilder commentsText = new StringBuilder();
            for (Comment comment : comments) {
                commentsText.append(comment.getBody());
                commentsText.append("\n");
            }


            try{
                File file = new File(file_name);
                // verifica se il file esiste già
                if (file.createNewFile()) {
                    System.out.println("File creato con successo!");
                } else {
                    System.out.println("Il file esiste già.");
                }
                // scrivi una stringa nel file
                FileWriter writer = new FileWriter(file);
                writer.write(issueText+"\n"+commentsText);
                writer.close();
                System.out.println("Scrittura completata con successo!");
                return file.getPath();

            } catch (IOException e) {
                System.out.println("Si è verificato un errore durante la creazione del file.");
                e.printStackTrace();
            }


        } else {
            System.out.println("This repository is private. Authentication is required.");

        }
        return null;
    }

    private String getRepoIdFromUrl(String url) {
        if(! ((url.contains("www.github.com") || url.contains("https://github.com") || url.contains("http://github.com")) || url.contains("github.com"))){
            System.out.println("Invalid URL");
            return null;
        }
        System.out.println("Scraping issue: " + url);
        String[] urlParts = url.split("/");
        String repo_id="";
        if(urlParts[0].equalsIgnoreCase("https:") || urlParts[0].equalsIgnoreCase("http:")){
            if(urlParts.length<5)
                return null;
            repo_id = urlParts[3] + "/" + urlParts[4];
        }else if(urlParts[0].equalsIgnoreCase("www.github.com") || urlParts[0].equalsIgnoreCase("github.com")){
            if(urlParts.length<3)
                return null;
            repo_id = urlParts[1] + "/" + urlParts[2];
        }else{
            return null;
        }
        return repo_id;
    }

    private String getIssueIdFromUrl(String url){
        if(! ((url.contains("www.github.com") || url.contains("https://github.com") || url.contains("http://github.com")) || url.contains("github.com"))){
            return null;
        }

        String[] urlParts = url.split("/");
        String issue_id="";
        if(urlParts[0].equalsIgnoreCase("https:") || urlParts[0].equalsIgnoreCase("http:")){
            if(urlParts.length<7)
                return null;
            issue_id = urlParts[6];
        }else if(urlParts[0].equalsIgnoreCase("www.github.com") || urlParts[0].equalsIgnoreCase("github.com")){
            if(urlParts.length<5)
                return null;
            issue_id = urlParts[4];
        }else{
            return null;
        }
        return issue_id;

    }

    public String[] runRepoScraper(String url, String openedBeforeStr, String openedAfterStr, String closedBeforeStr, String closedAfterStr, String assignee, String[] labels, String state) throws IOException {
        String repo_id = getRepoIdFromUrl(url);
        if(repo_id == null){
            throw new IOException("Invalid URL");
        }
        Date openedBeforeDate = null;
        Date openedAfterDate = null;
        Date closedBeforeDate = null;
        Date closedAfterDate = null;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            if (openedBeforeStr != null) {
                openedBeforeDate = dateFormat.parse(openedBeforeStr);
            } else {
                openedBeforeDate = new Date(Long.MAX_VALUE);
            }
            if (closedBeforeStr != null) {
                closedBeforeDate = dateFormat.parse(closedBeforeStr);
            } else {
                closedBeforeDate = new Date(Long.MAX_VALUE);
            }
            if (openedAfterStr != null) {
                openedAfterDate = dateFormat.parse(openedAfterStr);
            } else {
                openedAfterDate = new Date(Long.MIN_VALUE);
            }
            if (closedAfterStr != null) {
                closedAfterDate = dateFormat.parse(closedAfterStr);
            } else {
                closedAfterDate = new Date(Long.MIN_VALUE);
            }
        }catch (Exception e){
            throw new IOException("Invalid date format");
        }

        if(openedAfterDate.compareTo(openedBeforeDate)>0){
            throw new IOException("Invalid date range");
        }

        if(closedAfterDate.compareTo(closedBeforeDate)>0){
            throw new IOException("Invalid date range");
        }

        if(openedAfterDate.compareTo(closedBeforeDate)>0){
            throw new IOException("Invalid date range");
        }



        RepositoryId repoId = RepositoryId.createFromId(repo_id);
        IssueService issueService = new IssueService(client);

        HashMap<String, String> map= new HashMap();
        List<Issue> issues = null;
        if(state!=null && !(state.equalsIgnoreCase("open") || state.equalsIgnoreCase("closed") || state.equalsIgnoreCase("all"))){
            throw new IOException("Invalid state");
        }else if(state == null || state.equalsIgnoreCase("all")){
            map.put("state","closed");
            List<Issue> closed_issues = issueService.getIssues(repoId, map);
            map.put("state","open");
            List<Issue> open_issues = issueService.getIssues(repoId, map);
            closed_issues.addAll(open_issues);
            issues = closed_issues;
        } else if(state.equalsIgnoreCase("open")){
            map.put("state","open");
            issues = issueService.getIssues(repoId, map);
        } else if(state.equalsIgnoreCase("closed")){
            map.put("state","closed");
            issues = issueService.getIssues(repoId, map);
        }


        if(issues != null) {
            String[] paths = new String[issues.size()];
            int i = 0;
            for (Issue issue : issues) {
                if (issue.getCreatedAt().after(openedAfterDate) && issue.getCreatedAt().before(openedBeforeDate)) {
                    //Se l'issue è aperta, non ha data di chiusura, quindi la prendo solo se l'utente non ha filtrato per data di chiusura
                    //Se l'issue è chiusa, mi assicuro che sia nel range di date (infinito se l'utente non ha inserito alcun filtro)
                    if ((issue.getClosedAt() == null && closedAfterStr == null && closedAfterStr == null) ||
                            (issue.getClosedAt() != null && issue.getClosedAt().before(closedBeforeDate) && issue.getClosedAt().after(closedAfterDate))
                    ) {

                        if (assignee == null || (issue.getAssignee() != null && issue.getAssignee().getLogin().equalsIgnoreCase(assignee))) {
                            if (state == null || issue.getState().equalsIgnoreCase(state) || state.equalsIgnoreCase("all")) {
                                boolean containsLabel = false;
                                if (labels != null && labels.length > 0) {
                                    for (String label : labels) {
                                        for (Label issueLabel : issue.getLabels()) {
                                            if (issueLabel.getName().equalsIgnoreCase(label)) {
                                                containsLabel = true;
                                                break;
                                            }
                                        }
                                        if (containsLabel)
                                            break;
                                    }
                                }
                                if (labels == null || labels.length == 0 || containsLabel) {
                                    String issue_id = issue.getNumber() + "";
                                    String base_file_name = repo_id.replace("/", "-") + "-" + issue_id + "Issue.txt";

                                    paths[i] = scrapeIssue(repo_id, issue_id, base_file_name);
                                    i++;
                                }
                            }
                        }
                    }
                }


            }

            String[] actualpaths = new String[i];
            for (int j = 0; j < i; j++) {
                actualpaths[j] = paths[j];
            }
            return actualpaths;
        }else
            return new String[0];
    }


}
