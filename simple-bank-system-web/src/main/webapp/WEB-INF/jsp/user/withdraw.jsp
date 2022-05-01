<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Withdrawal for account ${user.id}</title>
<script type="text/javascript"
  src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link href="<c:url value="/css/common.css"/>" rel="stylesheet"
  type="text/css">
  <link
  href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.2/css/bootstrap.css"
  rel="stylesheet">
<link
  href="https://cdn.datatables.net/1.11.5/css/dataTables.bootstrap4.min.css"
  rel="stylesheet">
</head>
<body>
  <c:if test="${transactionFailed}">
    <div style="color: red; font-weight: bold;">${failedMessage}</div>
  </c:if>
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
  <c:url var="withdrawUrl" value="/rest/transaction/withdraw" />
  <div id="transactionMessage"></div>
  <form:form action="${withdrawUrl}" method="post"
    modelAttribute="withdraw" id="withdrawalForm">
    <form:input type="hidden" path="userId" value="${userId}"
      id="userId" />
    <form:input type="hidden" path="accountId" value="${accountId}"
      id="accountId" />
    <form:input type="hidden" path="transactionId" id="transactionId"
      value="${transactionId}" />
    <table>
      <tr>
        <td>Withdraw amount</td>
        <td><input type="number" id="amount" name="amount" /> <span
          id="amountErrors" style="color: red;"></span></td>
      </tr>
      <tr>
        <td colspan="2" align="center"><button 
          name="transaction" value="CANCEL" id="submitCancelButton">Cancel</button>
          <button name="transaction" value="WITHDRAW"
          id="submitWithdawButton">Withdraw</button></td>
      </tr>
    </table>
  </form:form>
  <script type="text/javascript">
  $(function () {
      $("#withdrawalForm").submit(function(e){
          e.preventDefault();
          var btnClicked = $(e.originalEvent.submitter);
          disableComponents(true);
          var action = $("#withdrawalForm").attr("action");
          var type = $(this).attr("value");
          var formData = {
            userId: $("#userId").val(),
            accountId: $("#accountId").val(),
            transactionId: $("#transactionId").val(),
            amount: $("#amount").val(),
            type: btnClicked.val(),
          };
          $.ajax({
            type: "POST",
            url: action,
            data: JSON.stringify(formData),
            dataType: "json",
            contentType: 'application/json',
            error: function (data, status, error) {
              var json = data.responseJSON;
              $.each(json.errorMessages, (index, item) => {
                $("#" + item.fieldName + "Errors").append(item.message + "<br>");
              });
              disableComponents(false);
              renewTransaction();
            },
            success: function (data, status) {
              if (data.status == "COMPLETED") {
                $("#transactionMessage").append("<span style=\"color: green\">Withdrawal Completed</span>");
                setTimeout(function () {
                  window.location.replace("/users/${userId}");
                }, 1300);
                return;
              }
              window.location.replace("/users/${userId}");
            }
          });
      });
  function renewTransaction() {
    $.ajax({
      type: "POST",
      url: "/rest/transaction/new",
      data: JSON.stringify({
        userId: "${userId}",
        accountId: "${accountId}",
        type: "WITHDRAW"
      }),
      dataType: "json",
      contentType: 'application/json',
      error: function (data, status, error) {
        $("#transactionMessage").append("<span style=\"color: red\">Unable to get new transaction.</span>");
        setTimeout(function () {
          window.location.replace("/users/${userId}");
        }, 2000);
      },
      success: function (data, status) {
        $("#transactionId").val(data.id)
      }
    });
  }
  function disableComponents(disable) {
    $("#amount").attr("disabled", disable);
    $("#submitCancelButton").attr("disabled", disable);
    $("#submitWithdawButton").attr("disabled", disable);
    if (!disable) {
      $("#amount").val("");
    } else {
      $("#amountErrors").empty();
    }
  }
});
</script>
</body>
</html>