package service;

import bean.EmailTemplate;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmailTemplateReader {

    public List<EmailTemplate> getListEmailTemplate() {
        File folder = new File("C:\\Users\\USER\\Desktop\\ahihi");
        File[] listOfFiles = folder.listFiles();
        JSONParser jsonParser = new JSONParser();
        List<EmailTemplate> emailTemplates = new ArrayList<EmailTemplate>();

        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".json")) {
                try {
                    FileReader reader = new FileReader(file.getPath());

                    JSONObject obj = (JSONObject) jsonParser.parse(reader);
                    emailTemplates.add(parseEmployeeObject(obj, file));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        return emailTemplates;
    }

    private EmailTemplate parseEmployeeObject(JSONObject employee, File file) throws IOException {
        String content = Jsoup.parse(new File(getFileNameHtml(file, (String) employee.get("id"))), "UTF-8").outerHtml();
        EmailTemplate emailTemplate = new EmailTemplate();

        emailTemplate.setId((String) employee.get("id"));
        emailTemplate.setSubject((String) employee.get("subject"));
        emailTemplate.setSender((String) employee.get("sender"));
        emailTemplate.setRecipients((List<String>) employee.get("recipients"));
        emailTemplate.setCc((List<String>) employee.get("cc"));
        emailTemplate.setContent(content);
        return emailTemplate;
    }

    private String getFileNameHtml(File file, String id) {
        return file.getParent().concat("\\").concat(id).concat(".html");
    }
}
