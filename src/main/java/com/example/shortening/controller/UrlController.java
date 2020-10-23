package com.example.shortening.controller;

import com.example.shortening.dto.UrlDto;
import com.example.shortening.dto.UrlListDto;

import com.example.shortening.model.UrlEntity;
import com.example.shortening.model.UserEntity;
import com.example.shortening.repository.UrlRepository;
import com.example.shortening.repository.UserRepository;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.net.URL;
import java.net.URLConnection;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;


@RestController
public class UrlController {

    private final UrlRepository urlRepository;
    private final UserRepository userRepository;

    public UrlController(UserRepository userRepository, UrlRepository urlRepository) {
        this.userRepository = userRepository;
        this.urlRepository = urlRepository;
    }

    @GetMapping(value = "/findurl/{id}") //gets single url by id
    public UrlDto findUrlEntity(@PathVariable Long id) throws Exception {
        UrlDto urlDto = new UrlDto();
        UrlEntity urlEntity = urlRepository.findById(id)
                .orElseThrow(() -> new Exception("Url entity not found"));
        ModelMapper modelMapper = new ModelMapper();
        urlDto = modelMapper.map(urlEntity, UrlDto.class);
        return urlDto;
    }

    @GetMapping(value = "/getallurls") //gets all urls
    public UrlListDto getAllUrls() {
        List<UrlEntity> urlEntityList = urlRepository.findAll();

        List<UrlDto> urlDtoList = urlEntityList.stream().map(UrlDto::new).collect(Collectors.toList());
        return new UrlListDto(urlDtoList);
    }

    @DeleteMapping(value = "/deleteurl/{id}") //deletes single url by id
    public String deleteUrlEntity(@PathVariable Long id) throws Exception {
        UrlEntity urlEntity = urlRepository.findById(id)
                .orElseThrow(() -> new Exception("Url entity not found"));
        urlRepository.delete(urlEntity);

        return "SUCCESS";
    }

    @GetMapping(value = "/createurl") //creates url
    public String shortenUrl(@NonNull @NotBlank String fullUrl, HttpServletRequest request)
            throws Exception {
        String shortUrl = RandomStringUtils.randomAlphanumeric(6);

        Principal principal = request.getUserPrincipal();
        UserEntity userEntity = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new Exception("userEntity not found"));


        String patter = "^(http|https|ftp)://.*$";

        if (!fullUrl.matches(patter)) {
            fullUrl = "http://" + fullUrl;
        }

        try {
            URL url = new URL(fullUrl);
            URLConnection conn = url.openConnection();
            conn.connect();
        } catch (Exception ex) {
            return "Invalid url!";
        }

        UrlEntity newUrl = new UrlEntity(fullUrl, shortUrl, userEntity, false);

        urlRepository.saveAndFlush(newUrl);

        return shortUrl;
    }

    @GetMapping(value = "/{shortUrl}") //redirects from short to full url
    public ModelAndView redirectUrl(@PathVariable String shortUrl,
                                    HttpServletResponse httpServletResponse) throws Exception {

        UrlEntity urlEntity = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new Exception("entity not found"));

        String redirectUrl = urlEntity.getFullUrl();

        urlEntity.increaseVisitcount();
        urlRepository.save(urlEntity);

        return new ModelAndView("redirect:" + redirectUrl);
    }

    @GetMapping(value = "/favourite/{id}")
    public String addToFavourites(@PathVariable Long id) throws Exception {

        UrlEntity urlEntity = urlRepository.findById(id)
                .orElseThrow(() -> new Exception("entity not found"));

        urlEntity.setFavourite(true);
        urlRepository.save(urlEntity);

        return "Added to favourites!";
    }

    @GetMapping(value = "/removefavourite/{id}")
    public String removeFavourite(@PathVariable Long id) throws Exception {

        UrlEntity urlEntity = urlRepository.findById(id)
                .orElseThrow(() -> new Exception("entity not found"));

        urlEntity.setFavourite(false);
        urlRepository.save(urlEntity);

        return "Removed from favourites!";
    }

    @GetMapping(value = "/getfavourites")
    public List<UrlDto> getFavourites(HttpServletRequest request) throws Exception {

        Principal principal = request.getUserPrincipal();
        UserEntity userEntity = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new Exception("userEntity not found"));

        List<UrlEntity> urlList = urlRepository.findAllByUserAndFavourite(userEntity, true)
                .orElse(new ArrayList<>());

        return urlList.stream().map(UrlDto::new).collect(Collectors.toList());
    }
}
