<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="import/header.jsp"></c:import>
<table style="align-content: center">
    <thead>
    <tr>
        <th>Name</th>
        <th>Size</th>
        <th>Date</th>
        <th>Image</th>
    </tr>
    </thead>
    <c:forEach items="${imagesList}" var="image">
        <tr>
            <td> ${image.getName()} </td>
            <td> ${image.getSize()/1000} KB</td>
            <td> ${image.getCreatingTimestamp()} </td>
            <td>
                <img src="data:image/jpg;base64,${image.getBase64Image()}" style="max-height: 100px; max-width: 100px" alt="${image.getName()}"/>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>