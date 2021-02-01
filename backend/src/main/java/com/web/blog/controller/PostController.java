package com.web.blog.controller;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.web.blog.model.ImgDto;
import com.web.blog.model.PostDto;
import com.web.blog.model.PostLikeDto;
import com.web.blog.model.PostParameterDto;
import com.web.blog.model.service.PostService;
import com.web.blog.util.FileUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api("PostController V1")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/post")
public class PostController {

	private static final Logger logger = LoggerFactory.getLogger(PostController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private final String fileUrl = Paths.get("C:", "ssafy", "uploaded").toString();
//	private final String fileUrl = Paths.get("C:", "ssafy", "uploaded").toString(); 

	@Autowired
	private PostService postService;

	@Autowired
	private FileUtil fileService;

	@ApiOperation(value = "글작성", notes = "새로운 게시글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> write(@ApiParam(value = "수정할 글 정보.", required = true)  PostDto postDto)  {
		logger.info("write - 호출");

		String result = SUCCESS;
		HttpStatus status = HttpStatus.OK;
		
//		List<MultipartFile> files = mtfRequest.getFiles("files");
//        String content = mtfRequest.getParameter("content");
//        String email = mtfRequest.getParameter("email");
//        String title = mtfRequest.getParameter("title");
		
        System.out.println(postDto);
		
		// 게시글 작성 성공 시
		try {
			if (postService.write(postDto)) {
				// 오늘 날짜 디렉토리 생성 여부 확인
				logger.info("게시글 작성 성공");
				
				int postNo = postService.getLastPostNo(postDto.getEmail());
				
				// 임시저장했던 글이었다면
				if(postDto.getPostNo() != -1) {
					// postNo 바꿔주고
					postNo = postDto.getPostNo();
					
					// file 변경 있다면
					if(postDto.getUnmodified() != null) {
						// 기존 파일 삭제
						if(!postService.deleteImages(postDto.getUnmodified())) {
							logger.error("파일 DB 삭제 실패!");
						}
					}
				}
				
				List<MultipartFile> files = postDto.getFiles();
				System.out.println(files.size());
				
				if (files != null && files.size() > 0) {
					if(!postService.saveImages(postNo, files)) {
						logger.error("파일 저장 실패!");
						result = FAIL;
						status = HttpStatus.INTERNAL_SERVER_ERROR;
					}
				}
			} else { // 작성 실패시
				logger.error("글 작성 실패!");
				result = FAIL;
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ResponseEntity<String>(result, status);
	}

	@ApiOperation(value = "글목록", notes = "모든 게시글의 정보를 반환한다.", response = List.class)
	@GetMapping("/list")
	public ResponseEntity<List<PostDto>> getList(
			@ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) PostParameterDto postParameterDto) throws Exception {
		logger.info("getList - 호출");
		return new ResponseEntity<List<PostDto>>(postService.getList(postParameterDto), HttpStatus.OK);
	}

	@ApiOperation(value = "글보기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.", response = PostDto.class)
	@GetMapping
	public ResponseEntity<Map<String, Object>> getPostDetail(
			@RequestParam @ApiParam(value = "얻어올 글 번호.", required = true, example = "24") int postNo,
			@RequestParam @ApiParam(value = "로그인 이메일 아이디.", required = true) String email) throws Exception {
		logger.info("getOne - 호출");

		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> likeCheckMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;

		try {
			// 글 정보
			PostDto postDto = postService.getDetail(postNo);
			resultMap.put("postInfo", postDto);

			// like 했는지 확인
			likeCheckMap.put("email", email);
			likeCheckMap.put("postNo", postNo);

			PostLikeDto likeDto = postService.likeInfo(likeCheckMap);

			// 해당 게시글 like 누른적 한 번도 없다면
			if (likeDto == null) {
				resultMap.put("likeCheck", "N");
			}
			// 좋아요를 누른 적이 있으면
			else {
				resultMap.put("likeCheck", likeDto.getLikeCheck());
			}

			// post imgs
			List<ImgDto> postImgs = postService.getImages(postNo);
			resultMap.put("fileList", postImgs);

			status = HttpStatus.OK;
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "게시글에 등록된 이미지 파일들의 이름을 가져온다.")
	@GetMapping("/imgs")
	public ResponseEntity<List<ImgDto>> getImgs(@RequestParam int postNo, HttpServletRequest request) {
		HttpStatus status = HttpStatus.OK;
		ResponseEntity<List<ImgDto>> entity = null;
		
		try {
			entity = new ResponseEntity<List<ImgDto>>(postService.getImages(postNo), status);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<List<ImgDto>>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@ApiOperation(value = "게시글에 등록된 이미지 중 하나를 다운로드한다.")
	@GetMapping("/imgs/download")
	public ResponseEntity<Resource> downloadImage(@RequestParam int postNo, @RequestParam String fileName, HttpServletRequest request) {
		logger.info("file name: " + fileName);
		String path = Paths.get(fileUrl, String.valueOf(postNo), fileName).toString();
		Resource resource = fileService.loadFileAsResource(path);
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@ApiOperation(value = "글수정 페이지 이동", notes = "글 수정 페이지로 이동하면 해당 게시글의 정보를 반환한다.", response = String.class)
	@GetMapping(value = "/moveUpdate")
	public ResponseEntity<Map<String, Object>> openBoardWrite(@RequestParam int postNo) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;

		try {
			PostDto post = postService.getDetail(postNo);

			if (post == null) {
				resultMap.put("message", "없는 게시글이거나 이미 삭제된 글입니다.");
				status = HttpStatus.NO_CONTENT;
			} else {
				resultMap.put("postInfo", post);
				resultMap.put("fileList", postService.getImages(postNo));
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "글수정", notes = "새로운 게시글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping
//	public ResponseEntity<String> modify(@ApiParam(value = "수정할 글 정보.", required = false) MultipartHttpServletRequest mtfRequest, @RequestParam(required = false) List<String> unmodified) throws Exception {
	public ResponseEntity<String> modify(@ApiParam(value = "수정할 글 정보.", required = true) PostDto post) throws Exception {
				logger.info("modify - 호출");

		String result = SUCCESS;
		HttpStatus status = HttpStatus.OK;
		
//        String content = mtfRequest.getParameter("content");
//        String title = mtfRequest.getParameter("title");
//        int postNo = Integer.parseInt(mtfRequest.getParameter("postNo"));
		
		System.out.println(post);
		
//		PostDto postDto = new PostDto();
//		postDto.setContent(content);
//		postDto.setPostNo(postNo);
//		postDto.setTitle(title);
		
		if (postService.modify(post)) {
			logger.info("게시글 수정 완료");
			
			// 바뀐 파일이 있다면
			if(post.getFiles() != null && post.getFiles().size() > 0) {
				
				if(!postService.deleteImages(post.getUnmodified())) {
					logger.error("파일 DB 삭제 실패!");
				}
				
				List<MultipartFile> files = post.getFiles();
				System.out.println(files);
				
				if (files != null) {
					if(!postService.saveImages(post.getPostNo(), files)) {
						logger.error("파일 저장 실패!");
						result = FAIL;
						status = HttpStatus.INTERNAL_SERVER_ERROR;
					}
				}
			}
		}
		
		return new ResponseEntity<String>(result, status);
	}

	@ApiOperation(value = "글삭제", notes = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping
	public ResponseEntity<String> delete(
			@RequestParam @ApiParam(value = "삭제할 글 번호.", required = true, example = "24") int postNo) throws Exception {
		logger.info("delete - 호출");
		if (postService.delete(postNo)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "like", notes = "글번호에 해당하는 게시글의 좋아요를 토글한다.", response = HashMap.class)
	@PutMapping("/like")
	public ResponseEntity<Map<String, Object>> like(@RequestParam int postNo, @RequestParam String email) {

		logger.info("like - 호출");
		Map<String, Object> map = new HashMap<String, Object>();

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;

		try {
			map.put("postNo", postNo);
			map.put("email", email);

			PostDto postDto = postService.getDetail(postNo);
			System.out.println(postDto);
			PostLikeDto likeDto = postService.likeInfo(map);
			System.out.println(likeDto);

			// 해당 게시글에 한번도 좋아요 한 적 없다면
			if (likeDto == null) {
				postService.insertLike(map);
				likeDto = postService.likeInfo(map);
			}

			String like_check = "N";
			like_check = likeDto.getLikeCheck(); // 좋아요 체크 값

			if (like_check.equals("N")) {
//				msgs.add("좋아요!");
				postService.like(map);
				like_check = "Y";
				postService.likeCntUp(postNo); // 좋아요 갯수 증가
			} else {
//				msgs.add("좋아요 취소");
				postService.unlike(map);
				like_check = "N";
				postService.likeCntDown(postNo); // 좋아요 갯수 감소
			}

			resultMap.put("postNo", postNo);
			resultMap.put("likeCheck", like_check);
			resultMap.put("likeCnt", postService.likeCount(postNo));
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
