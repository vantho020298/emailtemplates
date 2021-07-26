import bean.EmailTemplate;
import dao.ConnectionUtil;
import service.EmailTemplateReader;
import service.EmailTemplateService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Controller {
    public static void main(String[] args) {
        Connection connection = new ConnectionUtil().getConnection("jdbc:postgresql://localhost:5432/moso-insurance?currentSchema=public", "postgres", "postgres");
        List<EmailTemplate> emailTemplates = new EmailTemplateReader().getListEmailTemplate("/home/tholv/Workspaces/moso-insurance/backup");

        try {
            new EmailTemplateService().addEmailTemplatesToBD(emailTemplates, connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        ConnectionUtil.closeConnection(connection);
    }

}
