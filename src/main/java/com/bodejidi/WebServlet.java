package com.bodejidi;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class WebServlet extends HttpServlet 
{
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,java.io.IOException 
  {
    String firstName = req.getParameter("first_name");
    String lastName = req.getParameter("last_name");
    
    try
    {
      
    } 
  }
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,java.io.IOException
  {
    String firstName = req.getParameter("first_name");
    String lastName = req.getParameter("last_name");
    Connection conn = null;
    Statement stmt = null;
    
    try
    {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
    }
    catch(Exception ex)
    {
      //ignore;
    }
    
    try
    {
      conn = 
              DriverManager.getConnection("jdbc:mysql://localhost/test?"
                                            + "user=root"
                                            + "&password=");
      stmt = conn.createStatement();
      String sql = "INSERT INTO member(first_name,last_name,created_date,last_updated)"
                 + "VALUES('"+ firstName + "','" + lastName + "',now() ,now())";
      System.out.println("SQL: " + sql);
      stmt.execute(sql);
      resp.getWriter().println("Add " + firstName + " " + lastName + " success!");
    }
    catch(SQLException ex)
    {
       System.out.println("SQLException:" + ex.getMessage());
       System.out.println("SQLState: " + ex.getSQLState());
       System.out.println("VendorError: " + ex.getErrorCode());
       resp.getWriter().println("Error!");
    }    
    finally
    {
      if (stmt != null)
      {
        try
        {
          stmt.close();
        }
        catch (SQLException sqlEx)
        {
          //ignore;
        }
      }
        if (conn != null)
      {
        try
        {
          conn.close();
        }
        catch (SQLException sqlEx)
        {
          //ignore;
        }
      }
    }
  }
}