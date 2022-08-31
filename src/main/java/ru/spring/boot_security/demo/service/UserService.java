package ru.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.spring.boot_security.demo.model.Role;
import ru.spring.boot_security.demo.model.User;
import ru.spring.boot_security.demo.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {


    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
    }

    public List<User> index() {
        return userRepository.findAll();
    }

    public User getUserById(long id) {
        return userRepository.findById(id).get();
    }

    public void addUser(User user) {
        List<Role> roles = (List<Role>) user.getRoles();
        if(roles.size()==2){
            roles = roleByName(new String[]{"USER","ADMIN"});
            user.setRoles(roles);
        }
        else if (roles.get(0).getId()==2){
            roles = roleByName(new String[]{"ADMIN"});
            user.setRoles(roles);
        }
        else if (roles.get(0).getId()==1){
            roles = roleByName(new String[]{"USER"});
            user.setRoles(roles);
        }
        userRepository.save(user);
    }

    public List<Role> roleByName(String[] roles){
        List<Role> l = new ArrayList<>();
        for (int i = 0; i < roles.length; i++){
            if (roles[i].equals("USER")){
                l.add(new Role(2L,"ROLE_USER"));
            }
            if (roles[i].equals("ADMIN")){
                l.add(new Role(1L,"ROLE_ADMIN"));
            }
        }
        return l;
    }

    public void removeUser(long id) {
        userRepository.deleteById(id);
    }

    public void updateUser(User user) {
        List<Role> roles = (List<Role>) user.getRoles();
        if(roles.size()==2){
            roles = roleByName(new String[]{"USER","ADMIN"});
            user.setRoles(roles);
        }
        else if (roles.get(0).getId()==2){
            roles = roleByName(new String[]{"ADMIN"});
            user.setRoles(roles);
        }
        else if (roles.get(0).getId()==1){
            roles = roleByName(new String[]{"USER"});
            user.setRoles(roles);
        }
        userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }
}
