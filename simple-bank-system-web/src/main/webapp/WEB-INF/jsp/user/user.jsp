<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>View Users</title>
<link href="<c:url value="/css/common.css"/>" rel="stylesheet"
  type="text/css">
</head>
<body>
  <c:if test="${transactionSuccess}">
    <div style="color: green; font-weight: bold;">${successMessage}</div>
  </c:if>
  <h1>User Info</h1>
  [<a href="/users">Back to users</a>]
  <br><br>
  <table border="1">
    <tbody>
      <tr>
        <td>Email</td>
        <td>${user.email}</td>
      </tr>
      <tr>
        <td>First name</td>
        <td>${user.firstname}</td>
      </tr>
      <tr>
        <td>Last name</td>
        <td>${user.lastname}</td>
      </tr>
    </tbody>
  </table>
  <h4>Accounts</h4>
  <table border="1">
    <thead>
      <tr>
        <th>Type</th>
        <th>Balance</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${accounts}" var="account">
        <tr>
          <td>${account.type}<br>
          <a
            href="/users/${user.id}/transactions/${account.id}?page=0&size=5&sort=completedDate,desc">${account.id}</a></td>
          <td>${account.balance}</td>
          <td>
            <table border="1">
              <tr>
                <td><a
                  href="/users/${user.id}/withdraw/${account.id}">Withdraw</a></td>
                <td><a
                  href="/users/${user.id}/deposit/${account.id}">Deposit</a></td>
              </tr>
            </table>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</body>
</html>