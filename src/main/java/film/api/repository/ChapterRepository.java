package film.api.repository;

import film.api.models.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {

    @Query("SELECT c FROM Chapter c WHERE c.Film.Id = :filmID")
    List<Chapter> getChapterByFilmID(@Param("filmID") Long filmID);

    @Query("SELECT c FROM Chapter c WHERE c.Film.Id = (SELECT ch.Film.Id FROM Chapter ch WHERE ch.Id = :id)")
    List<Chapter> chapterByChapterId(@Param("id") Long id);

    @Query("SELECT c FROM Chapter c WHERE c.Id = :id")
    Chapter ChapterByIdChapter(@Param("id") Long id);

    @Query("SELECT c FROM Chapter c WHERE c NOT IN :chapterIDList")
    List<Chapter> findAllByIdNotIn(@Param("chapterIDList") List<Chapter> chapterIDList);

    @Query("SELECT c FROM Chapter c ORDER BY c.ChapterCreateDay DESC")
    List<Chapter> Newest();

    @Query("SELECT MAX(c.ChapterNumber) FROM Chapter c WHERE c.Film.Id = :idFilm")
    Integer chapternumberbyIdFilmInt(@Param("idFilm") Long idFilm);

    @Query("select c from Chapter c join History h on h.Chapter.Id = c.Id where h.User.Id =:idUser")
    List<Chapter> UserWatched(@Param("idUser") Long idUser);

//    @Query("select c.Id from Chapter c where c.idChapter=:idChapter")
//    Long getId(@Param("idChapter") String idChapter);
}
