package com.web.blog.model.service;

import java.io.File;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.web.blog.model.ImgDto;
import com.web.blog.model.PostDto;
import com.web.blog.model.PostLikeDto;
import com.web.blog.model.PostParameterDto;
import com.web.blog.model.mapper.PostMapper;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostMapper postMapper;

	private static final String fileUrl = Paths.get("C:", "ssafy", "uploaded").toString();
//	private final String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
//	private final String uploadPath = Paths.get(fileUrl, today).toString();

	@Override
	public PostDto getDetail(int postNo) throws Exception {
		return postMapper.getOne(postNo);
	}
	
	@Override
	public List<PostDto> getList(PostParameterDto postParameterDto) throws Exception {
		int start = postParameterDto.getPg() == 0 ? 0 : (postParameterDto.getPg() - 1) * postParameterDto.getSpp();
		postParameterDto.setStart(start);
		return postMapper.getList(postParameterDto);
	}
	@Override
	public List<PostDto> getTempList(PostParameterDto postParameterDto) throws Exception {
		int start = postParameterDto.getPg() == 0 ? 0 : (postParameterDto.getPg() - 1) * postParameterDto.getSpp();
		postParameterDto.setStart(start);
		return postMapper.getTempList(postParameterDto);
	}

	
	@Override
	public boolean write(PostDto postDto) throws Exception {
		if(postDto.getTitle() == null || postDto.getContent() == null
//				|| "".equals(postDto.getTitle()) || "".equals(postDto.getContent())	// 제목, 내용 빈 문자열이면 취소
				) {
			throw new Exception("No title or No Content!");
		}
		// 임시저장했던 글이라면
		if(postDto.getPostNo() != -1) {
			System.out.println("?2");
			return postMapper.writeTemptoDB(postDto) == 1;
		}
		
//		if(postDto.getTemp() == 1) {
//			System.out.println("?3");
//			return postMapper.writeTemp(postDto) == 1;
//		}
		if(postDto.getEmail() == null || "".equals(postDto.getEmail())) {
			throw new Exception("You are not Logged In!!");
		}
		return postMapper.write(postDto) == 1;
	}
	
	@Override
	public boolean writeTemp(PostDto postDto) throws Exception {
		if(postDto.getTitle() == null || postDto.getContent() == null
//				|| "".equals(postDto.getTitle()) || "".equals(postDto.getContent())	// 제목, 내용 빈 문자열이면 취소
				) {
			throw new Exception("No title or No Content!");
		}
		if(postDto.getPostNo() != -1) {
			return postMapper.modify(postDto) == 1;
		}
		if(postDto.getEmail() == null || "".equals(postDto.getEmail())) {
			throw new Exception("You are not Logged In!!");
		}
		return postMapper.writeTemp(postDto) == 1;
	}

	@Override
	@Transactional
	public boolean modify(PostDto postDto) throws Exception {
		return postMapper.modify(postDto) == 1;
	}

	@Override
	@Transactional
	public boolean delete(int postNo) throws Exception {
		return postMapper.delete(postNo) == 1;
	}

	@Override
	public int likeCount(int postNo) throws Exception {
		return postMapper.likeCount(postNo);
	}

	@Override
	public int likeCheck(Map<String, Object> map) throws Exception {
		return postMapper.likeCheck(map);
	}

	@Override
	public PostLikeDto likeInfo(Map<String, Object> map) throws Exception {
		return postMapper.likeInfo(map);
	}

	@Override
	public int insertLike(Map<String, Object> map) throws Exception {
		if(map.get("email") == null || "".equals(map.get("email").toString()) ) {
			throw new Exception();
		}
		return postMapper.insertLike(map);
	}

	@Override
	@Transactional
	public int like(Map<String, Object> map) throws Exception {
		return postMapper.like(map);
	}

	@Override
	@Transactional
	public int unlike(Map<String, Object> map) throws Exception {
		return postMapper.unlike(map);
	}

	@Override
	@Transactional
	public int likeCntUp(int postNo) throws Exception {
		// TODO Auto-generated method stub
		return postMapper.likeCntUp(postNo);
	}

	@Override
	@Transactional
	public int likeCntDown(int postNo) throws Exception {
		// TODO Auto-generated method stub
		return postMapper.likeCntDown(postNo);
	}

	
	@Override
	public int getLastPostNo(String email) throws Exception {
		return postMapper.getLastPostNo(email);
	}
	
	@Override
	public boolean uploadFile(ImgDto img) throws Exception {
		return postMapper.uploadFile(img) == 1;
	}
	
	@Override
	public List<ImgDto> getImages(int postNo) throws Exception {
		return postMapper.getFiles(postNo);
	}
	
	@Override
	public ImgDto getImageInfo(String picNo) throws Exception {
		return postMapper.getFileInfo(picNo);
	}

	@Override
	public boolean saveImages(int postNo, List<MultipartFile> files) throws Exception {
		if(files.size() < 1) {
			return false;
		}
		
		File dir = new File(Paths.get(fileUrl, String.valueOf(postNo)).toString());
		// postNo 디렉토리
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		// 
		for (MultipartFile file : files) {
			// file info logs
			System.out.println(file.getOriginalFilename());
			
			String oriPicName = file.getOriginalFilename();
			String oriPicNameExtension = FilenameUtils.getExtension(oriPicName).toLowerCase();
			String modPicName = RandomStringUtils.randomAlphanumeric(35-oriPicNameExtension.length()) + "." + oriPicNameExtension;
			long picSize = file.getSize();
			// 업로드 경로에 파일 생성
			File target = new File(Paths.get(fileUrl, String.valueOf(postNo), modPicName).toString());
			
			file.transferTo(target);

			// 파일 정보 저장
			ImgDto img = new ImgDto();
			img.setPostNo(postNo);
			img.setOriPicName(oriPicName);
			img.setModPicName(modPicName);
			img.setPicSize(picSize);

			// postNo로 파일 저장
			if(postMapper.uploadFile(img) != 1) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean saveImage(int postNo, MultipartFile file) throws Exception {
		System.out.println(file.getOriginalFilename());
		
		String oriPicName = file.getOriginalFilename();
		String oriPicNameExtension = FilenameUtils.getExtension(oriPicName).toLowerCase();
		String modPicName = RandomStringUtils.randomAlphanumeric(35-oriPicNameExtension.length()) + "." + oriPicNameExtension;
		long picSize = file.getSize();
		// 업로드 경로에 파일 생성
		File target = new File(Paths.get(fileUrl, String.valueOf(postNo), modPicName).toString());
		
		file.transferTo(target);

		// 파일 정보 저장
		ImgDto img = new ImgDto();
		img.setPostNo(postNo);
		img.setOriPicName(oriPicName);
		img.setModPicName(modPicName);
		img.setPicSize(picSize);

		// postNo로 파일 저장
		if(postMapper.uploadFile(img) != 1) {
			return false;
		}
		
		return true;
	}

	@Override
	@Transactional
	public boolean deleteImage(String picNo) throws Exception {
		return postMapper.deleteImg(picNo) == 1;
	}

}