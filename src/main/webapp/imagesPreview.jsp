<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Images Uploader</title>
    <style>
        .menu-item {
            padding: 1em;
        }

        table,tr,td {
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
        <a href="index.jsp"> Upload Images </a>
    </li>
    <li class="menu-item">
        <a href="imagesPreview.jsp"> View Images </a>
    </li>
</menu>
<table>
    <thead>
        <tr>
            <th>Image name</th>
            <th>Key</th>
        </tr>
    </thead>
    <tr>
        <td>Image Name</td>
        <td>Key</td>
    </tr>
    <c:forEach items="${imagesList}" var="item">
        <tr>
            <td>${item.getImageName()}</td>
            <td>${item.getKey()}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>