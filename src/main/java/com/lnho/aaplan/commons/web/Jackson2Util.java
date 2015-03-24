package com.lnho.aaplan.commons.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;


/**
 * Jackson2工具类
 * @Company : cyou
 * @author yangz
 * @date   2012-10-10 上午09:51:50
 */
public class Jackson2Util {
	private static final ObjectMapper mapper = new ObjectMapper();
    public static final String DEFAULT_CALLBACK = "callback";
	
	static {
    	mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    }
	/**
	 * 采用JSON格式输出结果对象
	 * @param result 结果对象
	 * @throws java.io.IOException
	 */
	public static void writeJson(HttpServletResponse response, Object result) {
		PrintWriter writer = null;
		response.setContentType("text/plain; charset=UTF-8");
		try {
			writer = response.getWriter();
			String json = toJson(result);
			writer.println(json);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			if(writer != null){
				writer.close();
			}
		}
	}


    /**
     * 转成jsonp格式输出
     * @param request
     * @param result
     * @return
     */
    public static  String convertToJsonpIfNeed(HttpServletRequest request ,HttpServletResponse response,  Object result) {
        StringBuilder jsonStr = new StringBuilder();
        //对于jsonp的请求做特殊处理
        boolean isJSONP = !StringUtils.hasText(request.getParameter(DEFAULT_CALLBACK));
        if(isJSONP){
            response.setContentType("application/x-javascript");
            jsonStr.append(request.getParameter(DEFAULT_CALLBACK)+"(");
        }
        jsonStr.append(toJson(result)) ;
        if(isJSONP){
            jsonStr.append(')');
        }

       return jsonStr.toString();
    }

    /**
     * 添加jsonp支持的writeJson
     * @param request
     * @param response
     * @param result
     */
    public static void writeJson(HttpServletRequest request, HttpServletResponse response, Object result) {
        PrintWriter writer = null;
        response.setContentType("text/plain; charset=UTF-8");
        try {
            writer = response.getWriter();
            String json = convertToJsonpIfNeed(request ,response , result);
            writer.println(json);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally{
            if(writer != null){
                writer.close();
            }
        }
    }

	/**
     * 将对象转化为Json格式字符串
     * @param obj
     * @return
	 * @throws java.io.IOException
	 * @throws com.fasterxml.jackson.databind.JsonMappingException
	 * @throws com.fasterxml.jackson.core.JsonGenerationException
     */
    public static String toJson(Object obj) {
    	try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
    
    /**
     * 将Json格式字符串转化为对象
     * @param <T>
     * @param json
     * @param requiredType
     * @return
     */
    public static <T> T toObject(String json, Class<T> requiredType) {
    	try {
    		return (T)mapper.readValue(json, requiredType);
    	}catch (Exception ex) {
    		throw new RuntimeException(ex.getMessage());
		}
    }
    
    /**
     * 将Json格式字符串转化为对象
     * @param <T>
     * @param json
     * @param type
     * @return
     */
    @SuppressWarnings("unchecked")
	public static <T> T toObject(String json, TypeReference<T> type) {
    	try {
    		return (T)mapper.readValue(json, type);
    	}catch (Exception ex) {
    		throw new RuntimeException(ex.getMessage());
		}
    }
  
}
