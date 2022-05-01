<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>View User Transactions</title>
<link href="<c:url value="/css/common.css"/>" rel="stylesheet"
  type="text/css">

<script type="text/javascript"
  src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!-- Datatables -->
<script type="text/javascript"
  src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
<link
  href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.2/css/bootstrap.css"
  rel="stylesheet">
<link
  href="https://cdn.datatables.net/1.11.5/css/dataTables.bootstrap4.min.css"
  rel="stylesheet">

<!-- jQuery Modal -->
<script
  src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.js"></script>
<link rel="stylesheet"
  href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.css" />
  <script type="text/javascript">
    $(function() {
        var table = $('#example')
                .DataTable(
                    {
                        "order": [[ 0, "desc" ]],
                        "ajax" : {
                            "url" : "/rest/users/${userId}/transactions/${accountId}",
                            "type" : "GET",
                            "datatype" : 'json',
                        },
                        columns : [
                                {
                                    'data' : 'date',
                                    render: function ( data, type, row ) {
                                    	return data + " " + row.time;
                                    }
                                }, {
                                    'data' : 'remarks'
                                }, {
                                    'data' : 'currency'
                                }, {
                                    'data' : 'debitOrCredit'
                                }, {
                                    'data' : 'balanceRunning'
                                },],
                    });
    });
</script>
</head>
<body>
  <h1>User Info</h1>
  [<a href="/users/${user.id}">Back to ${user.firstname} ${user.lastname}'s accounts</a>]
  <br><br>
  <table>
    <tr>
      <td valign="top"><table border="1">
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
    <table id="example" class="table table-striped table-bordered"
    style="width: 100%">
    <thead>
      <tr>
        <th>Date</th>
        <th>Remarks</th>
        <th>Currency</th>
        <th>Debit/Credit</th>
        <th>Running Balance</th>
      </tr>
    </thead>
  </table>
</body>
</html>