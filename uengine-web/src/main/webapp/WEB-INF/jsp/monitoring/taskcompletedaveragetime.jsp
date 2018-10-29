
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="width: 550px; height: 300px; overflow: hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/include/include-header_resource.jspf"%>
<%@ include file="/WEB-INF/include/include-flowchart_header_resource.jspf"%>
<%@ include file="/WEB-INF/include/include-monitoring.jspf"%>
<script type="text/javascript" src="<c:url value='/resources/js/monitoringDataSelectcanvas.js'/>"></script>
<style>
.ui-jqgrid-sortable {font-size: 12px;text-align: left}
.ui-jqgrid-labels .ui-th-column{border-right-width: 0px;  }
.ui-jqgrid tr.ui-row-ltr td {border-right-width: 0px;}
.ui-widget-content {background:#FFFFFF}
.ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default {background:#FFFFFF}
/*
*/
</style>

<script>
	
</script>
<title><spring:message code="menu.monitoring.content.tcat" /></title>
</head>
<body>
	<div id="chartContainer" style="width: 80%; height: 350px;"></div>

	<script>

	window.onload = function() {
		/*
		fn_chartjs('line', '<spring:message code="menu.monitoring.content.tcat" />',
				'taskcompletedaveragetime', '<spring:message code="menu.monitoring.label.week" />',
				'<spring:message code="menu.monitoring.content.completedaveragetime" />');
		*/
		fn_canvasline('taskcompletedaveragetime', 'chartContainer',
				'<spring:message code="menu.monitoring.content.completedaveragetime" />'); 
	};
		
	</script>
</body>

</html>