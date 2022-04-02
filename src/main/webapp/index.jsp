<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>SMU</title>
    <style>
        .list-item {
            padding: .5em;
        }
        .menu-item {
            padding: 1em;
        }
    </style>
</head>
<body>
<h1 style="display: flex; justify-content: center"> SMU - Some Media Uploader</h1>
    <menu style="display: flex; justify-content: center; list-style-type: none">
        <li class="menu-item">
            <a href="imageUpload.jsp"> Upload Images </a>
        </li>
        <li class="menu-item">
            <a href="index.jsp"> Upload Texts </a>
        </li>
    </menu>
    <form name="addTextForm" method="post" action="index" style="display: flex; flex-direction: column;">
        <ul style="list-style-type: none">
            <li class="list-item">
                <input type="text" name="textAuthor" placeholder="Text Author" size="25">
            </li>
            <li class="list-item">
                <input type="text" name="textBody" placeholder="Text" size="25">
            </li>
            <li class="list-item">
                <input type="submit" name="Submit" value="Add text">
            </li>
        </ul>
    </form>
</body>
</html>
