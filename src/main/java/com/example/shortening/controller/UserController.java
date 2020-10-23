package com.example.shortening.controller;

import com.example.shortening.dto.PostUserDto;
import com.example.shortening.dto.UrlListDto;
import com.example.shortening.dto.UserDto;
import com.example.shortening.dto.UserListDto;
import com.example.shortening.model.UserEntity;
import com.example.shortening.repository.UrlRepository;
import com.example.shortening.repository.UserRepository;
import lombok.Data;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController  {

    private UserRepository userRepository;
    private UrlRepository urlRepository;

    public UserController(UrlRepository urlRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.urlRepository = urlRepository;
    }

    @GetMapping(value="/finduser") //finds single user by username
    public UserDto findUserEntity(@NonNull @NotBlank String username) throws Exception {
        UserDto userDto = new UserDto();
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new Exception("userEntity not found"));
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userEntity, UserDto.class);
    }

    @GetMapping(value="/getallusers") //finds all users
    public UserListDto getAllUsers() {
        List<UserEntity> userEntityList = userRepository.findAll();

        List<UserDto> userDtoList = userEntityList.stream().map(UserDto::new).collect(Collectors.toList());
        return new UserListDto(userDtoList);
    }

    @GetMapping(value="/userurlList") //gets all urls connected to a single user
    public UrlListDto getUrlList(HttpServletRequest request) throws Exception {
        Principal principal = request.getUserPrincipal();
        UserEntity userEntity = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new Exception("userEntity not found"));
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userEntity, UrlListDto.class);
    }

    @PostMapping(value = "/createuser") //creates user
    ResponseEntity<String> createUser(@Valid @RequestBody PostUserDto userDto) {
        ModelMapper modelMapper = new ModelMapper();
        userRepository.saveAndFlush(modelMapper.map(userDto, UserEntity.class));
        return ResponseEntity.ok("User created succesfully");
    }

    @DeleteMapping(value = "/deleteuser") //deletes user account
    public String deleteUser(HttpServletRequest request) throws Exception {
        Principal principal = request.getUserPrincipal();
        UserEntity userEntity = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new Exception("userEntity not found"));

        if(userEntity.getUrlList() != null) {
            userEntity.getUrlList().forEach((urlEntity -> urlRepository.delete(urlEntity)));
        } userRepository.delete(userEntity);
        return "User deleted succesfully!";
    }

    @PutMapping(value = "/updateuser") //updates user information
    public String updateUser(@RequestBody PostUserDto userDto) {
        UserEntity userEntity = new UserEntity();
        ModelMapper modelMapper = new ModelMapper();
        userEntity = modelMapper.map(userDto, UserEntity.class);
        userRepository.save(userEntity);
        return "User updated succesfully!";
    }
}
