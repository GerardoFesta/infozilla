package io.kuy.infozilla.githubscraper;

import org.eclipse.egit.github.core.*;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class IssueScraper {

    public String runScraper(String url) throws IOException {
        String repo_id = getRepoIdFromUrl(url);
        String issue_id = getIssueIdFromUrl(url);
        if(repo_id == null || issue_id == null){
            throw new IOException("Invalid URL");
        }
        String base_file_name=repo_id.replace("/","-")+"-"+issue_id+"Issue.txt";
        return scrapeIssue(repo_id, issue_id, base_file_name);

    }

    public String scrapeIssue(String repo_id, String issue_id, String file_name) throws IOException {

        RepositoryId repoId = RepositoryId.createFromId(repo_id);
        IssueService issueService = new IssueService();
        RepositoryService repoService = new RepositoryService();

        // Get the repository information to check if it is public
        Repository repository = repoService.getRepository(repoId);
        boolean isPublic = repository.isPrivate() == false;

        if (isPublic) {
            // Retrieve the issue data

            Issue issue = issueService.getIssue(repoId, issue_id);
            // Get the body text of the issue and all comments
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
        System.out.println("Scraping issue: " + url);
        String[] urlParts = url.split("/");
        String repo_id="";
        if(urlParts[0].equalsIgnoreCase("https:") || urlParts[0].equalsIgnoreCase("http:")){
            repo_id = urlParts[3] + "/" + urlParts[4];
        }else if(urlParts[0].equalsIgnoreCase("www.github.com")){
            repo_id = urlParts[1] + "/" + urlParts[2];
        }else{
            System.out.println("Invalid URL");
            return null;
        }
        return repo_id;
    }

    private String getIssueIdFromUrl(String url){
        String[] urlParts = url.split("/");
        String issue_id="";
        if(urlParts[0].equalsIgnoreCase("https:") || urlParts[0].equalsIgnoreCase("http:")){
            issue_id = urlParts[6];
        }else if(urlParts[0].equalsIgnoreCase("www.github.com")){
            issue_id = urlParts[4];
        }else{
            System.out.println("Invalid URL");
            return null;
        }
        return issue_id;

    }

}
