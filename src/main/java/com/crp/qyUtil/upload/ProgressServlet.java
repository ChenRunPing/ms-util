package com.crp.qyUtil.upload;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ProgressServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		FileUploadStatus status = (FileUploadStatus) session.getAttribute("status");
		try {
			response.reset();
			response.getWriter().write("{\"pBytesRead\":"
					+status.getPBytesRead()+",\"pContentLength\":"+status.getPContentLength()+"}");
		System.out.println("{\"pBytesRead\":"
				+status.getPBytesRead()+",\"pContentLength\":"+status.getPContentLength()+"}");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		this.doPost(request,response);
		
	}
}
