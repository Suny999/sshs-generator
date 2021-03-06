package com.scai.core.base.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.scai.core.message.Message;
import com.scai.core.util.UuidUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.scai.core.exception.BusinessException;

/**
 * @author Suny
 * @date 2017/9/11
 */
@RestController
public abstract class BaseController {

    protected Log logger = LogFactory.getLog(this.getClass());

    /**
     * 得到ModelAndView
     *
     * @return
     */
	/*public ModelAndView getModelAndView() {
		return new ModelAndView();
	}
*/
    /**
     * 得到request对象
     *
     * @return
     */
	/*public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request;
	}*/

    /**
     * 得到32位的uuid
     *
     * @return
     */
    public String get32UUID() {
        return UuidUtil.get32UUID();
    }

    public static void logBefore(Log logger, String interfaceName) {
        logger.info("");
        logger.info("start");
        logger.info(interfaceName);
    }

    public static void logAfter(Log logger) {
        logger.info("end");
        logger.info("");
    }

	/*protected HttpServletRequest getServletRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}*/

	/*protected ServletContext getServletContext() {
		HttpServletRequest request = getServletRequest();
		if (request != null) {
			return request.getSession().getServletContext();
		}

		return null;
	}*/

    /**
     * 基于@ExceptionHandler异常处理
     */
    @ExceptionHandler
    @ResponseBody
    public Map<String, Object> handleAndReturnData(Exception e) {
        logger.info("-------------------------错误信息处理开始---------------------------");
        logger.error(e.getMessage(),e);
        Map<String, Object> data = new HashMap<String, Object>(10);
        if (e instanceof BusinessException) {
            BusinessException ex = (BusinessException) e;
            data.put("code", ex.getCode());
            data.put("msg", e.getMessage());
        }else if (e instanceof SQLException){
            data.put("msg",Message.getMessage("QR0003") );
        }else {
            data.put("msg", Message.getMessage("SY0004"));
        }
        data.put("excetion", e.getClass().getSimpleName());
        return data;
    }
}
