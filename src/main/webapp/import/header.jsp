<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Images Uploader</title>
    <style>
        .menu-item {
            padding: 1em;
        }

        .form-field {
            padding: .5em;
        }

        table,tr,td,th {
            border: 1px solid black;
        }
    </style>
    <%--    <script src="js/table.js"></script>--%>
</head>
<body>
<h1 style="display: flex; justify-content: center"> IU - Images Uploader </h1>
<h2 style="display: flex; justify-content: center"> Images Preview </h2>
<menu style="display: flex; justify-content: center; list-style-type: none; padding-inline-start: 0">
    <li class="menu-item">
        <a href="/imageUpload"> Upload Images </a>
    </li>
    <li class="menu-item">
        <a href="/imagesPreview"> View Images </a>
    </li>
</menu>