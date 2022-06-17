package cn.wolfcode.web.commons.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public abstract class WebUtil {

    public static Map<String, Object> getParamMap(HttpServletRequest request) {
        Map<String, String[]> requestMap = request.getParameterMap();
        return getParamMap(requestMap);
    }

    public static Map<String, Object> getParamMap(Map<String, String[]> requestMap) {
        Map<String, Object> paramMap = new HashMap<>();
        for (Map.Entry<String, String[]> ent : requestMap.entrySet()) {
            String[] values = requestMap.get(ent.getKey());
            if (values != null) {
                paramMap.put(ent.getKey(), (values.length > 0 ? values[0] : null));
            }
        }
        return paramMap;
    }
}
