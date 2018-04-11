package com.shudailaoshi.web.controller.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shudailaoshi.dao.redis.RedisRepository;
import com.shudailaoshi.entity.sys.User;
import com.shudailaoshi.pojo.vos.common.ResultVO;
import com.shudailaoshi.service.sys.UserService;
import com.shudailaoshi.utils.CodeUtil;
import com.shudailaoshi.utils.DateUtil;
import com.shudailaoshi.utils.LuceneUtil;
import com.shudailaoshi.web.controller.base.BaseController;
import com.shudailaoshi.web.utils.ResultUtil;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @author Liaoyifan
 *
 */
@RequestMapping("test")
@Controller
public class TestController extends BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	private RedisRepository redisRepository;
	
	@RequestMapping("test")
	@ResponseBody
	public Object test(){
		long time = DateUtil.getTime();
		for (int i = 0; i < 100; i++) {
			this.redisRepository.get("a");
		}
		return DateUtil.getTime() - time;
	}

	@RequestMapping("upload")
	@ResponseBody
	public ResultVO upload(HttpServletRequest request, @RequestParam(value = "file") MultipartFile file)
			throws IOException {
		// 上传
		String fileName = CodeUtil.getUniqueCode() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
		String dirPath = "e://file//";
		File dirFile = new File(dirPath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		FileUtils.writeByteArrayToFile(new File(dirPath + fileName), file.getBytes());
		return ResultUtil.success();
	}

	@RequestMapping("getByExample")
	@ResponseBody
	public User getByExample() {
		Example ex = new Example(User.class);
		ex.createCriteria().andNotEqualTo("id", 1);
		return this.userService.getByExample(ex);
	}

	@RequestMapping("/index")
	public String index() { 
		return "front/index";
	}

	@RequestMapping("/create")
	public void create() {
		LuceneUtil.create(userService.listAll());
	} 

	@RequestMapping("search/{keyword}")
	@ResponseBody
	public List<Object> search(@PathVariable String keyword) {
		return LuceneUtil.search(keyword, new ArrayList<String>() {
			private static final long serialVersionUID = 1L;
			{
				add("userName");
			}
		}, User.class);
	}

}