package ua.kapitonenko.taglib;

import org.apache.log4j.Logger;
import ua.kapitonenko.config.Application;
import ua.kapitonenko.config.keys.Keys;
import ua.kapitonenko.domain.entities.User;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class AccessTag extends TagSupport {
	private static final Logger LOGGER = Logger.getLogger(AccessTag.class);
	
	private String route;
	
	public void setRoute(String route) {
		this.route = route;
	}
	
	@Override
	public int doStartTag() throws JspException {
		HttpSession session = pageContext.getSession();
		User user = (User) session.getAttribute(Keys.USER);
		
		if (user != null && !Application.allowed(user.getUserRoleId(), route)) {
			LOGGER.debug(user.getUserRoleId() + " " + route);
			return SKIP_BODY;
		}
		
		return EVAL_BODY_INCLUDE;
		
		
/*		if (user != null && Application.allowed(user.getUserRoleId(), route))) {
			return EVAL_BODY_INCLUDE;
		}
		
		return SKIP_BODY;*/
	}
}
