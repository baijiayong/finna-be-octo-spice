package com.bodejidi;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class WebServlet extends HttpServlet 
{
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,java.io.IOException 
  {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try
    {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
    } 
    catch(Exception ex)
    {
      //ignore
    }
    
    try
    {
      conn = DriverManager.getConnection("jdbc:mysql://localhost/test?"
                                            + "user=root"
                                            + "&password=");
       
      stmt = conn.createStatement();
      String pid = req.getParameter("id");
      
      if (null == pid)
      {
        String sql = "SELECT * FROM member";
        System.out.println("SQL: " + sql);
        rs = stmt.executeQuery(sql);
        resp.getWriter().println("<html><head><title>Member List</title></head><body><h1>Member List<h1><table border=\"1\"><tr><td>ID</td><td>Name</td></tr>\n");
        while(rs.next()) {
                  Long id = rs.getLong("id");
                  String firstName = rs.getString("first_name");
                  String lastName = rs.getString("last_name");
                  resp.getWriter().println("<tr><td><a href=\"?id=" + id + "\">" + id + "</td><td>" + firstName + " " + lastName + "</td></tr>\n");
              }
        resp.getWriter().println("</table>");
        resp.getWriter().println("<p><a href=\".\">Add member</a></p>");
        resp.getWriter().println("</body></html>");
      }
      else 
      {
        String sql = "SELECT * FROM member";
        sql = sql + " WHERE id = " + pid;
        System.out.println("SQL: " + sql);
        rs = stmt.executeQuery(sql);
        resp.getWriter().println("<html><head><title>Member List</title></head><body><h1>Member</h1><table border=\"1\">\n");
        while(rs.next())
        {
          long id  = rs.getLong("id");
          String firstName = rs.getString("first_name");
          String lastName = rs.getString("last_name");
          resp.getWriter().println("<tr><td>ID</td><td>" + id + "</td></tr><tr><td>firstName: </td><td>" + firstName + "</td><tr><td>lastName: </td><td>" + lastName + "</tr></td>");  
        }
        resp.getWriter().println("</table>");
        resp.getWriter().println("<p><a href= \".\">Add Member</a></p>");
        resp.getWriter().println("</body>");
        resp.getWriter().println("</html>");
      }
    }
    catch(SQLException ex)
    {
      System.out.println("SQLException: " + ex.getMessage());
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
        if (rs != null)
        {
          try
          {
            rs.close();
          }
          catch(SQLException sqlEx)
          {
          //ignore;
          }
        }
      
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