package com.xyz.jdbnk.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "temp_user")
public class TempUser {
    @Id
    private String userId;
    private String email;
    private String password;
    private String name;
    private LocalDate dob;
    private long mob;
    private String license;
    private LocalDate validFrom;
    private LocalDate validTill;
    private String insType;
    private String officeName;
    private String officerName;
}
