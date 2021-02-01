package com.web.blog.controller;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.web.blog.model.ImgDto;
import com.web.blog.model.PostDto;
import com.web.blog.model.PostParameterDto;
import com.web.blog.model.service.PostService;
import com.web.blog.util.FileUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api("PostController V1")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/temp")
public class PostTempController {

	private static final Logger logger = LoggerFactory.getLogger(PostTempController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private final String fileUrl = Paths.get("C:", "ssafy", "uploaded").toString();
	private final String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
	private final String uploadPath = Paths.get(fileUrl, today).toString();

	@Autowired
	private PostService postService;

	@Autowired
	private FileUtil fileService;

	@ApiOperation(value = "글작성", notes = "새로운 게시글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> write(MultipartHttpServletRequest mtfRequest) throws Exception {
		logger.info("write - 호출");

		String result = SUCCESS;
		HttpStatus status = HttpStatus.OK;

		List<MultipartFile> files = mtfRequest.getFiles("files");
        String content = mtfRequest.getParameter("content");
        String email = mtfRequest.getParameter("email");
        String title = mtfRequest.getParameter("title");
        int postNo = Integer.parseInt(mtfRequest.getParameter("postNo"));
        
		PostDto postDto = new PostDto();
		postDto.setPostNo(postNo);
		postDto.setContent(content);
		postDto.setEmail(email);
		postDto.setTitle(title);
		postDto.setTemp(1);

		System.out.println(postDto);
		// 게시글 작성 성공 시
		if (postService.writeTemp(postDto)) {
			// 오늘 날짜 디렉토리 생성 여부 확인
			postNo = postService.getLastPostNo(email);
			File dir = new File(Paths.get(fileUrl, String.valueOf(postNo)).toString());
			if (!dir.exists()) {
				dir.mkdirs();
			}
			
			if (files != null) {
				for (MultipartFile file : files) {
					// file info logs
					String oriPicName = file.getOriginalFilename();
					String oriPicNameExtension = FilenameUtils.getExtension(oriPicName).toLowerCase();
					String modPicName = RandomStringUtils.randomAlphanumeric(32) + "." + oriPicNameExtension;
					long picSize = file.getSize();

					// 업로드 경로에 파일 생성
					File target = new File(fileUrl + "\\" + String.valueOf(postNo) + "\\" + modPicName);
//					File target = new File(uploadPath, modPicName);
					file.transferTo(target);

					// 파일 정보 저장
					ImgDto img = new ImgDto();
					img.setPostNo(postNo);
					img.setOriPicName(oriPicName);
					img.setModPicName(modPicName);
					img.setPicSize(picSize);

					// postNo로 파일 저장
					postService.uploadFile(img);
				}
			} else { // 파일 없을 때
				result = FAIL;
				status = HttpStatus.NO_CONTENT;
			}
		} else { // 작성 실패시
			result = FAIL;
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<String>(result, status);
	}

	@ApiOperation(value = "글목록", notes = "모든 게시글의 정보를 반환한다.", response = List.class)
	@GetMapping("/list")
	public ResponseEntity<List<PostDto>> getList(
			@ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) PostParameterDto postParameterDto) throws Exception {
		logger.info("getList - 호출");
		return new ResponseEntity<List<PostDto>>(postService.getTempList(postParameterDto), HttpStatus.OK);
	}
	
}
