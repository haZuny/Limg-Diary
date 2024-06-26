package com.hayden.limg_diary.service;

import com.hayden.limg_diary.dto.UserDto;
import com.hayden.limg_diary.entity.User;
import com.hayden.limg_diary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<Integer> getIdFromUsername(String username){
        try{
            User user = userRepository.findByUsername(username);
            return Optional.of(user.getUserid());
        } catch (UsernameNotFoundException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public boolean signUp(UserDto.UserSignupDto userDto){

        // null check
        if (userDto.getUsername().isEmpty())   return false;
        if (userDto.getPassword().isEmpty())   return false;

        // duplicate check
        if (userRepository.existsByUsername(userDto.getUsername()))    return false;

        // password match check
        if (!userDto.getPassword().equals(userDto.getPassword2()))    return false;

        // password encode
        BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bcryptEncoder.encode(userDto.getPassword());

        // generate entity obj
        User userEntity = new User();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(encodedPassword);
        userEntity.setRole("ROLE_USER");

        // db 저장
        try{
            userRepository.save(userEntity);
        } catch (Exception e){return false;}

        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
