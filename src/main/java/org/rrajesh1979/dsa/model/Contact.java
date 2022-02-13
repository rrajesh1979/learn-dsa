package org.rrajesh1979.dsa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class Contact {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private String email;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
