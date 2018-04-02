package com.tbug.ball.manage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reger.dubbo.annotation.Inject;
import com.tbug.ball.manage.common.annotation.Log;
import com.tbug.ball.manage.controller.base.BaseController;
import com.tbug.ball.manage.util.R;
import com.tbug.ball.service.OrderService;
import com.tbug.ball.service.ScheduleService;
import com.tbug.ball.service.ServiceException;
import com.tbug.ball.service.Dto.schedule.ScheduleDto;
import com.tbug.ball.service.common.DBContants.ScheduleConst;
import com.tbug.ball.service.common.DBContants.StakeOrderConst;

@Controller
@RequestMapping(ScheduleController.prefix)
public class ScheduleController extends BaseController {

	public static final String prefix = "/operate/schedule";
	
	@Inject
	ScheduleService scheduleService;
	@Inject
	OrderService orderService;
	
	@GetMapping()
	@RequiresPermissions("operate:schedule:schedule")
	String schedule(){
		return prefix + "/schedule";
	}
	
	@GetMapping("/list")
	@ResponseBody
	@RequiresPermissions("operate:schedule:schedule")
	List<ScheduleDto> list(@RequestParam("aName") String aName){
		Map<String, Object> params = new HashMap<>();
		if (!StringUtils.isEmpty(aName)){
			params.put("aName", aName);
		}
		List<ScheduleDto> scheduleDtoList = scheduleService.listScheduleDtoByMap(params);

		return scheduleDtoList;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("operate:schedule:add")
	String add(){
		return prefix + "/add";
	}
	
	@Log("添加赛程")
	@ResponseBody
	@PostMapping("save")
	@RequiresPermissions("operate:schedule:add")
	R save(ScheduleDto scheduleDto){
		try {
			scheduleDto.setGameResult(ScheduleConst.GAME_RESULT_0);
			scheduleDto.setaScore(null);
			scheduleDto.setbScore(null);
			scheduleDto.setCreatePerson(getCurrentUser().getLoginName());
			if (!scheduleService.createSchedule(scheduleDto)){
				return R.error();
			}
			return R.ok("操作成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return R.error(e.getMessage());
		}
	}
	
	@GetMapping("/edit/{id}")
	@RequiresPermissions("operate:schedule:edit")
	String edit(@PathVariable("id") Integer id, Model model){
		
		ScheduleDto scheduleDto = scheduleService.getScheduleDtoById(id);
		model.addAttribute("schedule", scheduleDto);
		
		return prefix + "/edit";
	}
	
	@ResponseBody
	@PostMapping("/update")
	@RequiresPermissions("operate:schedule:edit")
	R update(ScheduleDto scheduleDto){
		try {
			ScheduleDto DBScheduleDto = scheduleService.getScheduleDtoById(scheduleDto.getId());
			if (!ScheduleConst.STATUS_NEW.equals(DBScheduleDto.getStatus()) ){
				return R.error("当前状态不能修改信息");
			}
			
			if (!scheduleService.updSchedule(scheduleDto)){
				return R.error();
			}
			return R.ok("操作成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return R.error("操作失败");
		}
	}
	
	@ResponseBody
	@GetMapping("/onSale/{id}")
	@RequiresPermissions("operate:schedule:onSale")
	R onSale(@PathVariable("id") Integer id){
		ScheduleDto scheduleDto = new ScheduleDto();
		scheduleDto.setId(id);
		scheduleDto.setStatus(ScheduleConst.STATUS_START);
		try {
			ScheduleDto DBScheduleDto = scheduleService.getScheduleDtoById(id);
			if (!ScheduleConst.STATUS_NEW.equals(DBScheduleDto.getStatus()) ){
				return R.error("当前状态不能上架");
			}
			
			if (!scheduleService.updSchedule(scheduleDto)){
				return R.error();
			}
			return R.ok("操作成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return R.error(e.getMessage());
		}
	}
	
	@ResponseBody
	@GetMapping("/offSale/{id}")
	@RequiresPermissions("operate:schedule:offSale")
	R offSale(@PathVariable("id") Integer id){
		ScheduleDto scheduleDto = new ScheduleDto();
		scheduleDto.setId(id);
		scheduleDto.setStatus(ScheduleConst.STATUS_END);
		try {
			ScheduleDto DBScheduleDto = scheduleService.getScheduleDtoById(id);
			if (!ScheduleConst.STATUS_START.equals(DBScheduleDto.getStatus()) ){
				return R.error("当前状态不能下架");
			}
			
			if (!scheduleService.updSchedule(scheduleDto)){
				return R.error();
			}
			return R.ok("操作成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return R.error(e.getMessage());
		}
	}
	
	@GetMapping("/result/{id}")
	@RequiresPermissions("operate:schedule:result")
	String result(@PathVariable("id") Integer id, Model model){
		ScheduleDto scheduleDto = scheduleService.getScheduleDtoById(id);
		model.addAttribute("schedule", scheduleDto);
		
		return prefix + "/result";
	}
	
	@ResponseBody
	@PostMapping("/result/save")
	@RequiresPermissions("operate:schedule:result")
	R resultSave(ScheduleDto scheduleDto){
		try {
			ScheduleDto DBScheduleDto = scheduleService.getScheduleDtoById(scheduleDto.getId());
			if (!ScheduleConst.STATUS_END.equals(DBScheduleDto.getStatus()) ){
				return R.error("当前状态不能录入比赛结果");
			}
			if (scheduleDto.getaScore() == null){
				throw new ServiceException("甲方得分不能为空");
			}
			if (scheduleDto.getaScore().compareTo(Byte.valueOf("0")) < 0){
				throw new ServiceException("甲方得分不能为负值");
			}
			if (scheduleDto.getbScore() == null){
				throw new ServiceException("乙方得分不能为空");
			}
			if (scheduleDto.getbScore().compareTo(Byte.valueOf("0")) < 0){
				throw new ServiceException("乙方得分不能为负值");
			}
			if (scheduleDto.getGameResult() == null){
				throw new ServiceException("赛程结果不能为空");
			}
			if ((scheduleDto.getaScore().compareTo(scheduleDto.getbScore()) > 0 && !StakeOrderConst.STAKE_TYPE_WIN.equals(scheduleDto.getGameResult()))
					|| (scheduleDto.getaScore().compareTo(scheduleDto.getbScore()) == 0 && !StakeOrderConst.STAKE_TYPE_DRAW.equals(scheduleDto.getGameResult()))
					|| (scheduleDto.getaScore().compareTo(scheduleDto.getbScore()) < 0 && !StakeOrderConst.STAKE_TYPE_LOSE.equals(scheduleDto.getGameResult()))){
				throw new ServiceException("赛程结果与比分不符");
			}
			scheduleDto.setStatus(ScheduleConst.STATUS_RESULT);
			if (!scheduleService.updSchedule(scheduleDto)){
				return R.error();
			}
			return R.ok("操作成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return R.error(e.getMessage());
		}
	}
	
	@GetMapping("/detail/{id}")
	@RequiresPermissions("operate:schedule:schedule")
	String detail(@PathVariable("id") Integer id, Model model){
		ScheduleDto scheduleDto = scheduleService.getScheduleDtoById(id);
		model.addAttribute("schedule", scheduleDto);
		return prefix + "/detail";
	}
	
	@Log("赛程结算")
	@ResponseBody
	@GetMapping("/clean/{id}")
	@RequiresPermissions("operate:schedule:clean")
	R clean(@PathVariable("id") Integer id){
		ScheduleDto scheduleDto = scheduleService.getScheduleDtoById(id);
		if (!ScheduleConst.STATUS_RESULT.equals(scheduleDto.getStatus())){
			return R.error("赛程状态不能结算");
		}
		String msg = orderService.stakeCleanBySchedule(getCurrentUser().getLoginName(), id);
		return R.ok(msg);
	}
	
}
