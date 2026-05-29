package houseoflagman.service;

import houseoflagman.model.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    public void sendReservationConfirmation(Reservation reservation) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("House of Lagman <kontakt@houseoflagman.ch>");
        message.setTo(reservation.getEmail());
        message.setSubject("Reservationsbestätigung – House of Lagman");
        message.setText(
                "Guten Tag " + reservation.getName() + ",\n\n" +
                        "Vielen Dank für Ihre Reservation bei House of Lagman!\n\n" +
                        "Ihre Reservationsdetails:\n" +
                        "━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                        "Datum:    " + reservation.getDate().format(dateFormatter) + "\n" +
                        "Uhrzeit:  " + reservation.getTime().format(timeFormatter) + " Uhr\n" +
                        "Personen: " + reservation.getPersons() + "\n" +
                        "━━━━━━━━━━━━━━━━━━━━━━━━\n\n" +
                        "Bitte beachten Sie:\n" +
                        "• Ihre Reservation ist für 2 Stunden gültig\n" +
                        "• Bei Nichterscheinen wird der Tisch nach 15 Minuten freigegeben\n" +
                        "• Für Stornierungen kontaktieren Sie uns bitte telefonisch\n\n" +
                        "Wir freuen uns auf Ihren Besuch!\n\n" +
                        "Herzliche Grüsse\n\n" +
                        "House of Lagman\n" +
                        "Aargauerstrasse 2\n" +
                        "5610 Wohlen AG\n" +
                        "Tel: +41 77 267 21 51\n" +
                        "kontakt@houseoflagman.ch\n" +
                        "www.houseoflagman.ch"
        );
        mailSender.send(message);
    }
}