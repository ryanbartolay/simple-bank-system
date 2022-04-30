<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>View User Transactions</title>
<link href="<c:url value="/css/common.css"/>" rel="stylesheet"
  type="text/css">
</head>
<body>
  <h1>User Info</h1>
  <table>
    <tr>
      <td valign="top"><table border="1">
          <tbody>
            <tr>
              <td>Email</td>
              <td><a href="/users/${user.id}">${user.email}</a></td>
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
        </table></td>
      <td valign="top"><table border="1">
          <thead>
            <tr>
              <th>Type</th>
              <th>Balance</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>${account.type}<br>${account.id}</td>
              <td>${account.balance}</td>
            </tr>
          </tbody>
        </table></td>
    </tr>
  </table>
  <h1>Transactions</h1>
  <table border="1">
    <thead>
      <tr>
        <th>Date</th>
        <th>Remarks</th>
        <th>Currency</th>
        <th>Debit/Credit</th>
        <th>Running Balance</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${transactions}" var="transaction">
        <tr>
          <td align="center" width="100">${transaction.date}<br/>${transaction.time}</td>
          <td width="400">${transaction.remarks}</td>
          <td>${transaction.currency}</td>
          <td>${transaction.debitOrCredit}</td>
          <td>${transaction.balanceRunning}</td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</body>
</html>