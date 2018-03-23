package com.cafe24.mysite.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe24.mysite.dto.JSONResult;
import com.cafe24.mysite.service.GuestbookService;
import com.cafe24.mysite.vo.GuestbookVO;

@Controller("GuestbookAPIController")
@RequestMapping("/api/guestbook")
public class GuestbookController {
    @Autowired
    private GuestbookService gbService;
    
    
    @ResponseBody
    @RequestMapping("/list")
    public JSONResult list(@RequestParam(value="idx") Long index) {
	List<GuestbookVO> list = gbService.getList( index );
	return JSONResult.success( list );
    }
}
