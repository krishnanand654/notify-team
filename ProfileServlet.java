
import java.io.*;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.RequestDispatcher;


public class ProfileServlet extends HttpServlet
{
   
    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType ("text/html");
        RequestDispatcher sidebar = request.getRequestDispatcher("sidebar.jsp");
        sidebar.include(request, response);
        PrintWriter out = response.getWriter ();
     

        try
        {
            Class.forName ("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection ("jdbc:mysql://localhost:3306/test", "root", "root");
            
             HttpSession session = request.getSession();
            String userid = (String) session.getAttribute("userid");
             String username = (String) session.getAttribute("username");
            PreparedStatement ps =con.prepareStatement ("select * from users u, login l where u.author=l.id and l.id="+userid);
         
           
            out.println("<html><head><link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css\" integrity=\"sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65\" crossorigin=\"anonymous\"></head><body>");
            // Use the username to display a personalized message or perform other actions
           if(userid !=null) {
                
            out.println("<div class='home-container'>");
            out.print ("<table class='table table-bordered' width=50% border=1>");
           

            ResultSet rs = ps.executeQuery ();

            /* Printing column names */
            out.print ("</br></br>");
           
            out.print ("<tr>");
           
            out.print ("<th>name</th>");
                          
                          

         
            out.print ("</tr>");

            /* Printing result */
            while (rs.next ())
         {
            
             out.print ( "</td><td>" +  rs.getString (2) + " </td>"+"<td>" +  rs.getString (5) + " </td><td>" +"<td><a href= download?id="+ rs.getInt(1)  +">view</a></td>"+" </td><td>" +"<td><a href= download?id="+ rs.getInt(1)  +"&operation=download>Download</a></td>"+"<td><a href= delete?id="+ rs.getInt(1)  +"&operation=delete>Delete</a></td>"+"<td><a href= edit.jsp?id="+ rs.getInt(1)  +"&operation=edit>edit</a></td></tr>");
         }
            out.print ("</table></div>");
            out.print("<p>"+request.getParameter("message")+"</p>"  );
          }else{
               out.print("");
           }
           
           out.println("</body></html>");
        }
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