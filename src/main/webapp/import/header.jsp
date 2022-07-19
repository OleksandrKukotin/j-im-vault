<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Images Uploader</title>
    <style>
        menu {
            display: flex;
            justify-content: center;
            list-style-type: none;
            padding-inline-start: 0
        }

        table, tr, td, th {
            border: 1px solid black;
        }

        h1, h2, h3, h4 {
            display: flex;
            justify-content: center
        }

        .menu-item {
            padding: 1em;
        }

        .form-field {
            padding: .5em;
        }
    </style>
</head>
<body>
<h1> Images Uploader </h1>
<menu>
    <li class="menu-item">
        <a href="/imageUpload"> Upload Images </a>
    </li>
    <li class="menu-item">
        <a href="/imagesPreview"> Global TOP </a>
    </li>
    <li class="menu-item">
        <a href="/searchBySizeRange"> Search by size </a>
    </li>
</menu>