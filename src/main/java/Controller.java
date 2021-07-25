import bean.EmailTemplate;
import dao.ConnectionUtil;
import service.EmailTemplateReader;
import service.EmailTemplateService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Controller {
    public static void main(String[] args) {
        Connection connection = new ConnectionUtil().getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");
        List<EmailTemplate> emailTemplates = new EmailTemplateReader().getListEmailTemplate();

        try {
            new EmailTemplateService().addEmailTemplatesToBD(emailTemplates, connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        ConnectionUtil.closeConnection(connection);
    }

}
