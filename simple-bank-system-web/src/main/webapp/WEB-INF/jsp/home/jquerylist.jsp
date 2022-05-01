<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>View Users</title>
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
		var table = $('#example').DataTable({
			"ajax" : {
				"url" : "/rest/users",
				"type" : "GET", // you can probably remove this
				"datatype" : 'json', // you can probably remove this
			},
			columns : [{
				'data' : 'email'
			}, {
				'data' : 'firstname'
			}, {
				'data' : 'lastname'
			}, ]
		});
		$('#example tbody').on('click', 'tr', function() {
			var data = table.row(this).data();
			$.get("/rest/users/"  + data['id'] + "/accounts", function(html) {
			    console.log(html);
				$("#modal").modal("show");
			});
		});
	});
</script>
</head>
<body>
  <!-- AJAX response must be wrapped in the modal's root class. -->
  <div class="modal" id="modal">
    <p>Second AJAX Example!</p>
  </div>
  <h1>View Users</h1>
  <table id="example"  class="table table-striped table-bordered" style="width: 100%">
    <thead>
      <tr>
        <th>Email</th>
        <th>Firstname</th>
        <th>Lastname</th>
      </tr>
    </thead>
    <tfoot>
      <tr>
        <th>Email</th>
        <th>Firstname</th>
        <th>Lastname</th>
      </tr>
    </tfoot>
  </table>
</body>
</html>