package org.rrajesh1979.dsa.list;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.rrajesh1979.dsa.model.Contact;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ContactsArray {
    private static final String COMMA_DELIMITER = ",";
    static Contact[] contacts = new Contact[2000];
    public static void main(String[] args) {
        initializeContacts();
        printContacts();
        searchContact("Tony Stark");
        searchContact("Kate Price");
    }

    private static void initializeContacts() {
        log.info("Initializing contacts");
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/contacts.csv"))) {
            String header = br.readLine(); //Skip header
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                contacts[i] = new Contact(values[0], values[1], values[4], values[7], values[9]);
                i++;
            }
        } catch (Exception e) {
            log.error("Error while reading contacts file", e);
        }
    }

    public static void printContacts() {
        log.info("Printing contacts");
        for (Contact contact : contacts) {
            if (contact != null) {
                log.info("Contact: {}, Email: {}, Phone: {}, Address: {}", contact.getFullName(), contact.getEmail(), contact.getPhone(), contact.getAddress());
            }
        }
    }

    public static void searchContact(String name) {
        log.info("Searching contact {}", name);
        Stopwatch timer = Stopwatch.createUnstarted();
        timer.start();
        for (Contact contact : contacts) {
            if (contact != null && contact.getFullName().equalsIgnoreCase(name)) {
                timer.stop();
                log.info("Contact found: {}, Email: {}, Phone: {}, Address: {}", contact.getFullName(), contact.getEmail(), contact.getPhone(), contact.getAddress());
                log.info("Time taken: {}", timer.elapsed(TimeUnit.MICROSECONDS));
                return;
            }
        }
        timer.stop();
        log.info("Contact not found");
        log.info("Time taken: {}", timer.elapsed(TimeUnit.MICROSECONDS));
    }
}
