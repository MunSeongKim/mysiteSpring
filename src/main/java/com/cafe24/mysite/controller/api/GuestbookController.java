package com.cafe24.mysite.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe24.mysite.dto.JSONResult;
import com.cafe24.mysite.service.GuestbookService;
import com.cafe24.mysite.vo.GuestbookVO;

@Controller( "GuestbookAPIController" )
@RequestMapping( "/api/guestbook" )
public class GuestbookController {
    @Autowired
    private GuestbookService gbService;

    @ResponseBody
    @RequestMapping( "/list" )
    public JSONResult list( @RequestParam( value = "idx", required = true, defaultValue = "0L" ) Long no ) {
	List<GuestbookVO> list = gbService.getList( no );
	return JSONResult.success( list );
    }

    @ResponseBody
    @RequestMapping( "/insert" )
    public JSONResult insert(@RequestBody GuestbookVO vo) {
	GuestbookVO result = gbService.writeAPI(vo);
	if( result == null ) {
	    return JSONResult.fail("Failed to write");
	}
	return JSONResult.success(result);
    }
    
    @ResponseBody
    @RequestMapping( "/delete" )
    public JSONResult delete(@ModelAttribute GuestbookVO vo) {
	boolean result = gbService.delete(vo);
	return JSONResult.success( result ? vo.getNo() : -1 );
    }
}
