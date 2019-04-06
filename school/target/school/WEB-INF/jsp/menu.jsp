<%-- <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- sidebar-->
<aside class="aside">
    <!-- START Sidebar (left)-->
    <div class="aside-inner">
        <nav data-sidebar-anyclick-close="" class="sidebar">
            <!-- START sidebar nav-->
            <ul class="nav" >
                <!-- Iterates over all sidebar items-->
                
                <!-- <li id="homemenuId">
                    <a href="home.html">
                        Home
                    </a>
                </li> -->
                
               
                <c:forEach items="${mainMenuList}" var="mainMenuListItem">
                <li id="programsmenuId">
                    <a href="javascript:void(0)" data-toggle="collapse">
                        ${mainMenuListItem.menu_name}
                    </a>
                    <ul class="nav sidebar-subnav collapse">
                      
                      <c:forEach items="${subMenuList}" var="subMenuListItem">  
                      
                      <c:if test="${subMenuListItem.parent_menu==mainMenuListItem.menu_id}">
                        <li id="challengesmenuId">
                            <a href="#">
                                <span> ${subMenuListItem.menu_name}</span>
                            </a>
                        </li>
                        </c:if>
                        
                       
                        
                        </c:forEach>
                        
                    </ul>
                </li>
                
                </c:forEach>
                
                <li id="homeMenuId">
                    <a href="home.html">
                       Home
                    </a>
                    </li>
                 <li id="classManagementMenuId">
                    <a href="classManagement.html">
                       Classes
                    </a>
                   </li>
                    <li id="studentManagementMenuId">
                    <a href="studentManagement.html">
                       Students
                    </a>
                   </li>
                   
                    <li id="attendanceManagementMenuId">
                    <a href="attendanceManagement.html">
                       Attendance
                    </a>
                   </li>
                   
                   <li id="eventManagementMenuId">
                    <a href="eventManagement.html">
                       Event
                    </a>
                   </li>
                    <!-- <li id="attendanceDisupdManagementId">
                    <a href="attendanceDisupdManagement.html">
                       AttendanceDis
                    </a>
                   </li> -->
                
               <!--  <li id="rewardsmenuId">
                    <a href="javascript:void(0)" data-toggle="collapse">
                       School
                    </a>
                    <ul class="nav sidebar-subnav collapse">
                        <li id="campaignmenuId">
                            <a href="#">
                                <span>school -1</span>
                            </a>
                        </li>
                        <li id="customitemsmenuId">
                            <a href="#">
                                <span>Test -1</span>
                            </a>
                        </li>
                        <li id="egiftcardmenuId">
                            <a href="#">
                                <span>Test -2</span>
                            </a>
                        </li>
                        <li><hr style="margin-bottom: 3px !important;margin-top: 3px !important;"/></li>
                        <li id="reimbursementrequestmenuId">
                            <a href="#">
                                <span>Test -3</span>
                            </a>
                        </li>
                        <li id="pointsummarymenuId">
                            <a href="#">
                                <span>Test -4</span>
                            </a>
                        </li>
                        <li id="ordersmenuId">
                            <a href="#">
                                <span>Test -5</span>
                            </a>
                        </li>
                        <li id="redeemmenuId">
                            <a href="#">
                                <span>Test -6</span>
                            </a>
                        </li>
                    </ul>
                </li>
                <li  id="reportsmenuId">
                    <a href="javascript:void(0)" data-toggle="collapse">
                        Reports
                    </a>
                    <ul class="nav sidebar-subnav collapse">
                        <li id="activitymenuId">
                            <a href="#">
                                <span>Reports -1 </span>
                            </a>
                        </li>
                        <li id="healthmenuId">
                            <a href="#">
                                <span>Reports -1</span>
                            </a>
                        </li>
                        <li id="registrationmenuId">
                            <a href="#">
                                <span>Reports -1</span>
                            </a>
                        </li>
                        
                    </ul>
                </li>
                
                Notifications Starts
                 <li id="communicationsmenuId">
                    <a href="javascript:void(0)" data-toggle="collapse">
                        Bus Services
                    </a>
                    <ul class="nav sidebar-subnav collapse" >
                        <li id="notificationsmenuId">
                            <a href="#">
                                <span>Test -1</span>
                            </a>
                        </li>
                        <li id="templatelistmenuId">
                            <a href="#">
                                <span>Test -2</span>
                            </a>
                        </li>
                        <li id="distributionlistmenuId">
                            <a href="#">
                                <span>Test -3</span>
                            </a>
                        </li>
                    </ul>
                </li>
                 Notifications Ends
                 
                <li id="usersetupmenuId">
                    <a href="javascript:void(0)" data-toggle="collapse">
                        Students
                    </a>
                    <ul class="nav sidebar-subnav collapse" >
                        <li id="usersmenuId">
                            <a href="#">
                                <span>Girls</span>
                            </a>
                        </li>
                        <li id="relgfilemenuId">
                            <a href="#">
                                <span>Boys</span>
                            </a>
                        </li>
                    </ul>
                </li> -->
                                  
            </ul>
            <!-- END sidebar nav-->
			 
        </nav>
    </div>
    <!-- END Sidebar (left)-->
</aside> --%>