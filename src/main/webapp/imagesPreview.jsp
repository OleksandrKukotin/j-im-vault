<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="import/header.jsp"></c:import>
<table>
    <thead>
    <tr>
        <th>Name</th>
        <th>Date</th>
        <th>Image</th>
    </tr>
    </thead>
    <c:forEach items = "${imagesList}" var = "image">
        <tr>
            <td> ${image.getName()} </td>
            <td> ${image.getTime()} </td>
            <td>
                <img src="data:image/jpg;base64,${image.getBase64Image()}" width="300" height="100" alt="${image.getName()}"/>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>