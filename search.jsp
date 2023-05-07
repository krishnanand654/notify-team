<%-- 
    Document   : search
    Created on : May 6, 2023, 9:48:49 PM
    Author     : krish
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, java.util.*" %>
<%@ include file="/navbar.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
       
    </head>
    <body class='folder-body'>
        
        <%
            String subjectName = request.getParameter("search");
            
            String dbUrl = "jdbc:mysql://localhost:3306/test";
            String dbUser = "root";
            String dbPassword = "root";
            String query = "select * from users u, login l where u.author = l.id and u.subject LIKE'"+subjectName+"%'";

            try {
              // Connect to the database and execute the query
              Class.forName("com.mysql.jdbc.Driver");
              Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
              Statement stmt = conn.createStatement();


              if(conn == null){
               out.println("not connected");
              }

              ResultSet rs = stmt.executeQuery(query);
              if (rs.next()) {
              do{
                  String subject = rs.getString("subject");
                  String by = rs.getString("username");     
                  String date = rs.getString("date");
                  String mod = rs.getString("module");
                  String type = rs.getString("type");

                      %>
                      
                      <div class='folder'>
                  <h1><%=subject%></h1>
                  <p><%=date%></p>
                  <p><%=by%></p>
                  <p><%=mod%></p>
                  <p><%=type%></p>
                  </div>

                <%
                }while (rs.next());
                }else{
                    out.print("no data");
                }
                rs.close();
                stmt.close();
                conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                %>
    </body>
</html>
