package ua.kapitonenko.app.controller;

import org.apache.log4j.Logger;
import ua.kapitonenko.app.controller.commands.ActionCommand;
import ua.kapitonenko.app.controller.helpers.RequestHelper;
import ua.kapitonenko.app.controller.helpers.RequestWrapper;
import ua.kapitonenko.app.controller.helpers.ResponseParams;
import ua.kapitonenko.app.exceptions.ForbiddenException;
import ua.kapitonenko.app.exceptions.MethodNotAllowedException;
import ua.kapitonenko.app.exceptions.NotFoundException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {
	private static final Logger LOGGER = Logger.getLogger(Controller.class);
	private RequestHelper requestHelper = RequestHelper.getInstance();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RequestWrapper requestWrapper = new RequestWrapper(request);
		
		try {
			ActionCommand command = requestHelper.getCommand(requestWrapper);
			ResponseParams result = command.execute(requestWrapper);
			
			if (result.isRedirect()) {
				response.sendRedirect(result.getUri());
			} else {
				dispatch(request, response, result.getUri());
			}
			
		} catch (NotFoundException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
		} catch (MethodNotAllowedException e) {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, e.getMessage());
		} catch (ForbiddenException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
		} catch (ServletException | IOException e) {
			e.printStackTrace();
			// TODO add exception handling
		}
	}
	
	private void dispatch(HttpServletRequest request, HttpServletResponse response, String page)
			throws javax.servlet.ServletException, java.io.IOException {
		RequestDispatcher dispatcher =
				getServletContext().getRequestDispatcher(page);
		dispatcher.forward(request, response);
	}
}