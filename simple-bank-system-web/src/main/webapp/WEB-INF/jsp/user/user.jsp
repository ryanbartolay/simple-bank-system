<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>View Users</title>
<link href="<c:url value="/css/common.css"/>" rel="stylesheet"
  type="text/css">
</head>
<body>
  <c:choose>
    <c:when test="${depositSuccess}">
      <div style="color: green; font-weight: bold;">Successfully
        withdraw: ${depostiDto.amount}</div>
    </c:when>
    <c:otherwise>
      <div style="color: red; font-weight: bold;">${depositDto.remarks}</div>
    </c:otherwise>
  </c:choose>
  <c:choose>
    <c:when test="${withdrawSuccess}">
      <div style="color: green; font-weight: bold;">Successfully
        withdraw: ${withdrawDto.amount}</div>
    </c:when>
    <c:otherwise>
      <div style="color: red; font-weight: bold;">${withdrawDto.remarks}</div>
    </c:otherwise>
  </c:choose>
  <h1>User Info</h1>
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
          <td>${account.type}<br>${account.id}</td>
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