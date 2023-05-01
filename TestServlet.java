import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import jakarta.servlet.http.Part;
import jakarta.servlet.annotation.MultipartConfig;
import java.util.Date;
import java.text.SimpleDateFormat;

@MultipartConfig
public class TestServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
         Date currentDate = new Date();
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
         String formattedDate = dateFormat.format(currentDate);
        
        
        String url = "jdbc:mysql://localhost/test"; // replace "mydatabase" with your actual database name
        String user = "root"; // replace "root" with your actual database username
        String password = "root"; // replace "password" with your actual database password
        
        
      // replace "email" with the name of your input field
      
      
        String what = request.getParameter("what");
        String rdesc = request.getParameter("rdesc");
        
        String subname = request.getParameter("subname");

      
        String sub = request.getParameter("subject");
        String des = request.getParameter("desc");
        String type = request.getParameter("type");
        
        String phead = request.getParameter("phead");
        String plink = request.getParameter("plink");
        if("".equals(plink)){
            plink="0";
        }
        String pdesc = request.getParameter("pdesc");
       
           
        HttpSession session = request.getSession();
        String userid = (String) session.getAttribute("userid");
        String username = (String) session.getAttribute("username");
        
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            String operation = request.getParameter("operation");
            if (operation == null) {
                out.println("null");
            }else if (operation.equals("insert")) {
                 Part filePart = request.getPart("file");
                    String fileName = filePart.getSubmittedFileName();
                    InputStream fileContent = filePart.getInputStream();

            String sql = "INSERT INTO users (`name`, `content`, `author`, `subject`, `desc`, `date`,`type` ) VALUES (?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement statement = conn.prepareStatement(sql)) {
                    statement.setString(1, fileName);
                    statement.setBlob(2, fileContent);
                    statement.setString(3, userid);
                    statement.setString(4, sub);
                    statement.setString(5, des);
                    statement.setString(6, formattedDate);
                    statement.setString(7, type);
                    
                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        
                        out.println("Data inserted successfully!");
                       
                        out.println("<button onclick=\"window.parent.document.querySelector('.popup').remove()\">Close</button>");
                        
                    }   }
            conn.close();
            }else if (operation.equals("insertpost")) {
               

                
                 
            String sql = "INSERT INTO posts (`headline`,`links`, `pdesc`, `currdate`,`by`) VALUES (?,?, ?, ?,?)";
                try (PreparedStatement statement = conn.prepareStatement(sql)) {
                    statement.setString(1, phead);
                    statement.setString(2, plink);
                    statement.setString(3, pdesc);
                    statement.setString(4, formattedDate);
                    statement.setString(5, userid);
                    
                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        
                        out.println("Data inserted successfully!");
                       
                        out.println("<button onclick=\"window.parent.document.querySelector('.popup').remove()\">Close</button>");
                        
                    }   }
            conn.close();
            }else if(operation.equals("request")){
                
                
                 
            String sql = "INSERT INTO requests (`requestfor`,`rdesc`, `currdate`,`requestby`) VALUES (?,?,?,?)";
                try (PreparedStatement statement = conn.prepareStatement(sql)) {
                    statement.setString(1, what);
                    statement.setString(2, rdesc );
                    statement.setString(3, formattedDate);
                    statement.setString(4, userid);
                    
                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        
                        out.println("Requested successfully!");
                        
                        session.setAttribute("recordAdded", true);
                        
                        out.println("<button onclick=\"window.parent.location.reload(); window.close();\">Close</button>");

                      

                         }   }
            conn.close();
            }else if(operation.equals("subjectinsert")){
                 String sql = "INSERT INTO subjects (`subname`,`date`,`by`) VALUES (?,?,?)";
                try (PreparedStatement statement = conn.prepareStatement(sql)) {
                    statement.setString(1, subname);
                   
                    statement.setString(2, formattedDate);
                    statement.setString(3, userid);
                    
                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        
                        out.println("subject added successfully!");
                        
                      
                        
                        out.println("<button onclick=\"window.parent.location.reload(); window.close();\">Close</button>");

                      

                         }   }
            conn.close();
        } else if(operation.equals("update")){
            Part filePart = request.getPart("file");
                    String fileName = filePart.getSubmittedFileName();
                    InputStream fileContent = filePart.getInputStream();
                    
        String upfid = request.getParameter("upfid");       
        String upfname = request.getParameter("upfname");
        String upfdesc = request.getParameter("upfdesc");
        String upftype = request.getParameter("upftype");
        String upfsubject = request.getParameter("upfsubject");
        
           byte[] bytes = fileContent.readAllBytes();

// Use PreparedStatement and set the byte array as a parameter
String sql = "update users SET `name`=?, content=?, `desc`=?, `type`=?, `subject`=? where `id`=?";
try (PreparedStatement statement = conn.prepareStatement(sql)) {
    statement.setString(1, upfname);
    statement.setBytes(2, bytes);
    statement.setString(3, upfdesc);
    statement.setString(4, upftype);
    statement.setString(5, upfsubject);
    statement.setString(6, upfid);
    
    int rowsUpdated = statement.executeUpdate();
    if (rowsUpdated > 0) {
        out.println("subject updated successfully!");
    }
}}
        } catch (ClassNotFoundException |   SQLException e) {
            out.println("Error: " + e.getMessage());
        }
    }
   


  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String url = "jdbc:mysql://localhost/test"; // replace "mydatabase" with your actual database name
        String user = "root"; // replace "root" with your actual database username
        String password = "root"; // replace "password" with your actual database password
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            String operation = request.getParameter("operation");
            if (operation == null) {
                out.println("null");
            }else if (operation.equals("delete")) {
                int id = Integer.parseInt(request.getParameter("id"));
                String sql = "DELETE FROM users WHERE id=?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, id);
                
                
                int rowsAffected = statement.executeUpdate();
//                out.println(rowsAffected + " row(s) deleted.");
                response.sendRedirect("profile?row(s) deleted.");
                          
                    }
            conn.close();
            
        } catch (ClassNotFoundException |   SQLException e) {
            out.println("Error: " + e.getMessage());
        }
}
}
