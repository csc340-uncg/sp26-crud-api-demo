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
      <#if Session.SPRING_SECURITY_CONTEXT??>
        <li class="nav-item">
          <a class="nav-link" href="/logout">(${Session.SPRING_SECURITY_CONTEXT.authentication.name}) Sign Out</a>
        </li>
      </#if>
      <#if !Session.SPRING_SECURITY_CONTEXT??>
        <li class="nav-item">
          <a class="nav-link" href="/login">Sign In</a>
        </li>
      </#if>
    </ul>
  </nav>
  <!-- End Nav Bar -->
  <div class="container" style="margin-top: 15px;">
    <h1>${title} <span style="float:right"> <a href="/students" class="btn btn-primary">Go back</a></span></h1>
      <#if RequestParameters.success??>
    <div class="alert alert-success alert-dismissible fade show" role="alert">
      Student updated successfully!
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  </#if>
  <#if RequestParameters.error??>
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
      Error updating student!
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  </#if>
    <div class="card">
      <h5 class="card-header">${student.name}</h5>
      <img src="/profile-pictures/${(student.profilePicturePath)!'avatar.png'}" class="card-img-top"
        style="height: 100px; width: 100px;" alt="profile picture">
      <div class="card-body">
        <h5 class="card-title">ID: ${student.studentId?c}</h5>
        <p class="card-text">Email: ${student.email}</p>
        <p class="card-text">Name: ${student.name}</p>
        <p class="card-text">Major: ${student.major}</p>
        <p class="card-text">GPA: ${student.gpa}</p>
      </div>
    </div>
    <div class="alert alert-primary" role="alert" style="margin-top: 10px;"> Edit Student</div>
    <form id="edit-student" action="/students/update/${student.studentId?c}" method="post"
      enctype="multipart/form-data">
      <input type="text" id="studentId" name="studentId" value="${student.studentId?c}" readonly />
      <input type="text" id="name" name="name" value="${student.name}" placeholder="Name" />
      <input type="email" id="email" name="email" value="${student.email}" placeholder="example@domain.edu" />
      <input type="text" id="major" name="major" value="${student.major}" placeholder="Major" />
      <input type="number" id="gpa" name="gpa" step="0.01" value="${student.gpa}" placeholder="GPA" /><br>
      <select id="role" name="role">
        <option value="USER">USER</option>
        <option value="MOD">MOD</option>
      </select>
      <label for="picture" class="form-label"> Picture:</label>
      <input type="file" id="picture" name="picture" />
      <button type="submit" class="btn btn-primary">Save</button>
    </form>
    <div class="alert alert-danger" role="alert">
      Delete Student
      <a style="float: right;" href="/students/delete/${student.studentId?c}" class="btn btn-danger">Delete</a>
    </div>
  </div>
   <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

</body>

</html>