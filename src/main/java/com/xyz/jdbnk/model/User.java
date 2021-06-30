package com.xyz.jdbnk.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "user")
public class User {

    @Id
    private String userId;
    private String email;
    private String password;
    private String name;
    private LocalDate dob;
    private long mob;
    private String license;
    private String insType;
    private LocalDate validFrom;
    private LocalDate validTill;
    private String officeName;
    private String officerName;
    private String role;
    private Sales totalSales;
    private String insurer;
}
