<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Images Uploader</title>
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
<h1 style="display: flex; justify-content: center"> IU - Images Uploader</h1>
<menu style="display: flex; justify-content: center; list-style-type: none">
    <li class="menu-item">
        <a href="index.jsp"> Upload Images </a>
    </li>
    <li class="menu-item">
        <a> View Images </a>
    </li>
</menu>
<form name="addImageForm" method="post" action="imageUpload" enctype="multipart/form-data" style="display: flex; flex-direction: column;">
    <ul style="list-style-type: none">
        <li class="list-item">
            <input type="text" name="imageName" placeholder="Image name" size="25"/>
        </li>
        <li class="list-item">
            <input type="file" accept="image/*" name="imageFile"/>
        </li>
        <li class="list-item">
            <input type="submit" name="Submit" value="Upload image"/>
        </li>
    </ul>
</form>
</body>
</html>
