package com.xyz.jdbnk.services;

import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.xyz.jdbnk.model.*;
import com.xyz.jdbnk.repository.TempUserRepository;
import com.xyz.jdbnk.repository.UserRepository;
import com.xyz.jdbnk.security.AppUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserService implements UserDetailsService {

    private AmazonClient amazonClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TempUserRepository tempUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        user.orElseThrow(()->new UsernameNotFoundException("User not found"));
        return user.map(AppUserDetails::new).get();
    }
    public void signup(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User verify(String email){
        TempUser user = tempUserRepository.findByEmail(email);
        User a = new User();
        a.setName(user.getName());
        a.setDob(user.getDob());
        a.setEmail(user.getEmail());
        a.setLicense(user.getLicense());
        a.setInsType(user.getInsType());
        a.setMob(user.getMob());
        a.setOfficeName(user.getOfficeName());
        a.setOfficerName(user.getOfficerName());
        a.setValidFrom(user.getValidFrom());
        a.setValidTill(user.getValidTill());
        a.setPassword(passwordEncoder.encode(user.getPassword()));
        a.setRole(Role.AGENT);
        Sales sales = new Sales(new ArrayList<>(),0);
        a.setTotalSales(sales);
        a.setInsurer("");
        tempUserRepository.delete(user);
        return userRepository.save(a);
    }

    public User getUser(String email) throws UsernameNotFoundException{
        Optional<User> user =userRepository.findByEmail(email);
        user.orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
        return user.get();
    }
    public boolean checkEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    public List<User> getAllAgent() {
        return userRepository.findAllAgent();
    }

    public User addToSales(String email, Policy policy, String customerId, String customerName, String url){
        User user = userRepository.findByEmail(email).get();
        Sales sales = (user.getTotalSales()==null)?new Sales(new ArrayList<>(),0):user.getTotalSales();
        sales.setTotal(sales.getTotal()+1);
        List<SoldItem>items = sales.getSoldItems();
        items.add(new SoldItem(UUID.randomUUID().toString(),policy,customerId,customerName,"https://customer-documen.s3.amazonaws.com/"+url,LocalDate.now()));
        sales.setSoldItems(items);
        user.setTotalSales(sales);
        return userRepository.save(user);
    }

    public void deleteSoldItem(String email, String id){
        User user = userRepository.findByEmail(email).get();
        Sales sales = user.getTotalSales();
        List<SoldItem> items = sales.getSoldItems();
        for(int i=0;i<items.size();i++){
            if(items.get(i).getId().equals(id)){
                String url = items.get(i).getDocumentUrl();
                items.remove(i);
                sales.setSoldItems(items);
                sales.setTotal(sales.getTotal()-1);
                user.setTotalSales(sales);
                userRepository.save(user);
                break;
            }
        }
    }

    public List getSold(User user) {
        Sales sales = user.getTotalSales();
        List<SoldItem> items = sales.getSoldItems();
        return items;
    }
    public User updateUser(User user) {
        return userRepository.save(user);
    }

}
