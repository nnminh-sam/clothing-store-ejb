<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Muji PTITHCM</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/cart.css"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>

<jsp:include page="/WEB-INF/common/header.jsp"/>

<main class="container mt-4">
    <h2 class="mb-4">Giỏ hàng của bạn</h2>

    <c:choose>
        <c:when test="${empty cart}">
            <div class="alert alert-info">Giỏ hàng của bạn đang trống.</div>
        </c:when>
        <c:otherwise>
            <form action="${pageContext.request.contextPath}/checkout" method="post">
                <table class="table table-bordered align-middle text-center">
                    <thead class="table-light">
                    <tr>
                        <th>Chọn</th>
                        <th>Ảnh</th>
                        <th>Tên sản phẩm</th>
                        <th>Màu sắc</th>
                        <th>Kích thước</th>
                        <th>Số lượng</th>
                        <th>Giá</th>
                        <th>Tổng</th>
                        <th>Thao tác</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${cart}" var="item">
                        <tr>
                            <td>
                                <input type="checkbox" name="cartIds" value="${item.id}"/>
                            </td>
                            <td><img src="${item.productVariant.product.thumbnail}" alt="Ảnh sản phẩm" style="width: 80px"/></td>
                            <td>${item.productVariant.product.name}</td>
                            <td>${item.productVariant.color}</td>
                            <td>${item.productVariant.size}</td>
                            <td>${item.quantity}</td>
                            <td>${item.productVariant.product.price} VNĐ</td>
                            <td>
                                <c:set var="total" value="${item.quantity * item.productVariant.product.price}" />
                                    ${total} VNĐ
                            </td>
                            <td>
                                <form action="${pageContext.request.contextPath}/cart" method="post">
                                    <input type="hidden" name="action" value="remove"/>
                                    <input type="hidden" name="cartId" value="${item.id}"/>
                                    <button type="submit" class="btn btn-sm btn-danger">Xoá</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <div class="row mb-3">
                    <div class="col-md-6">
                        <label for="address" class="form-label">Địa chỉ giao hàng</label>
                        <input type="text" class="form-control" id="address" name="address" required/>
                    </div>
                    <div class="col-md-6">
                        <label for="phone" class="form-label">Số điện thoại</label>
                        <input type="text" class="form-control" id="phone" name="phone" required/>
                    </div>
                </div>

                <div class="text-end mt-4">
                    <button type="submit" class="btn btn-success">Tiến hành thanh toán các mục đã chọn</button>
                </div>
            </form>
        </c:otherwise>
    </c:choose>
</main>

<jsp:include page="/WEB-INF/common/footer.jsp"/>

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