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
<form name="addImageForm" method="post" action="imageUpload" style="display: flex; flex-direction: column;">
    <ul style="list-style-type: none">
        <li class="list-item">
            <input type="file" accept="image/*" id="textAuthor" name="textAuthor" placeholder="Choose Image" size="25">
        </li>
        <li class="list-item">
            <input type="text" id="imageName" name="imageName" placeholder="Image name" size="25">
        </li>
        <li class="list-item">
            <input type="submit" name="Submit" value="Add text">
        </li>
    </ul>
</form>
</body>
</html>
