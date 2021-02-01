package com.web.blog.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.model.CommentDto;
import com.web.blog.model.CommentLikeDto;
import com.web.blog.model.CommentParameterDto;
import com.web.blog.model.service.CommentService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/comment")
@CrossOrigin(origins = { "*" })

public class CommentController {
	@Autowired
	CommentService commentService;
	
//	@GetMapping("/{postNo}")
//	public ResponseEntity<List<CommentDto>> commentList (@PathVariable int postNo, HttpServletRequest req) throws Exception {
//		
//		return new ResponseEntity<List<CommentDto>>(commentService.commentList(postNo), HttpStatus.OK);
//	}

	@GetMapping("/{postNo}")
	public ResponseEntity<List<Map<String, Object>>> commentList (@PathVariable int postNo, @RequestParam String email, HttpServletRequest req) throws Exception {
		
		HttpStatus status = HttpStatus.OK;
		
		List<Map<String, Object>> commentDtoList = new ArrayList<Map<String,Object>>();
		
		for(CommentDto comment : commentService.commentList(postNo)) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			Map<String, Object> likecheck = new HashMap<String, Object>();
			resultMap.put("Comment", comment);
			likecheck.put("email", email);
			likecheck.put("commentNo", comment.getCommentNo());
			
			CommentLikeDto likeDto = commentService.likeInfo(likecheck);
			
			// 해당 게시글 like 누른적 한 번도 없다면
			if (likeDto == null) {
				resultMap.put("likeCheck", "N");
			}
			// 좋아요를 누른 적이 있으면
			else {
				resultMap.put("likeCheck", likeDto.getLikeCheck());
			}
			System.out.println(resultMap);
			commentDtoList.add(resultMap);
		}
		return new ResponseEntity<List<Map<String, Object>>>(commentDtoList, status);
	}
	
//	@PostMapping("/{postNo}")
//	public ResponseEntity<List<Map<String, Object>>> commentList (@PathVariable int postNo, @RequestParam String email, HttpServletRequest req) throws Exception {
//        
//        HttpStatus status = HttpStatus.OK;
//        
//        List<Map<String, Object>> commentDtoList = new ArrayList<Map<String,Object>>();
//        
//        for(CommentDto comment : commentService.commentList(postNo)) {
//        	Map<String, Object> resultMap = new HashMap<String, Object>();
//            Map<String, Object> likecheck = new HashMap<String, Object>();
//            resultMap.put("Comment", comment);
//            likecheck.put("email", email);
//            likecheck.put("commentNo", comment.getCommentNo());
//
//            CommentLikeDto likeDto = commentService.likeInfo(likecheck);
//
//            // 해당 게시글 like 누른적 한 번도 없다면
//            if (likeDto == null) {
//                resultMap.put("likeCheck", "N");
//            }
//            // 좋아요를 누른 적이 있으면
//            else {
//                resultMap.put("likeCheck", likeDto.getLikeCheck());
//            }
//            System.out.println(resultMap);
//            commentDtoList.add(resultMap);
//        }
//        return new ResponseEntity<List<Map<String, Object>>>(commentDtoList, status);
//    }
	
