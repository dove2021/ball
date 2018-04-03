package com.tbug.ball.service.model.flow;

import java.math.BigDecimal;

public class CustomerAccountFlowSum {

	/** 收入 */
	private BigDecimal inCome;
	
	/** 支出 */
	private BigDecimal outGo;

	public BigDecimal getInCome() {
		return inCome;
	}

	public void setInCome(BigDecimal inCome) {
		this.inCome = inCome;
	}

	public BigDecimal getOutGo() {
		return outGo;
	}

	public void setOutGo(BigDecimal outGo) {
		this.outGo = outGo;
	}

	
}
