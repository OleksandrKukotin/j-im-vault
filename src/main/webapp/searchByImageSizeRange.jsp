<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="import/header.jsp"></c:import>
<h2>Search Images by Size Range</h2>
<form name="searchBySizeRangeForm" action="searchBySizeRange" method="POST" class="styled-form">
    <ul class="form-list">
        <li>
            <p>Please, enter the range sizes of images in kilobytes (KB)!</p>
            <p><b>The min size must be smaller than the max size!</b></p>
        </li>
        <li class="form-field">
            <label for="max">Maximal size:</label>
            <input type="number" id="max" name="to" min="0" max="2097.152" value="2097" size="25" class="form-input"/>
        </li>
        <li class="form-field">
            <label for="min">Minimal size:</label>
            <input type="number" id="min" name="from" min="0" max="2097.151" value="0" size="25" class="form-input"/>
        </li>
        <li class="form-field">
            <input type="submit" name="Submit" value="Search" class="form-button"/>
        </li>
    </ul>
</form>
</main>
</body>
</html>