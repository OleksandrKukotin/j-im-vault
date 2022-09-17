<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="import/header.jsp"></c:import>
<h3 style="display: flex; justify-content: center">${message}</h3>
<menu>
    <li class="menu-item">
        <a href="/imagesPreview">View image</a>
    </li>
    <li class="menu-item">
        <a href="/imageUpload">Return to upload page</a>
    </li>
</menu>