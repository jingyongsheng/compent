package com.zcbl.modules.log.controller;

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
import com.zcbl.modules.log.entity.CompentLog;
import com.zcbl.modules.log.service.ComponetLogService;

@Controller
@RequestMapping(value = "${adminPath}/log")
public class ComponentLogController extends BaseController
{

	@Autowired
	private ComponetLogService componetService;

	@ModelAttribute
	public CompentLog get(@RequestParam(required = false) String id)
	{
		if (StringUtils.isNotBlank(id))
		{
			return componetService.get(id);
		} else
		{
			return new CompentLog();
		}
	}

	@RequestMapping(value =
	{ "list", "" })
	public String list(CompentLog compentLog, HttpServletRequest request, HttpServletResponse response, Model model)
	{

		Page<CompentLog> page = componetService.findPage(new Page<CompentLog>(request, response), compentLog);
		model.addAttribute("page", page);
		return "modules/log/componentList";
	}

	@RequestMapping(value = "save")
	public String save(CompentLog compentLog)
	{
		componetService.save(compentLog);
		return "redirect:" + adminPath + "/log?repage&type=" + compentLog.getType();
	}

	@RequestMapping(value = "form")
	public String form(CompentLog compentLog, Model model)
	{

		model.addAttribute("compentLog", compentLog);
		return "modules/log/componentForm";
	}

}
