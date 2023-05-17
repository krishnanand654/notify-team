
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

@MultipartConfig
public class InsertServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
         Date currentDate = new Date();
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
         String formattedDate = dateFormat.format(currentDate);
        
        
        String url = "jdbc:mysql://localhost/test"; 
        String user = "root"; 
        String password = "root";      
      
        String what = request.getParameter("what");
        String rdesc = request.getParameter("rdesc");
        
        String subname = request.getParameter("subname");

      
        String sub = request.getParameter("subject");
        String des = request.getParameter("desc");
        String type = request.getParameter("type");
        String mod = request.getParameter("mod");
        
        String phead = request.getParameter("phead");
        String plink = request.getParameter("plink");
        if("".equals(plink)){
            plink="null";
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
                        
                         RequestDispatcher successbox = request.getRequestDispatcher("test.html");
                        successbox.include(request, response);    
                    }   }
            conn.close();
            }
        } catch (ClassNotFoundException |   SQLException e) {
            out.println("Error: " + e.getMessage());
        }
    }
   


  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String url = "jdbc:mysql://localhost/test"; 
        String user = "root";
        String password = "root"; 
        
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
                response.sendRedirect("profile?row(s) deleted.");                         
                    }
            conn.close();
            
        } catch (ClassNotFoundException |   SQLException e) {
            out.println("Error: " + e.getMessage());
        }
}
}
