package cn.wolfcode.web.commons.components.tags;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Objects;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/5/8 10:30
 * @email chenshiyun2011@163.com
 */
public class GrantTag extends TagSupport {

    private PageContext pageContext;

    private String grants;

    @Override
    public void setPageContext(PageContext pageContext) {
        this.pageContext = pageContext;
    }

    public void setGrants(String grants) {
        this.grants = grants;
    }

    @Override
    public int doStartTag() throws JspException {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        boolean isIncludeBod = Objects.nonNull(user)
                && CollectionUtils.isNotEmpty(user.getAuthorities())
                && user.getAuthorities().stream().anyMatch(t -> t.getAuthority().equals(grants));

        return isIncludeBod ? BodyTagSupport.EVAL_BODY_INCLUDE : BodyTagSupport.SKIP_BODY;
    }
}
