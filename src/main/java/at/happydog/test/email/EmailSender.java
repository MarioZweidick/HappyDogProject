package at.happydog.test.email;

/**
 EmailSender interface

 This interface defines the send method for the EmailService class
 **/
public interface EmailSender {
    void send(String to, String email);
}
