package org.rrajesh1979.dsa.map;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.rrajesh1979.dsa.model.Contact;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ContactsHashMap {
    private static final String COMMA_DELIMITER = ",";
    static HashMap<String, Contact> contacts = new HashMap<>();
    private static final String END_OF_SCENARIO = "---------------------------------------------------------------------------";
    public static void main(String[] args) {
        initializeContactsHashMap();

        log.info("---------- Printing contacts in Hash Map ----------");
        printContactsHashMap();    //Access all contacts to load the cache
        log.info(END_OF_SCENARIO);

        // Linearly increasing time to search for a name
        log.info("---------- Linearly increasing time to search for a name in Linked List ----------");
        getContactByName("Rebbecca Didio");
        getContactByName("Edda Mcquaide");
        getContactByName("Thad Puskarich");
        getContactByName("Chauncey Mcelreath");
        getContactByName("Antonio Unruh");
        getContactByName("Tony Stark");
        log.info(END_OF_SCENARIO);

        // Constant time to insert a new contact at an index
        log.info("---------- Inserting a new contact at an index in Linked List ----------");
        Contact newContact = new Contact("Tony", "Stark", "California", "111-222-333", "123456789");
        insertContact(newContact);
        log.info(END_OF_SCENARIO);

        // Constant time to delete a contact at an index
        log.info("---------- Deleting a contact at an index in Linked List ----------");
        deleteContact("Rebbecca Didio");
        deleteContact("Thad Puskarich");
        deleteContact("Antonio Unruh");
        log.info(END_OF_SCENARIO);

    }

    private static void initializeContactsHashMap() {
        log.info("Initializing contacts Hash Map");
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/contacts.csv"))) {
            String header = br.readLine(); //Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                Contact contact = new Contact(values[0], values[1], values[2], values[3], values[4]);
                contacts.put(contact.getFullName(), contact);
            }
        } catch (Exception e) {
            log.error("Error while reading contacts file", e);
        }
    }

    public static void printContactsHashMap() {
        log.info("Printing contacts");
        for (Contact contact : contacts.values()) {
            if (contact != null) {
                log.info("Contact: {}, Email: {}, Phone: {}, Address: {}", contact.getFullName(), contact.getEmail(), contact.getPhone(), contact.getAddress());
            }
        }
    }

    public static void getContactByName(String name) {
        log.info("Searching contact {} in Linked List", name);
        Stopwatch timer = Stopwatch.createUnstarted();
        timer.start();

        Contact contact = contacts.get(name);

        timer.stop();
        if (contact != null ) {
            log.info("Contact found: {}, Email: {}, Phone: {}, Address: {}", contact.getFullName(), contact.getEmail(), contact.getPhone(), contact.getAddress());
        } else {
            log.info("Contact not found");
        }
        log.info("Time taken: {}", timer.elapsed(TimeUnit.MICROSECONDS));
    }

    public static void insertContact(Contact contact) {
        log.info("Inserting contact in Hash Map");
        Stopwatch timer = Stopwatch.createUnstarted();
        timer.start();

        contacts.put(contact.getFullName(), contact);

        timer.stop();
        log.info("Contact inserted");
        log.info("Time taken: {}", timer.elapsed(TimeUnit.MICROSECONDS));
    }

    public static void deleteContact(String name) {
        log.info("Deleting contact {} from Hash Map", name);
        Stopwatch timer = Stopwatch.createUnstarted();
        timer.start();

        contacts.remove(name);

        timer.stop();
        log.info("Contact deleted");
        log.info("Time taken: {}", timer.elapsed(TimeUnit.MICROSECONDS));
    }
}
