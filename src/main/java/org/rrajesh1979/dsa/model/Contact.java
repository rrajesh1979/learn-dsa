package org.rrajesh1979.dsa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class Contact {
    private String name;
    private String email;
    private String phone;
    private String address;
}
