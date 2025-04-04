<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>Muji PTITHCM</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/product-detail.css"/>
  <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&display=swap" rel="stylesheet"/>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
<jsp:include page="/WEB-INF/common/header.jsp"/>

<c:if test="${not empty error}">
  <div class="alert alert-danger">${error}</div>
</c:if>

<main class="container product-detail-container">
  <div class="row">
    <div class="col-md-6 product-image">
      <img src="${product.thumbnail}" alt="Product Image" />
    </div>
    <div class="col-md-6 product-info">
      <h1 class="product-title">${product.name}</h1>
      <p class="product-price">${product.price} VNĐ</p>
      <p class="product-stock">Còn lại: ${product.stock_quantity} sản phẩm</p>
      <p class="product-description">${product.description}</p>

      <!-- Color Selection -->
      <div class="option-group">
        <label><strong>Màu sắc:</strong></label>
        <div class="color-options">
          <c:forEach items="${colors}" var="color">
            <button type="button" class="color-btn" onclick="updateSelectColor('${color}', this)">${color}</button>
          </c:forEach>
        </div>
      </div>

      <!-- Size Selection -->
      <div class="option-group">
        <label><strong>Kích thước:</strong></label>
        <div class="size-options">
          <c:forEach items="${sizes}" var="size">
            <button type="button" class="size-btn" onclick="updateSelectSize('${size}', this)">${size}</button>
          </c:forEach>
        </div>
      </div>

      <!-- Quantity Selection -->
      <div class="quantity-group">
        <label><strong>Số lượng</strong></label>
        <input type="number" class="quantity-input" name="quantity" value="1" min="1"/>
      </div>

      <!-- Add to Cart Form -->
      <div class="action-buttons">
        <form action="${pageContext.request.contextPath}/cart" method="post">
          <input type="hidden" name="productId" value="${product.id}"/>
          <input type="hidden" id="selectedColor" name="color" value=""/>
          <input type="hidden" id="selectedSize" name="size" value=""/>
          <input type="hidden" id="selectedQuantity" name="quantity" value="1"/>
          <button type="submit" class="btn btn-add-to-cart">Thêm vào giỏ hàng</button>
        </form>
      </div>
    </div>
  </div>
</main>

<jsp:include page="/WEB-INF/common/footer.jsp"/>

<script>
  function updateSelectSize(value, button) {
    let selectedSize = document.getElementById("selectedSize");
    if (!selectedSize) {
      console.error("Cannot allocate selectedSize element");
    }

    let buttons = document.querySelectorAll(`.size-btn`);
    buttons.forEach(btn => btn.classList.remove('active'));
    button.classList.add('active');

    selectedSize.value = value;
  }

  function updateSelectColor(value, button) {
    let selectedColor = document.getElementById("selectedColor");
    if (!selectedColor) {
      console.error("Cannot allocate selectedColor element");
    }

    let buttons = document.querySelectorAll(`.color-btn`);
    buttons.forEach(btn => btn.classList.remove('active'));
    button.classList.add('active');

    selectedColor.value = value;
  }

  document.querySelector('.quantity-input').addEventListener('input', function () {
    document.getElementById('selectedQuantity').value = this.value;
  });
</script>

<style>
  .color-btn, .size-btn {
    padding: 8px 16px;
    margin: 5px;
    border: 1px solid #ccc;
    background-color: white;
    cursor: pointer;
    transition: 0.3s;
  }
  .color-btn.active, .size-btn.active {
    background-color: #d4a373;
    color: white;
    border-color: #d4a373;
  }
</style>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
  window.onload = function() {
    let hasAlert = '<%= session.getAttribute("alert") %>';
    let alertMessage = '<%= session.getAttribute("alertMessage") %>';
    if (hasAlert === "true") {
      alert(alertMessage);
      <% session.removeAttribute("alert"); %>
      <% session.removeAttribute("alertMessage"); %>
    }
  };
</script>

</body>
</html>