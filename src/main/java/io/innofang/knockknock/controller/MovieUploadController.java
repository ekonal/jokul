package io.innofang.knockknock.controller;

import io.innofang.knockknock.domain.Result;
import io.innofang.knockknock.enums.ResultEnum;
import io.innofang.knockknock.properties.StorageProperties;
import io.innofang.knockknock.services.StorageService;
import io.innofang.knockknock.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Inno Fang on 2018/4/28.
 */

@RestController
public class MovieUploadController {

    private final StorageService storageService;

    @Autowired
    private StorageProperties storageProperties;

    @Autowired
    public MovieUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/movie-src-list")
    public Result<List<String>> listUploadMovies() {
        return ResultUtil.success(ResultEnum.GET_MOVIE_SRC_LIST,
                storageService.loadAll().map(path ->
                        MvcUriComponentsBuilder.fromMethodName(
                                MovieUploadController.class,
                                "serveMovie",
                                path.getFileName().toString())
                                .build().toString())
                        .collect(Collectors.toList()));
    }

    @GetMapping("/movie/{filename:.+}")
    public ResponseEntity<Resource> serveMovie(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/play/{movie}")
    public Result<String> uploadMovieFile(@PathVariable("movie") String movie) {
        if (storageService.loadAll()
                .noneMatch(path -> path.getFileName().toString().contains(movie))) {
            File file = storageProperties.getSourceFile(movie);
            storageService.store(file);
        }
        return ResultUtil.success(ResultEnum.UPLOAD_MOVIE_FILE,
                MvcUriComponentsBuilder.fromMethodName(
                        MovieUploadController.class, "serveMovie", movie)
                        .build().toString() + ".mp4");
    }

}
