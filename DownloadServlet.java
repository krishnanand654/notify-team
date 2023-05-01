/**
 *
 * @author krish
 * 
 **/

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Blob;
import jakarta.servlet.ServletOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import java.io.PrintWriter;


public class DownloadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
            
            
            String sql = "SELECT * FROM users WHERE id = ?";
            
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Retrieve the blob data from the result set
                
                
                InputStream is = resultSet.getBinaryStream("content");
                String fileName = resultSet.getString("name");
//               long fileSize = resultSet.getLong("size");
                String operation = request.getParameter("operation");
                if (operation == null) {
                
            }else if (operation.equals("download")) {
                response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
                response.setContentType(getServletContext().getMimeType(fileName));
//                response.setContentLengthLong(fileSize);
            }

                // Get the output stream for the response
                OutputStream os = response.getOutputStream();

                // Copy the blob data to the output stream
                byte[] buffer = new byte[4096];
                int bytesRead = -1;
                while ((bytesRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }

                // Flush and close the output stream
                os.flush();
                os.close();
            } else {
                response.getOutputStream().write("File not found".getBytes());
            }

            // Close the result set, statement, and connection
            resultSet.close();
            statement.close();
            conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
