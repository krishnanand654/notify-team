<!DOCTYPE html>
<!--
Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Html.html to edit this template
-->
<%@ page import="java.sql.*, java.util.*" %>
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="style.css"/>
    </head>
    
    
    


    
    
    <body>
        
        <%
  String dbUrl = "jdbc:mysql://localhost:3306/test";
  String dbUser = "root";
  String dbPassword = "root";
  String query = "SELECT * FROM subjects";
  
  
   

  try {
    // Connect to the database and execute the query
    Class.forName("com.mysql.jdbc.Driver");
    Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery(query);
    if(conn == null){
     out.println("not connected");
    }
    
    // Output the YouTube video embed code for each video link
     
    %>
    
    
        
        
        <form action ="TestServlet" method="POST" enctype="multipart/form-data">
            <!--<input type="hidden" name="operation" value="insert">-->
            <label>subject</label>
                       
            <select name="subject" >
                 <%
    while (rs.next()) {
    String subname = rs.getString("subname");
    %>
                <option value="<%=subname%>"><%=subname%></option>
              
                <%}%>
              </select>
            
            <label>description</label>
            <input type="text" name="desc"/>
            <div id="drop-zone">Drop files here</div>
            <input type="file" name="file" id="file-input"/>
            
            
            <div>
                <label>select type</label>
                <select name='type'>
                    <option value="note">Note</option>
                <option value="assignment">Assignment</option>
                </select>
            </div>
            
            <input type="submit" name="operation" value="insert"/>
            
             

        </form>
        
     <button onclick="window.parent.document.querySelector('.popup').remove()">Close</button>

    </body>
    <script>
                // Get the drop zone and file input elements
                
        var dropZone = document.getElementById('drop-zone');
        var fileInput = document.getElementById('file-input');

        // Add event listeners for drag and drop events
        dropZone.addEventListener('dragover', handleDragOver);
        dropZone.addEventListener('drop', handleDrop);

        // Define the handleDragOver function
        function handleDragOver(e) {
          e.preventDefault();
          dropZone.classList.add('dragover');
        }

        // Define the handleDrop function
        function handleDrop(e) {
          e.preventDefault();
          dropZone.classList.remove('dragover');
          var files = e.dataTransfer.files;
          fileInput.files = files;
        }
        

        </script>
           <%
    

    // Close the database connection
    rs.close();
    stmt.close();
    conn.close();
  } catch (ClassNotFoundException e) {
    e.printStackTrace();
  }
%>
</html>
