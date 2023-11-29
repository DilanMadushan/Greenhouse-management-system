package lk.ijse.mrGreen.controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import jakarta.mail.Authenticator;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.*;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.InternetAddress;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

//import javax.mail.*;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Pattern;

public class SendMailController {
    @FXML
    private AnchorPane Anchor;

    @FXML
    private Label lblStatus;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextArea txtMessage;

    @FXML
    private JFXTextField txtSubject;

    @FXML
    void backOnAction(MouseEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/DashBoard.fxml"));
        Stage stage = (Stage) Anchor.getScene().getWindow();

        Scene scene=new Scene(rootNode);
        stage.setScene(scene);
        stage.centerOnScreen();

    }

    @FXML
    void btnSendOnAction(ActionEvent event) {

        boolean isvalid = validation();

        if (isvalid) {

            System.out.println("Start");
            lblStatus.setText("sending...");
            Mail mail = new Mail(); //creating an instance of Mail class
            mail.setMsg(txtMessage.getText());//email message
            mail.setTo(txtEmail.getText()); //receiver's mail
            mail.setSubject(txtSubject.getText()); //email subject

            Thread thread = new Thread(mail);
            thread.start();

            System.out.println("end");
            lblStatus.setText("sent successfully");
            clearFields();

        }
    }

    private void clearFields() {
        txtEmail.clear();
        txtMessage.clear();
        txtSubject.clear();
    }

    private boolean validation() {
        String email=txtEmail.getText();
        boolean isMatch = Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",email);
        if (!isMatch) {
            new Alert(Alert.AlertType.ERROR,"invalid email").show();
            return false;
        }
        return true;
    }

    public void emailOnAction(ActionEvent event) {
        txtSubject.requestFocus();
    }

    public void subjectOnAction(ActionEvent event) {
        txtMessage.requestFocus();
    }

    public static class Mail implements Runnable{
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
            String from = "mrgreen6013@gmail.com"; //sender's email address
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
