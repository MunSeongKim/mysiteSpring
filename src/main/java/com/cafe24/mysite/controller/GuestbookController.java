package com.cafe24.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cafe24.mysite.service.GuestbookService;
import com.cafe24.mysite.vo.GuestbookVO;

@Controller
@RequestMapping( "/guestbook" )
public class GuestbookController {
    @Autowired
    private GuestbookService gbService;
    
    private static final Long start = 0L;
    private static final Long count = 5L;
    
    @RequestMapping( "/list" )
    public String list( Model model ) {
	List<GuestbookVO> list = gbService.getList(start);
	Long startNumber = gbService.getCount();
	model.addAttribute( "list", list );
	model.addAttribute( "startNumber", startNumber);
	model.addAttribute( "postCount", count );
	return "guestbook/list";
    }

    @RequestMapping( value = "/write", method = RequestMethod.POST )
    public String write( @ModelAttribute GuestbookVO vo ) {
	gbService.write( vo );
	return "redirect:/guestbook/list";
    }

    @RequestMapping( value = "/delete/{no}", method = RequestMethod.GET )
    public String delete( @PathVariable Long no, Model model ) {
	model.addAttribute( "no", no );
	return "guestbook/delete";
    }

    @RequestMapping( value = "/delete", method = RequestMethod.POST )
    public String delete( @ModelAttribute GuestbookVO vo, Model model ) {
	gbService.delete( vo );
	return "redirect:/guestbook/list";
    }
    
    @RequestMapping( value = "/ajax", method = RequestMethod.GET )
    public String ajax() {
	return "guestbook/index-ajax";
    }

}
