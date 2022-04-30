<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>View Users</title>
<link href="<c:url value="/css/common.css"/>" rel="stylesheet"
  type="text/css">
</head>
<body>
  <c:if test="${transactionFailed}">
    <div style="color: red; font-weight: bold;">${failedMessage}</div>
  </c:if>
  <h1>Deposit</h1>
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
  <c:url var="depositUrl" value="/transaction/deposit" />
  <form:form action="${depositUrl}" method="post"
    modelAttribute="deposit">
    <form:input type="hidden" path="userId" value="${userId}" />
    <form:input type="hidden" path="accountId" value="${accountId}" />
    <form:input type="hidden" path="transactionId"
      value="${transactionId}" />
    <table>
      <tr>
        <td>Deposit amount</td>
        <td><input type="number" name="amount" /></td>
      </tr>
      <tr>
        <td colspan="2" align="center"><input type="submit"
          value="Cancel" name="transaction"> <input
          type="submit" value="Deposit" name="transaction"></td>
      </tr>
    </table>
  </form:form>
</body>
</html>