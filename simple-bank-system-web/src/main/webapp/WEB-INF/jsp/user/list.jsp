<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>View Users</title>
<link href="<c:url value="/css/common.css"/>" rel="stylesheet"
  type="text/css">
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