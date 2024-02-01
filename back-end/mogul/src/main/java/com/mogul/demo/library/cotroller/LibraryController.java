package com.mogul.demo.library.cotroller;

import com.mogul.demo.library.dto.*;
import com.mogul.demo.library.service.LibraryService;
import com.mogul.demo.util.CustomResponse;
import com.mogul.demo.webtoon.service.WebtoonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.messaging.SubscriptionRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/library")
@RequiredArgsConstructor
@Tag(name = "Library", description = "서재 API")
public class LibraryController {
    private final LibraryService libraryService;

    private final WebtoonService webtoonService;

    @GetMapping("/hot")
    @Operation(summary = "인기 서재 Read API", description = "서재탭의 메인 페이지에서 사용할 인기 서재 목록을 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    }, parameters = {
            @Parameter(name = "pno", description = "조회할 인기 서재 페이지의 번호 0부터 시작"),
            @Parameter(name = "count", description = "조회할 인기 서재 페이지의 크기"),
    })
    public ResponseEntity<CustomResponse> libraryListHot(@RequestParam("pno") int pageNumber, @RequestParam("count") int pageSize){
        List data = libraryService.findLibrariesHot(pageNumber, pageSize);
        CustomResponse res = new CustomResponse<List>(200, data, "인기 서재 목록 조회 성공");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "서재 목록 Read API", description = "특정 사용자의 서재 목록 조회", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    public ResponseEntity<CustomResponse> libraryList(){
        long userId = 1; // 로그인 구현 후 변경 요망!!!!!!!!
        List data = libraryService.findLibrariesByUserId(userId);
        CustomResponse res = new CustomResponse<List>(200, data, "사용자" + userId + "의 서재 목록 조회 성공");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

    @GetMapping("/{library-id}")
    @Operation(summary = "서재 상세 Read API", description = "서재 id로 특정 서재상세와 서재에 포함된 웹툰목록 조회", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    }, parameters = {
            @Parameter(name = "library-id", description = "조회할 서재의 id")
    })
    public ResponseEntity<CustomResponse> libraryDetail(@PathVariable("library-id") Long libraryId){
        Map<String, Object> data = new HashMap<>();
        data.put("libaray_detail", libraryService.findLibraryById(libraryId));
        data.put("included_webtoon", webtoonService.findWebtoonsByLibraryId(libraryId));
        CustomResponse res = new CustomResponse<Map>(200, data, "서재" + libraryId+"의 상세 정보 조회 성공");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "서재 Create API", description = "빈 서재를 등록 합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식")
    }, parameters = {
            @Parameter(name = "libraryCreateRequest", description = "라리브러리 이름을 포함한 요청 형식", content = {
                    @Content(schema = @Schema(implementation = LibraryCreateRequest.class))
            })
    })
    public ResponseEntity<CustomResponse> libraryAdd(@RequestBody @Valid LibraryCreateRequest libraryCreateRequest, BindingResult bindingResult){
        CustomResponse res;
        if(bindingResult.hasErrors()){
            res = new CustomResponse(400, null, "잘못된 요청 형식 입니다.");
        }else{
            long userId = 1; // 로그인 구현 후 변경 요망!!!!!!!!
            libraryCreateRequest.setUserId(userId);
            Long data = libraryService.addLibrary(libraryCreateRequest);
            res = new CustomResponse<Long>(200, data, "서재 생성 성공");
        }
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }


    @DeleteMapping("/{library-id}")
    @Operation(summary = "서재 Delete API", description = "서재 id로 특정 서재를 삭제합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "삭제 실패")
    }, parameters = {
            @Parameter(name = "library-id", description = "삭제할 서재의 id")
    })
    public ResponseEntity<CustomResponse> libraryRemove(@PathVariable("library-id") Long id){
        CustomResponse res;
        boolean data = libraryService.removeLibrary(id);
        res = new CustomResponse<Boolean>(data?200:404, data, data?"서재 삭제 성공":"서재 삭제 실패");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

    @PostMapping("/{library-id}")
    @Operation(summary = "서재에 웹툰 추가 API", description = "서재 id와 웹툰 id로 특정 서재에 특정 웹툰 추가", responses = {
            @ApiResponse(responseCode = "200", description = "추가 성공"),
            @ApiResponse(responseCode = "404", description = "서재 또는 웹툰을 찾을 수 없음, 추가 실패"),
            @ApiResponse(responseCode = "400", description = "잘못되 요청 형식")
    }, parameters = {
            @Parameter(name = "library-id", description = "웹툰이 추가될 서재의 id"),
            @Parameter(name = "libraryAddWebtoonRequest", description = "추가할 웹툰의 id를 포함한 요청 형식", content = {
                    @Content(schema = @Schema(implementation = LibraryAddWebtoonRequest.class))
            })
    })
    public ResponseEntity<CustomResponse> libraryAddWebtoon(@PathVariable("library-id") long id, @RequestBody @Valid LibraryAddWebtoonRequest libraryAddWebtoonRequest, BindingResult bindingResult){
        CustomResponse res;
        if(bindingResult.hasErrors()){
            res = new CustomResponse(400, null, "잘못된 요청 형식 입니다.");
        }else{
            if(webtoonService.isExist(libraryAddWebtoonRequest.getWebtoonId())) {
                long userId = 1; // 로그인 구현 후 변경 요망!!!!!!!!!
                libraryAddWebtoonRequest.setId(id);
                boolean data = libraryService.addWebtoon(libraryAddWebtoonRequest);
                res = new CustomResponse<Boolean>(data ? 200 : 404, data, data ? "웹툰 추가 성공" : "웹툰 추가 실패");
            }else{
                res = new CustomResponse(404, null, "존재하지 않는 웹툰");
            }
        }
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

    @GetMapping("/subscription")
    @Operation(summary = "구독중인 서재 목록 Read API", description = "특정 사용자가 구독하고 있는 서재의 목록을 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    }, parameters = {
            @Parameter(name = "pno", description = "조회할 서재 목록의 페이지 번호 0부터 시작"),
            @Parameter(name = "count", description = "조회할 서재 목록의 페이지의 크기")
    })
    public ResponseEntity<CustomResponse> subscriptionList(@RequestParam("pno") int pageNumber, @RequestParam("count") int pageSize){
        long userId = 1; // 로그인 구현 후 변경 요망!!!!!!
        List data = libraryService.findSubscription(userId, pageNumber, pageSize);
        CustomResponse res = new CustomResponse<List>(200, data, "구독 중인 서재 조회 성공");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

    @PostMapping("/subscription")
    @Operation(summary = "서재 구독 API", description = "특정 사용자가 특정 서재를 구독합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "구독 성공"),
            @ApiResponse(responseCode = "404", description = "구독 실패"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식")
    }, parameters = {
            @Parameter(name = "subscriptionRequest", description = "구독할 서재 id를 포함한 요청 형식", content = {
                    @Content(schema = @Schema(implementation = SubscriptionRequest.class))
            })
    })
    public ResponseEntity<CustomResponse> SubscriptionAdd(@RequestBody @Valid SubcriptionRequest subcriptionRequest, BindingResult bindingResult){
        CustomResponse res;
        if(bindingResult.hasErrors()){
            res = new CustomResponse(400, null, "잘못된 요청 형식 입니다.");
        }else{
            long userId = 1; // 로그인 구현 후 변경 요망!!!!!!
            subcriptionRequest.setUserId(userId);
            boolean data = libraryService.addSubscription(subcriptionRequest);
            res  = new CustomResponse<Boolean>(data?200:404, data, data?"서재 구독 성공":"서재 구독 실패");
        }
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

    @DeleteMapping("/subscription")
    @Operation(summary = "구독 취소 API", description = "특정 사용자가 특정 서재를 구독 취소합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "구독 정보 없음, 삭제 실패"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식")
    }, parameters = {
            @Parameter(name = "subscriptionCancleRequest", description = "구독한 서재 id를 포함한 구독 취소 요청 형식", content = {
                    @Content(schema = @Schema(implementation = SubscriptionCancelRequest.class))
            })
    })
    public ResponseEntity<CustomResponse> SubscriptionRemove(@RequestBody @Valid SubscriptionCancelRequest subscriptionCancelRequest, BindingResult bindingResult){
        CustomResponse res;
        if(bindingResult.hasErrors()) {
            res = new CustomResponse(400, null, "잘못된 요청 형식 입니다.");
        }else{
            long userId = 1; // 로그인 구현 후 변경 요망!!!!!!!
            subscriptionCancelRequest.setUserId(userId);
            boolean data = libraryService.removeSubscription(subscriptionCancelRequest);
            res = new CustomResponse(data?200:404, data, data?"구독 취소 성공":"구독 취소 실패");
        }
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

    @PatchMapping("/{library-id}")
    @Operation(summary = "서재 Update API", description = "서재 이름을 수정 합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "404", description = "서재를 찾을 수 없습니다, 수정 실패"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식")
    }, parameters = {
            @Parameter(name = "library-id", description = "수정할 서재의 id"),
            @Parameter(name = "libraryUpdateRequest", description = "수정된 서재의 이름을 포함한 서재 수정 요청 형식", content = {
                    @Content(schema = @Schema(implementation = LibraryUpdateRequest.class))
            })
    })
    public ResponseEntity<CustomResponse> libraryModify(@PathVariable("library-id") long id, @RequestBody @Valid LibraryUpdateRequest libraryUpdateRequest, BindingResult bindingResult){
        CustomResponse res;
        if(bindingResult.hasErrors()){
            res = new CustomResponse(400, null, "잘못된 요청 형식 입니다.");
        }else{
            libraryUpdateRequest.setId(id);
            boolean data = libraryService.modifyLibrary(libraryUpdateRequest);
            res = new CustomResponse<Boolean>(data?200:404, data, data?"라이브러리 업데이트 성공":"라이브러리 업데이트 실패");
        }
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

}
