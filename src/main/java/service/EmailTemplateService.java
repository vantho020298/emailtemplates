package service;

import bean.EmailTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class EmailTemplateService {
    public void addEmailTemplatesToBD(List<EmailTemplate> emailTemplates, Connection connection) throws SQLException {
        PreparedStatement statement = null;

        removeEmailTemplatesInDB(connection, statement);

        for (EmailTemplate emailTemplate : emailTemplates) {
            addEmailTemplateToBD(emailTemplate, connection, statement);
        }

        if (statement != null) {
            statement.close();
        }
    }

    private void addEmailTemplateToBD(EmailTemplate emailTemplate, Connection connection, PreparedStatement statement) throws SQLException {
        String sql = "INSERT INTO email_template(id, subject, sender, template, created_by) VALUES(?,?,?,?,?)";
        statement = connection.prepareStatement(sql);
        statement.setString(1, emailTemplate.getId());
        statement.setString(2, emailTemplate.getSubject());
        statement.setString(3, emailTemplate.getSender());
        statement.setString(4, emailTemplate.getContent());
        statement.setString(5, "system");
        statement.executeUpdate();

        for (String recipient : emailTemplate.getRecipients()) {
            sql = "INSERT INTO email_template_recipients VALUES(?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, emailTemplate.getId());
            statement.setString(2, recipient);
            statement.executeUpdate();
        }

        if (emailTemplate.getCc() != null) {
            for (String data : emailTemplate.getCc()) {
                sql = "INSERT INTO email_template_cc VALUES(?,?)";
                statement = connection.prepareStatement(sql);
                statement.setString(1, emailTemplate.getId());
                statement.setString(2, data);
                statement.executeUpdate();
            }
        }

        if (emailTemplate.getBcc() != null) {
            for (String data : emailTemplate.getBcc()) {
                sql = "INSERT INTO email_template_bcc VALUES(?,?)";
                statement = connection.prepareStatement(sql);
                statement.setString(1, emailTemplate.getId());
                statement.setString(2, data);
                statement.executeUpdate();
            }
        }

        if (emailTemplate.getTags() != null) {
            for (String data : emailTemplate.getTags()) {
                sql = "INSERT INTO email_template_tags VALUES(?,?)";
                statement = connection.prepareStatement(sql);
                statement.setString(1, emailTemplate.getId());
                statement.setString(2, data);
                statement.executeUpdate();
            }
        }
    }

    private void removeEmailTemplatesInDB(Connection connection, PreparedStatement statement) throws SQLException {
        String sql = "DELETE FROM email_template_cc";
        statement = connection.prepareStatement(sql);
        statement.executeUpdate();

        sql = "DELETE FROM email_template_bcc";
        statement = connection.prepareStatement(sql);
        statement.executeUpdate();

        sql = "DELETE FROM email_template_tags";
        statement = connection.prepareStatement(sql);
        statement.executeUpdate();

        sql = "DELETE FROM email_template_recipients";
        statement = connection.prepareStatement(sql);
        statement.executeUpdate();

        sql = "DELETE FROM email_template";
        statement = connection.prepareStatement(sql);
        statement.executeUpdate();
    }
}
