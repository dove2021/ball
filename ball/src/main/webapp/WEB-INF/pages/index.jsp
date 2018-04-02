<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/include.inc.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
<title>主页</title>

<link rel="shortcut icon" href="static/img/favicon.ico">

<link rel="stylesheet" href="static/js/weui/css/demos.css">
<link rel="stylesheet" href="static/js/weui/css/example.css">
<link rel="stylesheet" href="static/js/weui/css/weui.min.css">
<link rel="stylesheet" href="static/js/weui/css/jquery-weui.min.css">
<link rel="stylesheet" href="static/js/swiper/swiper.min.css">

<link rel="stylesheet" href="static/css/style.css">
<link href="static/css/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet">

<style type="text/css">
.swiper-container {width: 100%;height: 22%;}
.swiper-slide {text-align: center;font-size: 18px;background: #fff;/* Center slide text vertically */display: -webkit-box;display: -ms-flexbox;display: -webkit-flex;display: flex;-webkit-box-pack: center;-ms-flex-pack: center;-webkit-justify-content: center;justify-content: center;-webkit-box-align: center;-ms-flex-align: center;-webkit-align-items: center;align-items: center;}
.swiper-pagination-bullet {width: 20px;height: 20px;text-align: center;line-height: 20px;font-size: 12px;color:#000;opacity: 1;background: rgba(0,0,0,0.2);}
.swiper-pagination-bullet-active {color:#fff;background: #007aff;}

.black-row{display:block;position:absolute;height: 39px;width: 100%;}

.stake_open .weui_cells_stake_fee .weui-cell {line-height: 1;}
.stake_open .weui_cells_stake_open .weui-cell {line-height: 0.6;padding: 5px 5px;}    
</style>
</head>

<body >
<div class="page home js_show">
	<div class="weui-tab">
		<div class="weui-tab__bd">
		
			<!-- tab1 -->
			<div id="tab1" class="weui-tab__bd-item weui-tab__bd-item--active">
				<!-- Swiper -->
				<div class="swiper-container">
					<div class="swiper-wrapper">
						<div class="swiper-slide" style="background-image: url(static/img/demo/a.jpg);"></div>
						<div class="swiper-slide"><img alt="" src="static/img/demo/b.jpg"></div>
						<div class="swiper-slide"><img alt="" src="static/img/demo/c.jpg"></div>
						<div class="swiper-slide"><img alt="" src="static/img/demo/d.jpg"></div>
						<div class="swiper-slide"><img alt="" src="static/img/demo/e.jpg"></div>
					</div>
					<!-- Add Pagination -->
					<div class="swiper-pagination"></div>
				</div>
				<div class="weui-panel">
		            <div class="weui-panel__hd"><h2 style="text-align: center;font-weight: 900;">预售球赛</h2></div>
		            <div class="weui-panel__hd" style="text-align: center;">
                    	<div class="weui-flex" style="padding: 0px;">
                    		<div class="placeholder w_100"><span class="span_title">甲方</span></div>
				            <div class="weui-flex__item">
					            <div class="placeholder" style="text-align: center;">
					            	<span class="span_title">胜</span>
					            	<span class="span_title">平</span>
					            	<span class="span_title">负</span>
					            </div>
				            </div>
				            <div class="placeholder w_100" style="text-align: center;"><span class="span_title">乙方</span></div>
				        </div>
		            </div>
		            <div class="weui-panel__bd">
		            <c:choose>
		            	<c:when test="${empty scheduleNum}">
		                <div class="weui-media-box weui-media-box_text">
		                    <p class="weui-media-box__desc">
		                    	<div class="weui-flex" style="padding: 5px 0px;">
		                    		<div><div class="placeholder w_100 gamer_name">中国</div></div>
						            <div class="weui-flex__item">
							            <div class="placeholder" style="text-align: center;">
							            	<a href="javascript:stakeOrder(1,1,0,8)" class="a_button">8.88</a>
							            	<a href="javascript:stakeOrder(1,1,0,8)" class="a_button">8.88</a>
							            	<a href="javascript:stakeOrder(1,1,0,8)" class="a_button">8.88</a>
							            </div>
						            </div>
						            <div><div class="placeholder w_100 gamer_name">美国</div></div>
						        </div>
		                    </p>
		                </div>
		            	</c:when>
		            	<c:otherwise>
				            <c:forEach items="${scheduleList}" var="schedule">
			                <div class="weui-media-box weui-media-box_text">
			                    <p class="weui-media-box__desc">
			                    	<div class="weui-flex" style="padding: 5px 0px;">
			                    		<div><div class="placeholder w_100 gamer_name">${schedule.aName}</div></div>
							            <div class="weui-flex__item">
								            <div class="placeholder a_b" style="text-align: center;">
								            	<a href="javascript:stakeOrder(<fmt:formatDate value="${schedule.startTime}" pattern="yyMMddHHmmss" />,${schedule.id},1,${schedule.oddsWin});" class="a_button">${schedule.oddsWin}</a>
								            	<a href="javascript:stakeOrder(<fmt:formatDate value="${schedule.startTime}" pattern="yyMMddHHmmss" />,${schedule.id},2,${schedule.oddsDraw});" class="a_button">${schedule.oddsDraw}</a>
								            	<a href="javascript:stakeOrder(<fmt:formatDate value="${schedule.startTime}" pattern="yyMMddHHmmss" />,${schedule.id},3,${schedule.oddsLose});" class="a_button">${schedule.oddsLose}</a>
								            </div>
							            </div>
							            <div><div class="placeholder w_100 gamer_name">${schedule.bName}</div></div>
							        </div>
			                    </p>
			                </div>				            
				            </c:forEach>
		            	</c:otherwise>
		            </c:choose>
		            </div>
		        </div>
				<div class="page__ft">
			        <a href="javascript:home()"><img src="static/img/favicon.ico"></a>
			    </div>
			</div>
			<!-- tab1 end -->
			
			<!-- tab2 -->
			<div id="tab2" class="weui-tab__bd-item">
			<div class="page preview js_show">
				<div class="page__bd">
					<c:forEach items="${stakeOrderExlist}" var="stakeOrderEx">
					<div class="weui-form-preview">
						<div class="weui-form-preview__hd">
						    <label class="weui-form-preview__label">猜中可兑</label>
						    <span class="weui-form-preview__value">${stakeOrderEx.preCleanAmount}</span>
						</div>
						<div class="weui-form-preview__bd">
						    <div class="weui-form-preview__item">
						        <label class="weui-form-preview__label">赛程标题</label>
						        <span class="weui-form-preview__value">${stakeOrderEx.title}</span>
						    </div>
						    <div class="weui-form-preview__item">
						        <label class="weui-form-preview__label">下注方向</label>
						        <span class="weui-form-preview__value">${stakeOrderEx.stakeType eq '1' ? '胜' : stakeOrderEx.stakeType eq '2'? "平" : stakeOrderEx.stakeType eq '3' ? "负" : ""}</span>
						    </div>
						    <div class="weui-form-preview__item">
						        <label class="weui-form-preview__label">下注金额</label>
						        <em class="weui-form-preview__value">${stakeOrderEx.stakeAmount}</em>
						    </div>
						</div>
						<div class="weui-form-preview__ft">
						    <a type="button" class="weui-form-preview__btn weui-form-preview__btn_primary" href="javascript:orderDetail(${stakeOrderEx.id});">详情</a>
						</div>
					</div>
					<br>
					</c:forEach>
				</div>
			  	<div class="page__ft">
			        <a href="javascript:home()"><img src="static/img/favicon.ico"></a>
			    </div>
			</div>
			</div>
			<!-- tab2 end -->
			
			<!-- tab3 -->
			<div id="tab3" class="weui-tab__bd-item">
			  	<div class="page preview js_show">
			  		<div class="page__bd">
					<c:forEach items="${cleanList}" var="clean">
					<div class="weui-form-preview">
						<div class="weui-form-preview__hd">
						    <label class="weui-form-preview__label">兑奖额度</label>
						    <span class="weui-form-preview__value">${clean.cleanAmount}</span>
						</div>
						<div class="weui-form-preview__bd">
						    <div class="weui-form-preview__item">
						        <label class="weui-form-preview__label">赛程标题</label>
						        <span class="weui-form-preview__value">${clean.title}</span>
						    </div>
						    <div class="weui-form-preview__item">
						        <label class="weui-form-preview__label">赛程结果</label>
						        <em class="weui-form-preview__value" style="font-weight: bold;">${clean.aScore}:${clean.bScore} ${clean.gameResult eq '1' ? '胜' : clean.gameResult eq '2' ? "平" : clean.gameResult eq '3' ? "负" : ""}</em>
						    </div>
						</div>
						<div class="weui-form-preview__ft">
						    <a class="weui-form-preview__btn weui-form-preview__btn_primary" href="javascript:draw(${clean.id});">立即领取</a>
						</div>
					</div>
					<br>
					</c:forEach>			  		
			  		</div>
				  	<div class="page__ft">
				        <a href="javascript:home()"><img src="static/img/favicon.ico"></a>
				    </div>
			  	</div>
			</div>
			<!-- tab3 end -->
			
			<!-- tab4 -->
			<div id="tab4" class="weui-tab__bd-item">
			  	<div class="page__bd">
			  		<div class="weui-panel weui-panel_access">
						<a href="javascript:void(0);" class="weui-cell weui-media-box weui-media-box_appmsg">
		                    <div class="weui-media-box__hd">
		                        <img class="weui-media-box__thumb" style="width: 100%;height: 100%" src="static/js/weui/img/ball/2018football.png" alt="" />
		                    </div>
		                    <div class="weui-media-box__bd">
		                        <h4 class="weui-media-box__title">余额: <strong id="balance"><c:choose><c:when test="${!empty balance}">${balance}</c:when><c:otherwise>8888.88</c:otherwise></c:choose></strong></h4>
		                        <p class="weui-media-box__desc">客户号: <c:choose><c:when test="${!empty customerCode}">${customerCode}</c:when><c:otherwise>13866668888</c:otherwise></c:choose></p>
		                    </div>
		                </a>
			  		</div>
					<div class="weui-panel">
			            <div class="weui-panel__bd">
			                <div class="weui-media-box weui-media-box_small-appmsg">
			                    <div class="weui-cells">
			                        <a class="weui-cell weui-cell_access" href="javascript:;">
			                            <div class="weui-cell__hd"><!-- <img src="static/js/weui/img/present.png" alt="" style="width:26px;display:block"> -->
			                            	<i class="fa fa-download" aria-hidden="true"></i>
			                            </div>
			                            <div class="weui-cell__bd weui-cell_primary">
			                                <p style="margin-left: 5px;">充值</p>
			                            </div>
			                            <span class="weui-cell__ft"></span>
			                        </a>
			                        <a class="weui-cell weui-cell_access" href="javascript:;">
			                            <div class="weui-cell__hd"><!-- <img src="static/js/weui/img/ball/2018football.png" alt="" style="width:26px;display:block"> -->
			                            	<i class="fa fa-upload" aria-hidden="true"></i>
			                            </div>
			                            <div class="weui-cell__bd weui-cell_primary">
			                                <p style="margin-left: 5px;">提现</p>
			                            </div>
			                            <span class="weui-cell__ft"></span>
			                        </a>
			                    </div>
			                </div>
			            </div>
			        </div>
					<div class="weui-panel">
			            <div class="weui-panel__bd">
			                <div class="weui-media-box weui-media-box_small-appmsg">
			                    <div class="weui-cells">
			                    	<a class="weui-cell weui-cell_access" href="fund">
			                            <div class="weui-cell__hd"><!-- <img src="static/js/weui/img/ball/2018football.png" alt="" style="width:26px;display:block"> -->
			                            	<i class="fa fa-list" aria-hidden="true"></i>
			                            </div>
			                            <div class="weui-cell__bd weui-cell_primary">
			                                <p style="margin-left: 5px;">资金流水</p>
			                            </div>
			                            <span class="weui-cell__ft"></span>
			                        </a>
			                        <a class="weui-cell weui-cell_access" href="orderH">
			                            <div class="weui-cell__hd"><!-- <img src="static/js/weui/img/ball/2018football.png" alt="" style="width:26px;display:block"> -->
			                            	<i class="fa fa-th-list" aria-hidden="true"></i>
			                            </div>
			                            <div class="weui-cell__bd weui-cell_primary">
			                                <p style="margin-left: 5px;">历史订单</p>
			                            </div>
			                            <span class="weui-cell__ft"></span>
			                        </a>
			                    </div>
			                </div>
			            </div>
			        </div>
					<div class="weui-panel">
			            <div class="weui-panel__bd">
			                <div class="weui-media-box weui-media-box_small-appmsg">
			                    <div class="weui-cells">
			                        <a class="weui-cell weui-cell_access" href="customer/set">
			                            <div class="weui-cell__hd"><!-- <img src="static/js/weui/img/ball/2018football.png" alt="" style="width:26px;display:block"> -->
			                            	<i class="fa fa-cog fa-spin"></i>
			                            </div>
			                            <div class="weui-cell__bd weui-cell_primary">
			                                <p style="margin-left: 5px;">个人设置</p>
			                            </div>
			                            <span class="weui-cell__ft"></span>
			                        </a>
			                    </div>
			                </div>
			            </div>
			        </div>
					<div class="weui-panel">
			            <div class="weui-panel__bd">
			                <div class="weui-media-box weui-media-box_small-appmsg">
			                    <div class="weui-cells">
			                        <a class="weui-cell weui-cell_access" href="javascript:logOff();">
			                            <div class="weui-cell__bd weui-cell_primary">
			                                <p style="text-align: center;">退出登录</p>
			                            </div>
			                        </a>
			                    </div>
			                </div>
			            </div>
			        </div>
			  	</div>
			  	<div class="page__ft">
			        <a href="javascript:;"><img src="static/img/favicon.ico"></a>
			    </div>
			</div>
			<!-- tab4 end -->
		</div>
		
		<div class="weui-tabbar">
			<a href="#tab1" class="weui-tabbar__item weui-bar__item--on" >
				<div class="weui-tabbar__icon" style="display: inline-block;position: relative;">
			 		<img src="static/js/weui/img/icon_nav_article.png" alt="">
					<c:choose>
						<c:when test="${empty scheduleNum}">
						<span class="weui-badge" style="position: absolute;top: -.4em;right: -.6em;">1</span>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${scheduleNum <= 0}"></c:when>
								<c:otherwise>
								<span class="weui-badge" style="position: absolute;top: -.4em;right: -.6em;">${scheduleNum}</span>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
			  	</div>
			  	<p class="weui-tabbar__label">赛程</p>
			</a>
			<a href="#tab2" class="weui-tabbar__item">
			  	<div class="weui-tabbar__icon" style="display: inline-block;position: relative;">
			    	<img src="static/js/weui/img/icon_nav_cell.png" alt="">
				  	<c:if test="${!empty orderNum}">
				  	<span class="weui-badge" style="position: absolute;top: -.4em;right: -.6em;">${orderNum}</span>
				  	</c:if>
			  	</div>
				<p class="weui-tabbar__label">注单</p>
			</a>
			<a href="#tab3" class="weui-tabbar__item">
			  	<div class="weui-tabbar__icon" style="display: inline-block;position: relative;">
			    	<img src="static/js/weui/img/present.png" alt="">
				  	<c:if test="${!empty cleanNum}">
				  	<span class="weui-badge" style="position: absolute;top: -.4em;right: -.6em;">${cleanNum}</span>
				  	</c:if>
			  	</div>
			  	<p class="weui-tabbar__label">兑奖</p>
			</a>
			<a href="#tab4" class="weui-tabbar__item">
			  	<div class="weui-tabbar__icon">
			    	<img src="static/js/weui/img/icon_nav_button.png" alt="">
			  	</div>
			  <p class="weui-tabbar__label">我</p>
			</a>
		</div>
	</div>
</div>

<c:forEach items="${stakeDtoList}" var="stake">
<input type="hidden" id="${stake.stakeCode}_fee" name="${stake.stakeCode}_fee" value="${stake.tradeFee + stake.memberFee}" />
<input type="hidden" id="${stake.stakeCode}_price" name="${stake.stakeCode}_price" value="${stake.price}" />
</c:forEach>

<div id="stake_popup-bottom" class="weui-popup__container popup-bottom stake_open">
	<div class="weui-popup__overlay"></div>
	<div class="weui-popup__modal">
		<div class="weui-cells weui-cells_form">
			<input type="hidden" id="scheduleId" name="scheduleId" />
			<input type="hidden" id="stakeType" name="stakeType" />
			<input type="hidden" id="stakeCode" name="stakeCode" value="XZ01"/>
			<input type="hidden" id="num" name="num" value="1" />
			<input type="hidden" id="stakeAmount" name="stakeAmount" value="10"/>
			<input type="hidden" id="stakeFee" name="stakeFee" value="0.8" />
			<input type="hidden" id="totalAmount" name="totalAmount" value="" />
			<div class="toolbar">
				<div class="toolbar-inner">
		            <a href="javascript:;" class="picker-button close-popup">关闭</a>
		            <h1 id="schedule_title" class="title"></h1>
	          	</div>
			</div>
			<div class="modal-content weui-cells_form ">
				<div class="weui-cells weui_cells_stake_open">
					<div class="weui-cell">
						<div class="weui-cell__hd"><label class="weui-label">下注方向</label></div>
						<div class="weui-cell__bd">
							<div class="weui-flex">
								<a href="javascript:;" id="stakeType1" class="weui-btn weui-btn_mini weui-btn_default stakeType" style="margin-top:15px">&nbsp;胜&nbsp;</a>
								<a href="javascript:;" id="stakeType2" class="weui-btn weui-btn_mini weui-btn_disabled weui-btn_default stakeType">&nbsp;平&nbsp;</a>
								<a href="javascript:;" id="stakeType3" class="weui-btn weui-btn_mini weui-btn_disabled weui-btn_default stakeType">&nbsp;负&nbsp;</a>
							</div>
						</div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__hd"><label class="weui-label">下注品种</label></div>
						<div class="weui-cell__bd">
							<div class="weui-flex">
								<a href="javascript:orderStakeCode('XZ01');" id="XZ01" class="weui-btn weui-btn_mini weui-btn_default stakeCode c_default" style="margin-top:15px">10</a>
								<a href="javascript:orderStakeCode('XZ02');" id="XZ02" class="weui-btn weui-btn_mini weui-btn_default stakeCode">100</a>
								<a href="javascript:orderStakeCode('XZ03');" id="XZ03" class="weui-btn weui-btn_mini weui-btn_default stakeCode">1000</a>
							</div>
						</div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__hd"><label class="weui-label">下注手数</label></div>
						<div class="weui-cell__bd">
							<div class="weui-flex">
								<a href="javascript:orderStakeNum(1);" id="num_1" class="weui-btn weui-btn_mini stakeNum weui-btn_default c_default" style="margin-top:15px">1</a>
								<a href="javascript:orderStakeNum(2);" id="num_2" class="weui-btn weui-btn_mini stakeNum weui-btn_default">2</a>
								<a href="javascript:orderStakeNum(3);" id="num_3" class="weui-btn weui-btn_mini stakeNum weui-btn_default">3</a>
								<a href="javascript:orderStakeNum(4);" id="num_4" class="weui-btn weui-btn_mini stakeNum weui-btn_default">4</a>
								<a href="javascript:orderStakeNum(5);" id="num_5" class="weui-btn weui-btn_mini stakeNum weui-btn_default">5</a>
							</div>
							<div class="weui-flex">
								<a href="javascript:orderStakeNum(6);" id="num_6" class="weui-btn weui-btn_mini stakeNum weui-btn_default" style="margin-top:15px">6</a>
								<a href="javascript:orderStakeNum(7);" id="num_7" class="weui-btn weui-btn_mini stakeNum weui-btn_default">7</a>
								<a href="javascript:orderStakeNum(8);" id="num_8" class="weui-btn weui-btn_mini stakeNum weui-btn_default">8</a>
								<a href="javascript:orderStakeNum(9);" id="num_9" class="weui-btn weui-btn_mini stakeNum weui-btn_default">9</a>
								<a href="javascript:orderStakeNum(10);" id="num_10" class="weui-btn weui-btn_mini stakeNum weui-btn_default">x</a>
							</div>
						</div>
					</div>
				</div>
				<div class="weui-cells__title">费用</div>
				<div class="weui-cells weui_cells_stake_fee">
					<div class="weui-cell">
						<div class="weui-cell__hd"><label class="weui-label">下注金额</label></div>
						<div class="weui-cell__bd">
							<label class="weui-label"><strong id="stakeAmount_c">10.00</strong></label>
						</div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__hd"><label class="weui-label">手  续 费</label></div>
						<div class="weui-cell__bd">
							<label class="weui-label"><strong id="stakeFee_c">0.80</strong></label>
						</div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__hd"><label class="weui-label">总  费 用</label></div>
						<div class="weui-cell__bd">
							<label class="weui-label"><strong id="totalAmount_c">10.80</strong></label>
						</div>
					</div>
				</div>
			</div>
			<div class="weui-btn-area">
            	<a class="weui-btn weui-btn_primary" href="javascript:orderStake();" id="showTooltips">确定下注(<span id="clean_amount">胜,兑10.80</span>)</a>
	        </div>
		</div>
	</div>
</div>

<div id="weui_popup__container" class="weui-popup__container">
    <div class="weui-popup__overlay"></div>
    <div class="weui-popup__modal">
  	    <div class="weui-cells weui-cells_form">
		<div class="toolbar">
			<div class="toolbar-inner">
                <a href="javascript:;" class="picker-button close-popup">关闭</a>
                <h1 id="schedule_title" class="title">订单详情</h1>
        	</div>
		</div>
		<div class="modal-content weui-cells_form">
		<div class="page__bd">
			<div class="weui-cells">
				<div class="weui-cell">
					<div class="weui-cell__hd"><label class="weui-label">订单编号</label></div>
					<div class="weui-cell__bd">
						<strong id="od_orderNo"></strong>
					</div>
				</div>
	        	<div class="weui-cell">
					<div class="weui-cell__hd"><label class="weui-label">下注时间</label></div>
					<div class="weui-cell__bd">
						<strong id="od_createTime"></strong>
					</div>
				</div>
	        </div>
	        <div class="weui-cells">
	        	<div class="weui-cell">
					<div class="weui-cell__hd"><label class="weui-label">下注方向</label></div>
					<div class="weui-cell__bd">
						<div class="weui-btn weui-btn_mini weui-btn_default stakeType c_default" >&nbsp;<span id="od_stakeType"></span>&nbsp;</div>
					</div>
				</div>
	        	<div class="weui-cell">
					<div class="weui-cell__hd"><label class="weui-label">下注品种</label></div>
					<div class="weui-cell__bd">
						<div id="od_stakePrice" class="weui-btn weui-btn_mini weui-btn_default stakeType c_default" ></div>
					</div>
				</div>
	        	<div class="weui-cell">
					<div class="weui-cell__hd"><label class="weui-label">下注手数</label></div>
					<div class="weui-cell__bd">
						<div id="od_orderNum" class="weui-btn weui-btn_mini weui-btn_default stakeType c_default" ></div>
					</div>
				</div>
	        	<div class="weui-cell">
					<div class="weui-cell__hd"><label class="weui-label">下注手续费</label></div>
					<div class="weui-cell__bd">
						<strong id="od_fee"></strong>
					</div>
				</div>
	        </div>
	        <div class="weui-cells">
	        	<div class="weui-cell">
					<div class="weui-cell__hd"><label class="weui-label">下注金额</label></div>
					<div class="weui-cell__bd">
						<strong id="od_stakeAmount"></strong>
					</div>
				</div>
	        	<div class="weui-cell">
					<div class="weui-cell__hd"><label class="weui-label">赔率</label></div>
					<div class="weui-cell__bd">
						<strong id="od_oddsScale"></strong>
					</div>
				</div>
	        	<div class="weui-cell">
					<div class="weui-cell__hd"><label class="weui-label">猜对兑奖</label></div>
					<div class="weui-cell__bd">
						<strong id="od_preCleanAmount"></strong>
					</div>
				</div>
	        </div>
		</div>
		</div>
  	</div>
  </div>
</div>

</body>

<script src="static/js/jquery-3.2.1.min.js"></script>
<script src="static/js/weui/jquery-weui.min.js"></script>
<script src='static/js/swiper/swiper.min.js'></script>

<script>

$(function(){
    	$.ajax({
    		type: "get",
    		url: "login/check",
    		dataType: "json",
    		error: function(){
    			$.toptip('网络异常', 'error');
    		},
    		success: function(data){
    			if (data.code !=  1){
    				$.modal({
    					  title: "提示",
    					  text: "您还没有登录",
    					  buttons: [
    					    { text: "登录", onClick: function(){ window.location.href = "login";} },
    					    /* { text: "注册", onClick: function(){window.location.href = "customer/register/0";}}, */
    					    { text: "取消", className: "default", onClick: function(){ console.log(3)} },
    					  ]
    				});
    			}
    		}
    	});		
	});
	
    var swiper = new Swiper('.swiper-container', {
    	loop : true,
    	autoplay:true,
        pagination: {
        	el: '.swiper-pagination',
          	clickable: true,
          	renderBullet: function (index, className) {
            	return '<span class="' + className + '">' + (index + 1) + '</span>';
          	},
        },
    });
	
    function logOff(){
    	$.actions({
    		title: '确定要退出吗？',
    		actions: [{
    		    text: "退出登录",
    		    className: "color-danger",
    		    onClick: function() {
    		    	$.ajax({
    		    		type: "get",
    		    		url: "login/off",
    		    		dataType: "json",
    		    		success: function(data){
    		    			if (data.code !=  1){
    		    				$.toast(data.msg, "forbidden");
    		    			} else {
    		    				$.toast("已退出登陆", function() {
    		    					//do something
    		    					window.location.href = "index";
    		    				});
    		    			}
    		    		}
    		    	});    		      
    		    }
    		}]
    	});
    }
    
    // 赔率
    var odds_scale = 0;
    // 定时器id
    var intervalID = 0;
    // 下单方向 1_胜 2_平 3_负
    var type_name = "";
    function stakeOrder(startTime,stakeId, type, odds){
    	odds_scale = odds;
    	$.ajax({
    		type: "get",
    		url: "login/check",
    		dataType: "json",
    		error: function(){
    			$.toptip('链接失败', 'error');
    		},
    		success: function(data){
    			if (data.code !=  1){
    				$.modal({
  						title: "提示",
  					  	text: "您还没有登录",
  					  	buttons: [
  					    	{ text: "登录", onClick: function(){ window.location.href = "login";} },
  					    	/* { text: "注册", onClick: function(){ window.location.href = "customer/register/0";} }, */
  					    	{ text: "取消", className: "default", onClick: function(){ console.log(3)} },
  					  	]
  					});
    				
    			} else {
    				// 样式
    				$(".stakeType").removeClass("c_default");
    				$(".stakeType").addClass("weui-btn_disabled");
    				if (type == 1){
    					type_name = "胜";
    					$("#stakeType1").removeClass("weui-btn_disabled");
    					$("#stakeType1").addClass("c_default");
    				} else if (type == 2){
    					type_name = "平";
    					$("#stakeType2").removeClass("weui-btn_disabled");
    					$("#stakeType2").addClass("c_default");
    				} else if (type == 3){
    					type_name = "负";
    					$("#stakeType3").removeClass("weui-btn_disabled");
    					$("#stakeType3").addClass("c_default");
    				}
    				$(".stakeCode").removeClass("c_default");
    				$("#XZ01").addClass("c_default");
    				$(".stakeNum").removeClass("c_default");
    				$("#num_1").addClass("c_default");
    				
    				// 计算
    				var price = $("#XZ01_price").val();
			    	var fee = $("#XZ01_fee").val();
			    	
			    	var stakeAmount = parseFloat(price);
			    	var stakeFee = parseFloat(fee);
			    	var totalAmount = stakeAmount + stakeFee;
			    	
			    	var cleanAmount = odds_scale * stakeAmount;
    				
    				// 初始化数据
    				$("#scheduleId").val(stakeId);
    				$("#stakeType").val(type);
    				$("#stakeCode").val("XZ01");
    				$("#num").val(1);
    				$("#stakeAmount").val(stakeAmount);
    				$("#stakeFee").val(stakeFee);
    				$("#totalAmount").val(totalAmount);
    				
    		    	// 显示客户
    		    	$("#stakeAmount_c").html(stakeAmount.toFixed(2));
    		    	$("#stakeFee_c").html(stakeFee.toFixed(2));
    		    	$("#totalAmount_c").html(totalAmount.toFixed(2));
    				
    		    	$("#clean_amount").html("赔率"+odds_scale.toFixed(2)+","+type_name+"兑" + cleanAmount.toFixed(2));
    				
    				window.clearInterval(intervalID);
    				showCountDown("" + startTime,'schedule_title');
    				intervalID = window.setInterval(function(){showCountDown("" + startTime,'schedule_title');}, 1000);
    				
    				// 获取数据
    				$("#stake_popup-bottom").popup();
    			}
    		}
    	});
    }
    
    function showCountDown(startTime,divname){
	    var now = new Date();
	    var endDate = new Date(parseInt(20 + startTime.substr(0,2)),
	    		parseInt(startTime.substr(2,2)) - 1,
	    		parseInt(startTime.substr(4,2)),
	    		parseInt(startTime.substr(6,2)),
	    		parseInt(startTime.substr(8,2)),
	    		parseInt(startTime.substr(10,2)));
	    
	    var cc = document.getElementById(divname);
	    
	    var leftTime = endDate.getTime() - now.getTime(); 
	    if (leftTime > 0){
		    var leftsecond = parseInt(leftTime/1000); 
		    var day1 = Math.floor(leftsecond / 86400); 
		    var hour = Math.floor((leftsecond - day1*86400)/3600); 
		    var minute = Math.floor((leftsecond - day1*86400 - hour*3600)/60); 
		    var second = Math.floor(leftsecond - day1*86400 - hour*3600 - minute*60); 
		    
		    cc.innerHTML = "开赛倒记: " + day1 + "天 " 
		    + (hour > 9 ? hour : "0" + hour) + ":" 
		    + (minute > 9 ? minute : "0" + minute) + ":" 
		    + (second > 9 ? second : "0" + second); 
	    } else {
	    	window.clearInterval(intervalID);
	    	var endDateStr = endDate.format("yyyy/MM/dd hh:mm"); 
	    	cc.innerHTML = "开赛于: " + endDateStr;
	    }
    }
    
    Date.prototype.format = function(format){ 
    	var o = { 
	    	"M+" : this.getMonth()+1, //month 
	    	"d+" : this.getDate(), //day 
	    	"h+" : this.getHours(), //hour 
	    	"m+" : this.getMinutes(), //minute 
	    	"s+" : this.getSeconds(), //second 
	    	"q+" : Math.floor((this.getMonth()+3)/3), //quarter 
	    	"S" : this.getMilliseconds() //millisecond 
    	} 
    	if(/(y+)/.test(format)) { 
    		format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
    	} 
    	for(var k in o) { 
    		if(new RegExp("("+ k +")").test(format)) { 
    			format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
    		} 
    	} 
    	return format; 
    }
    
    function orderStakeCode(stakeCode){
    	$(".stakeCode").removeClass("c_default");
    	$("#"+stakeCode).addClass("c_default");
    	
    	var price = $("#"+stakeCode+"_price").val();
    	var fee = $("#"+stakeCode+"_fee").val();
    	var num = $("#num").val();
    	
    	var stakeAmount = parseFloat(price) * parseInt(num);
    	var stakeFee = parseFloat(fee) * parseInt(num);
    	var totalAmount = stakeAmount + stakeFee;
    	
    	var cleanAmount = odds_scale * stakeAmount;
    	
    	// 传给后台
    	$("#stakeCode").val(stakeCode);
    	$("#stakeAmount").val(stakeAmount.toFixed(2));
    	$("#stakeFee").val(stakeFee.toFixed(2));
    	$("#totalAmount").val(totalAmount);
    	
    	// 显示客户
    	$("#stakeAmount_c").html(stakeAmount.toFixed(2));
    	$("#stakeFee_c").html(stakeFee.toFixed(2));
    	$("#totalAmount_c").html(totalAmount.toFixed(2));
    	
    	// 按钮
    	$("#clean_amount").html("赔率"+odds_scale.toFixed(2)+","+type_name+"兑" + cleanAmount.toFixed(2));
    }
    
    function orderStakeNum(num){
    	$(".stakeNum").removeClass("c_default");
    	$("#num_"+num).addClass("c_default");
    	
    	var stakeCode = $("#stakeCode").val();
    	var price = $("#"+stakeCode+"_price").val();
    	var fee = $("#"+stakeCode+"_fee").val();
    	
    	var stakeAmount = parseFloat(price) * parseInt(num);
    	var stakeFee = parseFloat(fee) * parseInt(num);
    	var totalAmount = stakeAmount + stakeFee;
    	
    	var cleanAmount = odds_scale * stakeAmount;

    	// 传给后台
    	$("#num").val(num);
    	$("#stakeAmount").val(stakeAmount.toFixed(2));
    	$("#stakeFee").val(stakeFee.toFixed(2));
    	$("#totalAmount").val(totalAmount);
    	
    	// 显示客户
    	$("#stakeAmount_c").html(stakeAmount.toFixed(2));
    	$("#stakeFee_c").html(stakeFee.toFixed(2));
    	$("#totalAmount_c").html(totalAmount.toFixed(2));
    	
    	// 按钮
    	$("#clean_amount").html("赔率"+odds_scale.toFixed(2)+","+type_name+"兑" + cleanAmount.toFixed(2));
    }
    
    function orderStake(){
    	var scheduleId = $("#scheduleId").val();
    	var stakeType = $("#stakeType").val();
    	var stakeCode = $("#stakeCode").val();
    	var num = $("#num").val();
    	var stakeAmount = $("#stakeAmount").val();
    	var stakeFee = $("#stakeFee").val();
    	var totalAmount = $("#totalAmount").val();
    	
    	// 余额
    	var balance = $("#balance").text();
    	if (parseFloat(totalAmount) >= parseFloat(balance)){
    		$.toptip('余额不足', 'error');
    	} 
    	else {
    		$.confirm("确定下注吗？", 
    			function() {
    			  //点击确认后的回调函数
    		    	$.ajax({
    		    		type: "post",
    		    		url: "order/stake",
    		    		data: {"scheduleId":scheduleId,"stakeType":stakeType,"stakeCode":stakeCode,"orderNum":num,"totalFee":stakeFee,"stakeAmount":stakeAmount,"totalAmount":totalAmount,"oddsScale":odds_scale},
    		    		dataType: "json",
    		    		success: function (data){
    		    			if(data.code != 1){
    		    				$.toptip(data.msg, 'error');
    		    			}
    		    			else {
    		    				$.toast("下单成功", function() {
    		    					window.location.href = "index";
    		    				});
    		    			}
    		    		}
    		    	});    			  
    			}, 
    			function() {
    			  //点击取消后的回调函数
    			}
    		);
    	}
    }
    
    function draw(id){
		$.confirm("确定领取吗？", 
   			function() {
   			  //点击确认后的回调函数
   		    	$.ajax({
   		    		type: "get",
   		    		url: "order/draw/" + id,
   		    		dataType: "json",
   		    		success: function (data){
   		    			if(data.code != 1){
   		    				$.toptip(data.msg, 'error');
   		    			}
   		    			else {
   		    				$.toast("领取成功", function() {
   		    					window.location.href = "index";
   		    				});
   		    			}
   		    		}
   		    	});    			  
   			}, 
   			function() {
   			  //点击取消后的回调函数
   			}
   		);    	
    }
    
    function orderDetail(id){
    	$.ajax({
    		type: "get",
    		url: "order/detail/" + id,
    		dateType: "json",
    		success: function(data){
    			if (data != null){
    				$("#od_orderNo").html(data.orderNo);
    				$("#od_createTime").html(formatDate(new Date(data.createTime)));
    				$("#od_stakeType").html(data.stakeType == '1' ? '胜' : data.stakeType == '2' ? '平' : data.stakeType == '3' ? '负' : data.stakeType);
    				$("#od_stakePrice").html(data.stakePrice);
    				$("#od_orderNum").html(data.orderNum);
    				$("#od_fee").html((data.tradeFee + data.memberFee).toFixed(2));
    				$("#od_stakeAmount").html(data.stakeAmount.toFixed(2));
    				$("#od_oddsScale").html(data.oddsScale.toFixed(2));
    				$("#od_preCleanAmount").html(data.preCleanAmount.toFixed(2));
    				
    				$("#weui_popup__container").popup();
    			} else {
    				$.toast('非法操作', 'forbidden');
    			}
    		},
    		error: function(){
    			$.toast('网络异常', 'forbidden');
    		},
    	});
    }

    function formatDate(now) {
    	var year = now.getFullYear();
    	var month = now.getMonth() + 1;
    	var date = now.getDate();
    	var hour = now.getHours();
    	var minute = now.getMinutes();
    	var second = now.getSeconds();
    	return year+"/"+month+"/"+date+" "+(hour > 9 ? hour : "0" + hour)+":"+(minute > 9 ? minute : "0" + minute)+":"+(second > 9 ? second : "0" + second); 
    }
</script>

</html>