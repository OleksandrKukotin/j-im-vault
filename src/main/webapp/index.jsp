<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="import/header.jsp"></c:import>
<form name="addImageForm" method="post" action="imageUpload" enctype="multipart/form-data" style="display: flex; flex-direction: column;">
    <ul style="list-style-type: none">
        <li class="form-field">
            <input type="text" name="imageName" placeholder="image.Image name" size="25"/>
        </li>
        <li class="form-field">
            <input type="file" accept="image/*" name="imageFile"/>
        </li>
        <li class="form-field">
            <input type="submit" name="Submit" value="Upload image"/>
        </li>
    </ul>
</form>
