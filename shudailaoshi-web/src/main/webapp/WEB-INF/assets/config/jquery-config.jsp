<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="org.apache.shiro.SecurityUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();%>
<%String wsPath = "ws://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();%>
<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
<meta name='viewport' content='width=device-width,initial-scale=1 user-scalable=0'>
<meta http-equiv='Widow-target' Content='_top'>
<link rel='shortcut icon' href='<%=basePath%>/favicon.ico'/>
<script>
(function(window){
	window.basePath = '<%=basePath%>';
	window.wsPath = '<%=wsPath%>';
	window.baseHost = '<%=request.getServerName() + ":" + request.getServerPort()%>';
	window.currentUserName = '<%=SecurityUtils.getSubject().getPrincipal()%>';
	window.hasCustomerRole = <%=SecurityUtils.getSubject().hasRole("customer")%>;
})(window);
</script>
<script src="<%=basePath%>/assets/front/jquery-1.11.0.min.js"></script>
<script src='<%=basePath%>/assets/lib/layer/layer.js'></script>
<script src='<%=basePath%>/assets/lib/require.js'></script>
<script src='<%=basePath%>/assets/config/require-config.js'></script>
<script src='<%=basePath%>/assets/config/jquery-config.js'></script>