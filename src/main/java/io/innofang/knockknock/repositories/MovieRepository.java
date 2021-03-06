package io.innofang.knockknock.repositories;

import io.innofang.knockknock.domain.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Inno Fang on 2018/4/27.
 */
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findByTitle(String title);

//    @Query("select new io.innofang.knockknock.domain.Movie(movie.title, movie.score, movie.post) " +
//            "from movie, type, movie_type " +
//            "where movie_type.type_id=type.id " +
//            "and movie_type.movie_id=movie.id " +
//            "and type.name=:typeName")
//    List<Movie> findByType(@Param("typeName") String type, Pageable pageable);
}
