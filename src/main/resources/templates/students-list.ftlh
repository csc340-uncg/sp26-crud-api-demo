<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" type="text/css" href="/css/style.css">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
    integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
  <title>${title}</title>
</head>

<body>
  <!-- Begin Nav Bar -->
  <nav class="navbar navbar-expand-lg  navbar-dark" style="background-color: #799351;">
    <a class="navbar-brand" href="/students" style="margin: 10px;">
      <img src="/logo.jpg" alt="" width="80" height="60">
    </a>
    <ul class="nav justify-content-end">
      <li class="nav-item">
        <a class="nav-link active" aria-current="page" href="/students">Home</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">Second</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">Third</a>
      </li>
    </ul>
  </nav>
  <!-- End Nav Bar -->
  <div class="container" style="margin-top: 15px;">
    <h1>${title}</h1>
    <a href="/students/add" class="btn btn-outline-primary" style="float:right;">Create New Student</a>

    <form action="/students/search">
      <input type="text" name="name" placeholder="Search By Name &#128270;">
    </form>

    <div class="row  row-cols-3">
      <#list studentsList as student>
        <div class="col">
          <div class="card shadow-sm" style="margin-bottom: 15px;">
            <img src="/profile-pictures/${(student.profilePicturePath)!'avatar.png'}" class="card-img-top" alt="Profile Picture" style="height: 200px; object-fit: cover;">
            <div class="card-body">
              <h5 class="card-title">${student.name}</h5>
              <a href="/students/major/${student.major}" class="card-link">${student.major}</a>
            </div>
            <a href="/students/${student.studentId?c}" class="btn btn-sm btn-outline-primary">View Details</a>
          </div>
        </div>
      </#list>

    </div>
    <!-- Students Table -->
    <table class="table table-striped rounded-3 overflow-hidden">
      <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Major</th>
      </tr>
      <#list studentsList as student>
        <tr>
          <td>
            <a href="/students/${student.studentId?c}" style="color:#000000">${student.studentId?c}</a>
          </td>
          <td>${student.name}</td>
          <td>
            <a href="/students/major/${student.major}" style="color:#000000">${student.major}</a>
          </td>
        </tr>
      </#list>
    </table>

  </div>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
    integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
    crossorigin="anonymous"></script>
</body>

</html>
