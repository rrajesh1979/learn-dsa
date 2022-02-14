package org.rrajesh1979.dsa.benchmark;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.rrajesh1979.dsa.model.Contact;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.TimeUnit;

@Slf4j
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5)
public class ArrayPerformanceBenchmark {
    @State(Scope.Benchmark)
    public static class MyState {
        private static final String COMMA_DELIMITER = ",";
        static Contact[] contacts = new Contact[2000];

        Contact contact = new Contact("Tony", "Stark", "Earth", "111-222-3333", "tony@avengers.com");

        @Setup(Level.Trial)
        public void setUp() {
            initializeContactsArray();
        }

        private static void initializeContactsArray() {
            try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/contacts.csv"))) {
                String header = br.readLine(); //Skip header
                String line;
                int i = 0;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(COMMA_DELIMITER);
                    contacts[i] = new Contact(values[0], values[1], values[2], values[3], values[4]);
                    i++;
                }
            } catch (Exception e) {
                log.error("Error while reading contacts file", e);
            }
        }

        public static void printContactsArray() {
            for (Contact contact : contacts) {
                if (contact != null) {
                    log.info("Contact: {}, Email: {}, Phone: {}, Address: {}", contact.getFullName(), contact.getEmail(), contact.getPhone(), contact.getAddress());
                }
            }
        }

        public static void getContactByName(String name) {
            for (Contact contact : contacts) {
                if (contact != null && contact.getFullName().equalsIgnoreCase(name)) {
                    return;
                }
            }
        }

        public static void getContactByIndex(int index) {
            Contact contact = contacts[index];
        }

        public static Contact[] insertContactAtIndex(int index, Contact contact) {
            Contact[] newContacts = new Contact[contacts.length + 1];
            for (int i = 0; i < contacts.length + 1; i++) {
                if (i < index) {
                    newContacts[i] = contacts[i];
                } else if (i == index) {
                    newContacts[i] = contact;
                } else {
                    newContacts[i] = contacts[i - 1];
                }
            }
            return newContacts;
        }

        public static Contact[] deleteContactAtIndex(int index) {
            Contact[] newContacts = new Contact[contacts.length - 1];
            for (int i = 0; i < contacts.length - 1; i++) {
                if (i < index) {
                    newContacts[i] = contacts[i];
                } else {
                    newContacts[i] = contacts[i + 1];
                }
            }
            return newContacts;
        }
    }

    @Benchmark
    public void benchmarkGetContactByName(MyState state) {
        state.getContactByName("Thad Puskarich");
    }

    @Benchmark
    public void benchmarkGetContactNotInArray(MyState state) {
        state.getContactByName("Tony Stark");
    }

    @Benchmark
    public void benchmarkGetContactByIndex(MyState state) {
        state.getContactByIndex(100);
    }

    @Benchmark
    public void benchmarkInsertContactAtIndex(MyState state) {
        state.insertContactAtIndex(100, state.contact);
    }

    @Benchmark
    public void benchmarkDeleteContactAtIndex(MyState state) {
        state.deleteContactAtIndex(100);
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(ArrayPerformanceBenchmark.class.getSimpleName()).threads(1)
                .measurementIterations(20)
                .forks(1).shouldFailOnError(true)
                .shouldDoGC(true)
                .jvmArgs("-server").build();
        new Runner(options).run();
    }

}
