<%-- 
    Document   : vedio
    Created on : Apr 10, 2023, 12:48:44 AM
    Author     : krish
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, java.util.*,java.time.format.DateTimeFormatter,java.time.LocalDate" %>

 <html>
    
          <head>
              <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
               <link rel="stylesheet" href="style.css"/>
          </head>
          <body class="main" >
              <div >
              
              <%
  String dbUrl = "jdbc:mysql://localhost:3306/test";
  String dbUser = "root";
  String dbPassword = "root";
  String query = "SELECT * FROM posts p, login l where p.by = l.id order by currdate asc;";
  String nquery = "SELECT * FROM subjects";
  
   

  try {
    // Connect to the database and execute the query
    Class.forName("com.mysql.jdbc.Driver");
    Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    Statement stmt = conn.createStatement();
    
    ResultSet nrs = stmt.executeQuery(nquery);
    if(conn == null){
     out.println("not connected");
    }
    %>
    <div class="uploadfrm">
               <form action ="TestServlet" method="POST" enctype="multipart/form-data">
            <!--<input type="hidden" name="operation" value="insert">-->
           
                       
           
            
           
            <div class='top-upload'>
            <div id="drop-zone">Drop files here</div>
           
            <textarea class='desc-area' type="text" name="desc" placeholder="Share your knowledge"/></textarea>
               
               <div class='upload-flex'>
                   <div>
                        <label for="file-input">
                                <i class="fa-solid fa-paperclip" style='color:#A8A8A8;'"></i>
                        </label>
                        <input class='filefrm'type="file" name="file" id="file-input"/>



                     <select name="subject"class='select-btn' >
                          <option>select subject</option>
                         <%
                        while (nrs.next()) {
                        String subname = nrs.getString("subname");
                        %>
                        
                        <option value="<%=subname%>"><%=subname%></option>

                        <%}%>
                      </select >

                        <select name='type' class='select-btn'>
                                    <option selected >select type</option>
                            <option value="note">Note</option>
                        <option value="assignment">Assignment</option>
                        </select>
            </div>
              
                      <button class='btn' type="submit" name="operation" value="insert"/><i class="fa-solid fa-paper-plane fa-lg" style="color: #478bff;"></i></button>
               
            </div>
           </div> 
             

        </form>
            </div>  
              
              <!--<div><img class='background' src='https://mdbootstrap.com/img/new/textures/full/171.jpg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940'/> </div>-->
                  <div class="home-req-flex">
                  <div>
                  <div class='mag-ctn'>
                  <div class='top-links'>
                        <a class='head-list' href="folder.jsp">Notes</a>
                         <form action="retrieve-files" method="post">
                            <input type="hidden" name="type" value="assignment" />
                           <button class='head-list' type="submit">Assignment</button>
                         </form>
                        <a class='head-list' href="requestbar.jsp">Requests</a>
                    </div>
                      <% 
                            if (session.getAttribute("recordAdded") != null) { %>
                             
                            <div class="alert alert-success" role="alert">
                              New request available
                            </div>
                            <% session.removeAttribute("recordAdded"); %>
                          <% } %>
                  
                  
                  </div>

    <%
    
    // Output the YouTube video embed code for each video link
     
     RequestDispatcher sidebar = request.getRequestDispatcher("sidebar.jsp");
    sidebar.include(request, response);
     ResultSet rs = stmt.executeQuery(query);
    while (rs.next()) {
    String heading = rs.getString("headline");
     String desc = rs.getString("pdesc");     
     String date = rs.getString("currdate");
     String by = rs.getString("username");
     
       String dateStr = rs.getString("currdate");
    java.time.format.DateTimeFormatter formatter1 = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");
    java.time.format.DateTimeFormatter formatter2 = java.time.format.DateTimeFormatter.ofPattern("yyyy-MMM-dd");
    LocalDate date2 = LocalDate.parse(dateStr, formatter1);
    String formattedDate = formatter2.format(date2);






     
       String videoLink = rs.getString("links");
      String videoId = "";
      if (videoLink.contains("youtube.com")) {
        videoId = videoLink.substring(videoLink.indexOf("v=") + 2);
      } else if (videoLink.contains("youtu.be")) {
        videoId = videoLink.substring(videoLink.indexOf(".be/") + 4);
      }
      
      String BySubstring = by.substring(0,1);
            String byCap = BySubstring.toUpperCase();
           
            String usercap = by.substring(0, 1).toUpperCase() + by.substring(1);
            
      %>
      
      
     
    <div class='home-container1'>
        
     <%if(videoId != "" ){%>
        <div class="home-flex"> 
            
           <%}else{%> 
           <div class="home-flex2"> 
            
           <%}%>   
           <div>
                <div class='line2'></div>
               <p ><span class="profile-logo2"><%= byCap%></span><%=usercap%><span class="date"> <%= formattedDate%> </span></p>
                 
           </div>
               <%if(videoId != "" ){%>
               <div class="home-flex3">
                    <%}else{%> 
                    
                    <div>
                     <%}%> 
                     
                     <p class='post-desc'><%=desc%><br><br><span class='post-link'><%=videoLink%></span></p>
               
             <%if(videoId == "" ){%>
            <div class='myElement'>
                
              <iframe style='display:none' width="380" height="200" src="https://www.youtube.com/embed/<%= videoId %>" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
            </div>
             <%}else{%>
             <div class='myElement'>
                
              <iframe width="380" height="200" src="https://www.youtube.com/embed/<%= videoId %>" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
            </div>
            <%}%>
               </div>
               <div class='line3'></div>
    </div>
               
            
    </div>
    </div>
        <%
    }

    // Close the database connection
    rs.close();
    stmt.close();
    conn.close();
  } catch (Exception e) {
    e.printStackTrace();
  }
%>
    </div>
   
    </div>
                  </div>
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
</html>

<%--


<div class="head-desc">
                <div class='point-flex'>
                    
                    
                <%if(videoId == "" ){%>
                
                    <p class='point-icon'>::</p>
               <%}%>
               <h2><%=heading%></h2>
                </div>
                <div class='sub'>
            <p>On : <%=date%> </p>
            <p>By : <%=by%> </p>
            </div>
                
     
           <p><%=desc%> </p>
           

            </div>
              <%if(videoId == "" ){%>
            <div class='myElement'>
                
              <iframe style='display:none' width="380" height="210" src="https://www.youtube.com/embed/<%= videoId %>" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
            </div>
             <%}else{%>
             <div class='myElement'>
                
              <iframe width="380" height="210" src="https://www.youtube.com/embed/<%= videoId %>" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
            </div>
            <%}%>
         --%>  