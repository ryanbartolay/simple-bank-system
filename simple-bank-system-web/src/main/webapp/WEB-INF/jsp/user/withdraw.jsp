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
  <h1>Withdraw</h1>
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
  <form:form action="${add_book_url}" method="post"
    modelAttribute="transaction">
    <form:input type="text" path="userId" value="${user.id}"/>
    <form:input type="text" path="accountId"  value="${account.id}"/>
    <form:input type="text" path="transactionId"  value="${transaction.id}"/>
    <table>
      <tr>
        <td>Withdraw amount</td>
        <td><input type="number" name="amount" /></td>
      </tr>
      <tr>
        <td colspan="2" align="center">
            <input type="button" value="Cancel"> <input type="button" value="Withdraw">
        </td>
      </tr>
    </table>
  </form:form>
  <br />
  <br />
  <span>TX ID ${transaction.id}</span>
</body>
</html>