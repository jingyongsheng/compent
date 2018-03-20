package com.zcbl.modules.component.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zcbl.common.persistence.Page;
import com.zcbl.common.utils.StringUtils;
import com.zcbl.common.web.BaseController;
import com.zcbl.modules.component.entity.CompentTask;
import com.zcbl.modules.component.service.ComponetService;

@Controller
@RequestMapping(value = "${adminPath}/component")
public class ComponentController extends BaseController {
	
	@Autowired
	private ComponetService componetService;
	
	
	@ModelAttribute
	public CompentTask get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return componetService.get(id);
		}else{
			return new CompentTask();
		}
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(CompentTask compentTask, HttpServletRequest request, HttpServletResponse response, Model model) {
		
        Page<CompentTask> page = componetService.findPage(new Page<CompentTask>(request, response), compentTask); 
        model.addAttribute("page", page);
		return "modules/component/componentList";
	}

	@RequestMapping(value = "save")
	public String save(CompentTask compentTask){
		componetService.save(compentTask);
		return "redirect:" + adminPath + "/component?repage&type="+compentTask.getType();
	}

	@RequestMapping(value = "form")
	public String form(CompentTask compentTask, Model model) {
		
		model.addAttribute("compentTask", compentTask);
		return "modules/component/componentForm";
	}
	
}
