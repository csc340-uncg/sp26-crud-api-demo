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
  <div class="container" style="margin-top: 15px;">
    <h1>${title}</h1>
    <form id="new-student" action="/students/" method="post" enctype="multipart/form-data">
      <input type="text" id="name" name="name" placeholder="Name" />
      <input type="email" id="email" name="email" placeholder="example@domain.edu" />
      <input type="text" id="major" name="major" placeholder="Major" />
      <input type="number" id="gpa" name="gpa" step="0.01" placeholder="GPA" />
      <label for="picture" class="form-label"> Picture:</label>
      <input type="file" id="picture" name="picture" />
      <button type="submit" class="btn btn-primary">Save</button>
      <a href="/students" class="btn btn-outline-danger">Cancel</a>
    </form>

  </div>

</body>

</html>
