package ua.kapitonenko.app.controller.helpers;

import org.apache.log4j.Logger;
import ua.kapitonenko.app.config.Application;
import ua.kapitonenko.app.config.keys.Actions;
import ua.kapitonenko.app.controller.commands.*;
import ua.kapitonenko.app.domain.records.User;
import ua.kapitonenko.app.exceptions.ForbiddenException;
import ua.kapitonenko.app.exceptions.NotFoundException;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;

public class RequestHelper {
	private static final Logger LOGGER = Logger.getLogger(RequestHelper.class);
	private static RequestHelper instance = new RequestHelper();
	private HashMap<String, ActionCommand> commands = new HashMap<>();
	
	private RequestHelper() {
		initCommands();
	}
	
	public static RequestHelper getInstance() {
		return instance;
	}
	
	private void initCommands() {
		commands.put(Actions.LOGIN, new LoginAction());
		commands.put(Actions.LOGOUT, new LogoutAction());
		commands.put(Actions.HOME, new HomeAction());
		commands.put(Actions.SIGNUP, new SignUpAction());
		commands.put(Actions.LANGUAGE, new LanguageAction());
		commands.put(Actions.PRODUCTS, new ProductListAction());
		commands.put(Actions.PRODUCTS_CREATE, new ProductCreateAction());
		commands.put(Actions.PRODUCTS_ADD, new ProductAddAction());
		commands.put(Actions.PRODUCTS_DELETE, new ProductDeleteAction());
		commands.put(Actions.RECEIPT_CREATE, new ReceiptCreateAction());
		commands.put(Actions.RECEIPT_RETURN, new ReceiptReturnAction());
		commands.put(Actions.RECEIPT_CANCEL, new ReceiptCancelAction());
		commands.put(Actions.RECEIPTS, new ReceiptListAction());
		commands.put(Actions.REPORT_CREATE, new ReportCreateAction());
		commands.put(Actions.REPORTS, new ReportListAction());
		commands.put(Actions.REPORT_VIEW, new ReportViewAction());
		
	}
	
	public ActionCommand getCommand(RequestWrapper request) throws IOException, ServletException {
		String key = request.getUri();
		
		LOGGER.debug(request.getMethod() + ": " + key);
		LOGGER.debug(request.paramsToString());
		
		ActionCommand command = commands.get(key);
		
		if (command == null) {
			LOGGER.debug("command not found");
			throw new NotFoundException(key);
		}
		User user = request.getSession().getUser();
		
		if (user == null && !Application.guestAllowed(key)) {
			return commands.get(Actions.HOME);
		}
		
		if (user != null && !Application.allowed(user.getUserRoleId(), key)) {
			throw new ForbiddenException(key);
		}
		
		return command;
	}
	
}
