package org.rrajesh1979.dsa.list;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.rrajesh1979.dsa.model.Contact;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ContactsLinkedList {
    private static final String COMMA_DELIMITER = ",";
    static LinkedList<Contact> contacts = new LinkedList<>();
    private static final String END_OF_SCENARIO = "---------------------------------------------------------------------------";
    public static void main(String[] args) {
        initializeContactsLinkedList();

        log.info("---------- Printing contacts in Linked List ----------");
        printContacts();    //Access all contacts to load the cache
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

        // Constant time to get a contact by index
        log.info("---------- Constant time to get a contact by index in Linked List ----------");
        getContactByIndex(0);
        getContactByIndex(100);
        getContactByIndex(300);
        log.info(END_OF_SCENARIO);

        // Constant time to insert a new contact at an index
        log.info("---------- Inserting a new contact at an index in Linked List ----------");
        Contact newContact = new Contact("Tony", "Stark", "California", "111-222-333", "123456789");
        insertContactAtIndex(0, newContact);
        insertContactAtIndex(100, newContact);
        insertContactAtIndex(300, newContact);
        log.info(END_OF_SCENARIO);

        // Constant time to delete a contact at an index
        log.info("---------- Deleting a contact at an index in Linked List ----------");
        deleteContactAtIndex(0);
        deleteContactAtIndex(100);
        deleteContactAtIndex(300);
        log.info(END_OF_SCENARIO);

    }

    private static void initializeContactsLinkedList() {
        log.info("Initializing contacts Linked List");
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/contacts.csv"))) {
            String header = br.readLine(); //Skip header
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                contacts.add(new Contact(values[0], values[1], values[2], values[3], values[4]));
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

    public static void getContactByName(String name) {
        log.info("Searching contact {} in Linked List", name);
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

    public static void getContactByIndex(int index) {
        log.info("Getting contact at array index {}", index);
        Stopwatch timer = Stopwatch.createUnstarted();
        timer.start();
        Contact contact = contacts.get(index);
        timer.stop();
        log.info("Contact found: {}, Email: {}, Phone: {}, Address: {}", contact.getFullName(), contact.getEmail(), contact.getPhone(), contact.getAddress());
        log.info("Time taken: {}", timer.elapsed(TimeUnit.MICROSECONDS));
    }

    public static void insertContactAtIndex(int index, Contact contact) {
        log.info("Inserting contact at Linked List index {}", index);
        Stopwatch timer = Stopwatch.createUnstarted();
        timer.start();

        contacts.add(index, contact);

        timer.stop();
        log.info("Contact inserted");
        log.info("Time taken: {}", timer.elapsed(TimeUnit.MICROSECONDS));
    }

    public static void deleteContactAtIndex(int index) {
        log.info("Deleting contact at Linked List index {}", index);
        Stopwatch timer = Stopwatch.createUnstarted();
        timer.start();
        contacts.remove(index);
        timer.stop();
        log.info("Contact deleted");
        log.info("Time taken: {}", timer.elapsed(TimeUnit.MICROSECONDS));
    }
}
