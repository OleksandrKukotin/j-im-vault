<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="import/header.jsp"></c:import>
<form name="addImageForm" method="post" action="imageUpload" enctype="multipart/form-data" class="styled-form">
    <ul class="form-list">
        <li class="form-field">
            <input type="text" name="imageName" placeholder="Image name" size="25" class="form-input"/>
        </li>
        <li class="form-field">
            <input type="file" accept="image/*" name="imageFile" class="form-input"/>
        </li>
        <li class="form-field">
            <input type="submit" name="Submit" value="Upload image" class="form-button"/>
        </li>
    </ul>
</form>
</main>
</body>
</html>