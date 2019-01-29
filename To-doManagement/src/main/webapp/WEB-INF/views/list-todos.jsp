<%@include file="common/header.jspf" %>
<%@include file="common/nav.jspf" %>

	<div class="container">
		<table class="table table-striped">
			<caption>Your Todos are</caption>

			<thead>
				<tr>
					<th>Description</th>
					<th>Date</th>
					<th>Completed</th>
					<th>Update</th>
					<th>Delete</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach items="${todos}" var="todo">
					<tr>
						<td>${todo.desc}</td>
						<td><fmt:formatDate pattern="dd/mm/yyyy"
								value="${todo.targetDate}" /></td>
						<td>${todo.done}</td>
						<td><a class="btn btn-success"
							href="/update-todo?id=${todo.id}">Update</a></td>
						<td><a class="btn btn-danger"
							href="/delete-todo?id=${todo.id}">Delete</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div>
			<a class="btn btn-success" href="/add-todo">Add</a>
		</div>
	</div>

	<%@include file="common/footer.jspf" %>
</body>
</html>