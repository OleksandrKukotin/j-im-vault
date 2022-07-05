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

        table,tr,td,th {
            border: 1px solid black;
        }
    </style>
    <%--    <script src="js/table.js"></script>--%>
</head>
<body>
<c:import url="import/header.jsp"></c:import>
<form action="imagesPreview" method="POST">
    <input type="submit">
</form>
<table>
    <thead>
    <tr>
        <th>Image name</th>
        <th>Key</th>
    </tr>
    </thead>
    <c:forEach items = "${imagesList}" var = "image">
        <tr>
            <td> ${image.getImageName()} </td>
            <td> ${image.getKey()} </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>