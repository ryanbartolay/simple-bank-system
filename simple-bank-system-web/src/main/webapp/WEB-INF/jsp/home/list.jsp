<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>View Users</title>
<script type="text/javascript"
  src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript"
  src="https://code.jquery.com/ui/1.13.1/jquery-ui.min.js"></script>
<link
  href="http://code.jquery.com/ui/1.13.1/themes/ui-lightness/jquery-ui.css"
  rel="stylesheet">
<script type="text/javascript">
	$(function() {
		$.ajax({
			url : "demo_test.txt",
			success : function(result) {
				$("#div1").html(result);
			}
		});
	});
</script>
</head>
<body>
  <h1>View Users</h1>
  <table border="1">
    <thead>
      <tr>
        <th>Email</th>
        <th>Firstname</th>
        <th>Lastname</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${users}" var="user">
        <tr>
          <td><a href="/users/${user.id}">${user.email}</a></td>
          <td>${user.firstname}</td>
          <td>${user.lastname}</td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</body>
</html>