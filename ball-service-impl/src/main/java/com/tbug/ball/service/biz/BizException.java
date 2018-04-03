package com.tbug.ball.service.biz;

public class BizException extends Exception {
	private static final long serialVersionUID = 1L;

	public BizException() {
		super();
	}

	public BizException(String message) {
		super(message);
	}

	public BizException(String message, Throwable cause) {
		super(message, cause);
	}

	public BizException(Throwable cause) {
		super(cause);
	}

	protected BizException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
