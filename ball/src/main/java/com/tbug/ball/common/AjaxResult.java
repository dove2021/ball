package com.tbug.ball.common;

import java.util.HashMap;
import java.util.Map;

public class AjaxResult extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	public AjaxResult() { 
		put("code", 1);
		put("msg", "操作成功");
	}
 
	public static AjaxResult error() {
		return error(0, "操作失败");
	}

	public static AjaxResult error(String msg) {
		return error(500, msg);
	}

	public static AjaxResult error(int code, String msg) {
		AjaxResult r = new AjaxResult();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static AjaxResult ok(String msg) {
		AjaxResult r = new AjaxResult();
		r.put("msg", msg);
		return r;
	}

	public static AjaxResult ok(Map<String, Object> map) {
		AjaxResult r = new AjaxResult();
		r.putAll(map);
		return r;
	}

	public static AjaxResult ok() {
		return new AjaxResult();
	}

	@Override
	public AjaxResult put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
