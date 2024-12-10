package film.api.controller;

import film.api.DTO.request.ContextRequestDTO;
import film.api.service.*;
import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ApiV1")
//@Slf4j
@CrossOrigin("*")
@RequiredArgsConstructor
public class HistoryController {
//    @Value("${jwt.header}")
//    private String tokenHeader;




//    @PostMapping("/recommendation")
//    public ResponseEntity<?> recommendMovies(HttpServletRequest request,
//                                      @RequestBody ContextRequestDTO contextRequestDTO) {
//        String token = request.getHeader(tokenHeader).substring(7);
//        String username = jwtUtil.getUsernameFromToken(token);
//        int topN = 4;
//        return new ResponseEntity<>(recommendationService.recommend(username, contextRequestDTO, topN),HttpStatus.OK);
//    }
}
