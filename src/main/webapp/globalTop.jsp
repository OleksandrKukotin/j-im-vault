<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="import/header.jsp"></c:import>
<p>${notFoundMessage}</p>
<table ${notFoundStyle}>
    <thead>
    <tr>
        <th>Name</th>
        <th>Size</th>
        <th>Date</th>
        <th>Image</th>
    </tr>
    </thead>
    <c:forEach items="${imageDtos}" var="imageDto">
        <tr>
            <td> ${imageDto.getName()} </td>
            <td> ${imageDto.getSize()/1000} KB</td>
            <td> ${imageDto.getFormattedCreatingTimestamp()} </td>
            <td>
                <img src="data:image/jpg;base64,${imageDto.getBase64Image()}" style="max-height: 100px; max-width: 100px" alt="${imageDto.getName()}"/>
            </td>
        </tr>
    </c:forEach>
</table>