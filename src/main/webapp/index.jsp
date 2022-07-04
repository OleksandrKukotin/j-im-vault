<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Images Uploader</title>
    <style>
        .form-field {
            padding: .5em;
        }
        .menu-item {
            display: flex;
            padding: 1em;
        }
    </style>
</head>
<body>
<c:import url="import/header.jsp"></c:import>
<form name="addImageForm" method="post" action="imageUpload" enctype="multipart/form-data" style="display: flex; flex-direction: column;">
    <ul style="list-style-type: none">
        <li class="form-field">
            <input type="text" name="imageName" placeholder="Image name" size="25"/>
        </li>
        <li class="form-field">
            <input type="file" accept="image/*" name="imageFile"/>
        </li>
        <li class="form-field">
            <input type="submit" name="Submit" value="Upload image"/>
        </li>
    </ul>
</form>
</body>
</html>
