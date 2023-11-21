package lk.ijse.mrGreen.controller;

import com.jfoenix.controls.JFXTextField;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.UserModel;

import java.sql.SQLException;
import java.util.Properties;

public class ForgetFormController {

    @FXML
    public Text txtEmailCheck;


    @FXML
    private JFXTextField txtUserName;

    UserModel userModel = new UserModel();

    @FXML
    void checkOnAction(ActionEvent event) {
        String name = txtUserName.getText();

        try {
            String pw = userModel.getPassword(name);
            String email = userModel.getEmail(name);

            System.out.println("Start");
            SendMailController.Mail mail = new SendMailController.Mail(); //creating an instance of Mail class
            mail.setMsg(pw);
            mail.setTo(email);
            mail.setSubject("Password");

            Thread thread = new Thread(mail);
            thread.start();

            System.out.println("end");
            txtEmailCheck.setVisible(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    @FXML
    void closeOnAction(MouseEvent event) {
        Stage stage = (Stage) txtUserName.getScene().getWindow();
        stage.close();
    }
    public class Mail implements Runnable {
        private String msg;
        private String to;
        private String subject;

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public boolean outMail() throws MessagingException {
            String from = "mrgreen6013@gmail.com";
            String host = "localhost";

            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", 587);
            Session session = Session.getInstance(properties, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("mrgreen6013@gmail.com", "xutb ijio dfed riin");  // have to change some settings in SMTP
                }
            });

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setSubject(this.subject);
            mimeMessage.setText(this.msg);
            Transport.send(mimeMessage);
            return true;
        }

        public void run() {
            if (msg != null) {
                try {
                    outMail();
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("not sent. empty msg!");
            }
        }
    }


}
