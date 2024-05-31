package com.sparta.todoproject_jwt.service;

import com.sparta.todoproject_jwt.dto.SignInRequestDto;
import com.sparta.todoproject_jwt.dto.SignUpRequestDto;
import com.sparta.todoproject_jwt.dto.SignUpResponseDto;
import com.sparta.todoproject_jwt.entity.User;
import com.sparta.todoproject_jwt.jwt.JwtUtil;
import com.sparta.todoproject_jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public SignUpResponseDto signUp(SignUpRequestDto requestDto){
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        Optional<User> checkUsername = userRepository.findByUsername(username);
        if(checkUsername.isPresent()){
            throw new IllegalArgumentException("중복된 username 입니다.");
        }

        User user = new User(username, password);
        userRepository.save(user);
        return new SignUpResponseDto(user);
    }

    public String signIn(SignInRequestDto requestDto){
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("회원을 찾을 수 없습니다.")
        );

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("회원을 찾을 수 없습니다.");
        }

       return jwtUtil.createToken(user.getUsername());


    }
}
