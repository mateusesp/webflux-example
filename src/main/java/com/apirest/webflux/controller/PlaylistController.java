package com.apirest.webflux.controller;

import com.apirest.webflux.document.Playlist;
import com.apirest.webflux.services.PlaylistService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@CrossOrigin(origins = "*")
@RestController
public class PlaylistController {

    private static final Logger logger = LoggerFactory.getLogger(PlaylistController.class);

    @Autowired
    PlaylistService service;

    @GetMapping(value = "/playlist")
    public Flux<Playlist> getPlaylist() {
        return service.findAll();
    }

    @GetMapping(value = "/playlist/{id}")
    public Mono<Playlist> getPlaylistId(@PathVariable String id) {
        return service.findById(id);
    }

    @PostMapping(value = "/playlist")
    public Mono<Playlist> save(@RequestBody Playlist playlist) {
        return service.save(playlist);
    }

    @GetMapping(value = "/playlist/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Tuple2<Long, Playlist>> getPlaylistByEvents() {

        logger.info("Start get Playlists by WEBFLUX");
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
        Flux<Playlist> events = service.findAll();
        return Flux.zip(interval, events);
    }

    @GetMapping(value = "/playlist/mvc")
    public List<String> getPlaylistByMvc() throws InterruptedException {

        logger.info("Start get Playlists by MVC");


        List<String> playlistList = new ArrayList<>();
        playlistList.add("Java 8");
        playlistList.add("Spring Security");
        playlistList.add("Github");
        playlistList.add("Deploy de uma aplicação java no IBM Cloud");
        playlistList.add("Bean no Spring Framework");
        TimeUnit.SECONDS.sleep(1);

        return playlistList;
    }

}
