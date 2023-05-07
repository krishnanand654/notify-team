<!DOCTYPE html>
<!--
Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Html.html to edit this template
-->
<html>
    <head>
        <link rel="stylesheet" href="style.css"/>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
       <div class="navbar-container">
           <div>
               
                 <% if(session.getAttribute("username") != null) { 
            
            String navname = (String) session.getAttribute("username");
            String navnameSubstring = navname.substring(0,1);
            String navlogocap = navnameSubstring.toUpperCase();
            String navcap = navname.substring(0, 1).toUpperCase() + navname.substring(1);
            %>
             
            <div>
            <p class="sidebar-item"><span class="profile-logo"><%= navlogocap%></span><%= navcap%>'s Notify</p>
           <%}%>
            
            </div>
               
               
           </div>
           <div>
               <form action='search.jsp' method='POST'>
                    <input type='text' name="search" class='search' placeholder='Search' required  autocomplete="off"/>
                    <Button class='btn search-btn'type='submit' name='operation' value='search'/><i class="fa-solid fa-magnifying-glass" style="color:#73797f;"></i></Button>
               </form>
           </div>
  <div class='mag-ctn'>
              <div class='top-links'>
                    <a class='head-list' href="folder.jsp">Notes</a>
                     <form action="retrieve-files" method="post">
                        <input type="hidden" name="type" value="assignment" />
                       <button class='head-list' type="submit">Assignment</button>
                     </form>
                    <a class='head-list' href="requestbar.jsp">Requests</a>
                </div>
                 
              
              </div>
</div>
<%          RequestDispatcher sidebar = request.getRequestDispatcher("sidebar.jsp");
            sidebar.include(request, response);
%>


    </body>
</html>