//	// 잘되는거
//	@PostMapping("/{postNo}")
//	public ResponseEntity<List<Map<String, Object>>> infiniteScrollDownComment (@PathVariable int postNo, @RequestParam String email, @RequestBody CommentParameterDto commentParameterDto, HttpServletRequest req) throws Exception {
//		HttpStatus status = HttpStatus.OK;
//		System.out.println(commentParameterDto);
//		commentParameterDto.setSpp(commentParameterDto.getStart()+10);
//		List<Map<String, Object>> commentDtoList = new ArrayList<Map<String,Object>>();
//		for(CommentDto comment : commentService.infiniteScrollDown(postNo, commentParameterDto)) {
//			Map<String, Object> resultMap = new HashMap<String, Object>();
//			Map<String, Object> likecheck = new HashMap<String, Object>();
//			resultMap.put("Comment", comment);
//			likecheck.put("email", email);
//			likecheck.put("commentNo", comment.getCommentNo());
//			
//			CommentLikeDto likeDto = commentService.likeInfo(likecheck);
//			
//			// 해당 게시글 like 누른적 한 번도 없다면
//			if (likeDto == null) {
//				resultMap.put("likeCheck", "N");
//			}
//			// 좋아요를 누른 적이 있으면
//			else {
//				resultMap.put("likeCheck", likeDto.getLikeCheck());
//			}
//			System.out.println(resultMap);
//			commentDtoList.add(resultMap);
//			
//		}
//		return new ResponseEntity<List<Map<String, Object>>>(commentDtoList, status);
//	}

	@PostMapping("/{postNo}")
	public ResponseEntity<List<Map<String, Object>>> infiniteScrollDownComment (@PathVariable int postNo, @RequestParam String email, @RequestParam int pg, HttpServletRequest req) throws Exception {
		HttpStatus status = HttpStatus.OK;
		
		pg *= 10;
		System.out.println(pg);
        List<Map<String, Object>> commentDtoList = new ArrayList<Map<String,Object>>();
        for(CommentDto comment : commentService.infiniteScrollDown(postNo, pg)) {
        	Map<String, Object> resultMap = new HashMap<String, Object>();
            Map<String, Object> likecheck = new HashMap<String, Object>();
            resultMap.put("Comment", comment);
            likecheck.put("email", email);
            likecheck.put("commentNo", comment.getCommentNo());

            CommentLikeDto likeDto = commentService.likeInfo(likecheck);

            // 해당 게시글 like 누른적 한 번도 없다면
            if (likeDto == null) {
                resultMap.put("likeCheck", "N");
            }
            // 좋아요를 누른 적이 있으면
            else {
                resultMap.put("likeCheck", likeDto.getLikeCheck());
            }
            System.out.println(resultMap);
            commentDtoList.add(resultMap);
            
        }
        return new ResponseEntity<List<Map<String, Object>>>(commentDtoList, status);
	}
	
	@PostMapping
	public ResponseEntity<Boolean> commentWrite(@RequestBody Map<String, String> map){
		System.out.println(map);
		HttpStatus status = HttpStatus.ACCEPTED;
		CommentDto dto = new CommentDto();
		dto.setContent(map.get("content"));
		dto.setEmail(map.get("email"));
		dto.setPostNo(Integer.parseInt(map.get("postNo")));
		System.out.println(dto);
		boolean flag = true;
		
		try {
			commentService.commentWrite(dto);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<Boolean>(flag, status);
	}
	
	@PutMapping
	public ResponseEntity<String> modify(@RequestBody CommentDto commentDto) throws Exception {

		if (commentService.commentUpdate(commentDto)) {
			return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		}
		return new ResponseEntity<String>("FAIL", HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<Boolean> delete(@RequestBody Map<String, String> map) {
		HttpStatus status = HttpStatus.ACCEPTED;
		System.out.println(map);
		boolean flag = false;
		
		try {
			flag = commentService.commentDelete(Integer.parseInt(map.get("commentNo")));
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Boolean>(flag, status);
	}
	
	@ApiOperation(value = "like", notes = "댓글번호에 해당하는 댓글의 좋아요를 토글한다. 그리고 DB 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/like")
	public ResponseEntity<Map<String, Object>> like(@RequestParam int commentNo, @RequestParam String email) {

		Map<String, Object> map = new HashMap<String, Object>();

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;

		try {
			map.put("commentNo", commentNo);
			map.put("email", email);

			CommentLikeDto likeDto = commentService.likeInfo(map);
			System.out.println(likeDto);

			System.out.println(1);

//			int like_cnt = postDto.getLikeCnt(); // 게시판의 좋아요 카운트
			// 해당 게시글에 한번도 좋아요 한 적 없다면
			if (likeDto == null) {
				commentService.insertLike(map);
				System.out.println(2);
				likeDto = commentService.likeInfo(map);
			}

			String like_check = "N";
			like_check = likeDto.getLikeCheck(); // 좋아요 체크 값

			if (like_check.equals("N")) {
//				msgs.add("좋아요!");
				commentService.like(map);
				like_check = "Y";
//				like_cnt++;
				commentService.likeCntUp(commentNo); // 좋아요 갯수 증가
			} else {
//				msgs.add("좋아요 취소");
				commentService.unlike(map);
				like_check = "N";
//				like_cnt--;
				commentService.likeCntDown(commentNo); // 좋아요 갯수 감소
			}

			resultMap.put("commentNo", commentNo);
			resultMap.put("likeCheck", like_check);
//	    	resultMap.put("likeCnt", like_cnt);
			resultMap.put("likeCnt", commentService.likeCount(commentNo));
			status = HttpStatus.OK;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}
