package com.mcharopkin.server;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import org.json.*;


public class MainServlet extends HttpServlet {
	

	@Override
	public void init() throws ServletException {
		super.init();
	}


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		
		resp.addHeader("Access-Control-Allow-Origin", "192.168.122.1:8080");
		resp.addHeader("Access-Control-Allow_Methods", "*");
		resp.setCharacterEncoding("UTF-8");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
	
		resp.addHeader("Access-Control-Allow-Origin", "192.168.122.1:8080");
		resp.addHeader("Access-Control-Allow_Methods", "*");

		String msg = req.getParameter("msg");
		try {
			JSONObject respMessage = new JSONObject();
			respMessage.put("msg", msg);

			PrintWriter writer = resp.getWriter();
		
		        writer.print(respMessage);
		        writer.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
	         
		resp.addHeader("Access-Control-Allow-Origin", "192.168.122.1:8080");
		resp.addHeader("Access-Control-Allow_Methods", "PUT, DELETE, POST, GET, OPTIONS");
	}

	@Override
	public void destroy() {
		super.destroy();
	}
}

