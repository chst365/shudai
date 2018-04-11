<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="org.apache.shiro.SecurityUtils" session="false"%>
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
	window.currentUserName = '<%=SecurityUtils.getSubject().getPrincipal()%>';
})(window);
</script>
<!-- css -->
<link href='<%=basePath%>/assets/icon/font-awesome-4.5.0/css/font-awesome.css' rel='stylesheet'/>
<link href='<%=basePath%>/assets/icon/icons.css' rel='stylesheet'/>
<!-- ext -->
<link href='<%=basePath%>/assets/lib/ext-6.2.0/build/classic/theme-crisp/resources/theme-crisp-all.css' rel='stylesheet'/>
<link href='<%=basePath%>/assets/lib/ext-6.2.0/build/packages/ux/classic/crisp/resources/ux-all.css' rel='stylesheet'/>
<script src='<%=basePath%>/assets/lib/ext-6.2.0/ext-bootstrap.js'></script>
<script src='<%=basePath%>/assets/lib/ext-6.2.0/build/classic/locale/locale-zh_CN.js'></script>
<!-- require -->
<script src='<%=basePath%>/assets/lib/require.js'></script>
<script src='<%=basePath%>/assets/config/require-config.js'></script>
<script src='<%=basePath%>/assets/config/ext-config.js'></script>