package com.xyz.jdbnk.services;

import com.xyz.jdbnk.exception.InvalidLicense;
import com.xyz.jdbnk.model.TempUser;
import com.xyz.jdbnk.model.User;
import com.xyz.jdbnk.repository.TempUserRepository;
import com.xyz.jdbnk.util.ExcelLookup;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class TempUserService{

    @Autowired
    TempUserRepository repo;

    @Autowired
    VerifyEmailService verifyEmailService;

    @Autowired
    static
    ExcelLookup util;

    public TempUser signup(TempUser user) throws IOException, InvalidFormatException, InvalidLicense {
        String s = user.getLicense().substring(0,6);
        List l = util.read_excel(s);
        if (l.size()<2)
            throw new InvalidLicense("License is invalid");
        else{
            verifyEmailService.verifyEmail(user.getEmail());
            user.setValidFrom((LocalDate) l.get(4));
            user.setValidTill((LocalDate) l.get(5));
            user.setInsType((String) l.get(2));
            user.setOfficeName((String) l.get(1));
            user.setOfficerName((String) l.get(3));
            return repo.save(user);
        }
    }

    public void delete(String email){
        TempUser user = repo.findByEmail(email);
        repo.delete(user);
    }

    public boolean checkEmail(String email) {
        TempUser user = repo.findByEmail(email);
        if (user!=null)
            return false;
        else
            return true;
    }
}
