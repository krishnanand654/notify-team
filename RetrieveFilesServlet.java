///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
// */
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.annotation.WebServlet;
//import java.io.File;
//import java.util.List;
//import java.sql.DriverManager;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.Blob;
//import java.io.ByteArrayInputStream;
//import jakarta.servlet.jsp.JspWriter;
//
//
//
//
///**
// *
// * @author krish
// */
//@WebServlet("/retrieve-files")
//public class RetrieveFilesServlet extends HttpServlet {
//
//    
//    
//    
//    
//    
//    public class ByteArrayFile extends File {
//    private final ByteArrayInputStream byteArrayInputStream;
//
//    public ByteArrayFile(String name, ByteArrayInputStream byteArrayInputStream) {
//        super(name);
//        this.byteArrayInputStream = byteArrayInputStream;
//    }
//
//    public ByteArrayInputStream getByteArrayInputStream() {
//        return byteArrayInputStream;
//    }
//}
//    
//    
//    
//    
//    
//    private static final long serialVersionUID = 1L;
//    private final String url = "jdbc:mysql://localhost/test";
//    private final String user = "root";
//    private final String password = "root";
//    
//    
//    
//    
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        PrintWriter out = response.getWriter();
//        List<File> files = new ArrayList<>();
//        try{
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection conn = DriverManager.getConnection(url, user, password);
//            out.println("connected");
//            String sql = "SELECT * FROM users";
//            PreparedStatement statement = conn.prepareStatement(sql);
//            ResultSet resultSet = statement.executeQuery();
//            
//            while (resultSet.next()) {
//                int id = resultSet.getInt("id");
//                String name = resultSet.getString("name");
//                Blob content = resultSet.getBlob("content");
//                if (content != null) {
//                     byte[] bytes = content.getBytes(1, (int) content.length());
//                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
//                ByteArrayFile file = new ByteArrayFile(name, byteArrayInputStream);
//                files.add(file);
//                }
//            }
//            
//            statement.close();
//            conn.close();
//        }catch(ClassNotFoundException | SQLException e){
//            
//        }
//         request.setAttribute("files", files);
//          request.getRequestDispatcher("files.jsp").forward(request, response);
//    }
//
//
//}


import java.io.*;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.RequestDispatcher;
import java.net.*;

@WebServlet("/retrieve-files")
public class RetrieveFilesServlet extends HttpServlet
{
   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
      
        
        PrintWriter out = response.getWriter ();
        out.print("<html><head><link rel='stylesheet' type='text/css' href='style.css'></head><body class='split'>");
        
        response.setContentType ("text/html");
        RequestDispatcher sidebar = request.getRequestDispatcher("sidebar.jsp");
        sidebar.include(request, response);
        
        String type = request.getParameter("type");
        String sub = request.getParameter("subject");
        
        try
        {
            Class.forName ("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection ("jdbc:mysql://localhost:3306/test", "root", "root");
            PreparedStatement ps ;
            if(sub != null){
                ps = con.prepareStatement ("select * from users u, login l where u.author = l.id and type='"+type+"' and subject='"+sub+"'");
            }else{
                ps = con.prepareStatement ("select * from users u, login l where u.author = l.id and type='"+type+"'");
            }
            
//         SELECT u.name, u.subject, u.author, l.username, u.desc, p.links, p.pdesc, p.by, p.currdate FROM users u INNER JOIN login l ON u.author = l.id LEFT JOIN posts p ON p.by = l.id ORDER BY COALESCE(p.currdate, u.currdate) DESC;
            HttpSession session = request.getSession();
            String userid = (String) session.getAttribute("userid");
//            String username = (String) session.getAttribute("username");

            // Use the username to display a personalized message or perform other actions
           if(userid !=null) {
            out.println("<div class='home-container'>");
            
            ResultSet rs = ps.executeQuery ();

            /* Printing column names */
     out.print("<div><img class='background' src='https://images.pexels.com/photos/305816/pexels-photo-305816.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940'/>");
         while (rs.next ())
         {
             out.print ( "<div class='card-flex'>"
                     + "<div class='card'> "
                     + "<div  class='card-con'>"
                     + "<div>"
                     + "<h3>"  +  rs.getString ("subject").substring(0, 1).toUpperCase() + rs.getString ("subject").substring(1) + "</h3>"
                             
                                     + "</div>"
                                     + "<div>"+
                     "<form action=\"view\" method=\"post\">\n" +
                        "    <input type=\"hidden\" name=\"id\" value=\"" + rs.getInt(1) + "\" />\n" +
                        "    <button class='arrow' type=\"submit\"><img src=\"https://img.icons8.com/ios-filled/256/circled-chevron-right.png\"/></button>\n" +
                        "</form>"

                             
                                     
                                     +" <i class=\"fa-solid fa-cloud-arrow-down\" style='margin:0 10px 0; color:rgba(25, 23, 17, 0.6)'></i></a>"
                                     + "</div>"
                                     + "</div>"
                                     + "</div>");
            
         }
         
        }else{
               out.print("");
           }}
        catch (ClassNotFoundException | SQLException e2)
        {
            e2.printStackTrace ();
        }
        finally
        {
            out.close ();
        }
    }
}