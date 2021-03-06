package io.innofang.knockknock.controller;

import io.innofang.knockknock.domain.Movie;
import io.innofang.knockknock.domain.Result;
import io.innofang.knockknock.enums.ResultEnum;
import io.innofang.knockknock.exception.MovieNotFoundException;
import io.innofang.knockknock.repositories.MovieRepository;
import io.innofang.knockknock.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Inno Fang on 2018/4/27.
 */
@RestController
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping(value = "/movie-list/{page}")
    public Result<List<Movie>> getMovieInfoList(@PathVariable("page") int page) {
        Pageable pageable = PageRequest.of(page, 12);
        Page<Movie> movies = movieRepository.findAll(pageable);
        return ResultUtil.success(ResultEnum.GET_MOVIE_INFO_LIST, movies.getContent());
    }


    @GetMapping(value = "/{title}")
    public Result<Movie> getMovieDetail(@PathVariable("title") String title) {
        Movie movie = movieRepository.findByTitle(title);
        if (null == movie) {
            throw new MovieNotFoundException(ResultEnum.MOVIE_NOT_FOUND);
        }
        return ResultUtil.success(ResultEnum.GET_MOVIE_DETAIL, movie);
    }

}
