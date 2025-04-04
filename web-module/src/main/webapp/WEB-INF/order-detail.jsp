<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Chi tiết đơn hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>

<jsp:include page="/WEB-INF/common/header.jsp"/>

<div class="container mt-4">
    <h3>Chi tiết đơn hàng #${order.id}</h3>
    <p><strong>Người nhận:</strong> ${user.fullName}</p>
    <p><strong>Ngày đặt:</strong> ${order.date}</p>
    <p><strong>Trạng thái:</strong> ${order.status}</p>
    <p><strong>Phương thức thanh toán:</strong> ${order.paymentMethod}</p>
    <p><strong>Trạng thái thanh toán:</strong> ${order.paymentStatus}</p>
    <p><strong>Địa chỉ giao hàng:</strong> ${order.address}</p>
    <p><strong>Số điện thoại:</strong> ${order.phone}</p>

    <table class="table table-bordered mt-4">
        <thead class="table-light">
        <tr>
            <th>Sản phẩm</th>
            <th>Số lượng</th>
            <th>Đơn giá</th>
            <th>Thành tiền</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="detail" items="${order.orderDetails}">
            <tr>
                <td>${detail.productVariant.product.name} (${detail.productVariant.color}, ${detail.productVariant.size})</td>
                <td>${detail.quantity}</td>
                <td>${detail.price} VNĐ</td>
                <td>${detail.quantity * detail.price} VNĐ</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div class="text-end">
        <h5>Tổng tiền: <strong>${order.total_price} VNĐ</strong></h5>
    </div>

    <div class="mt-3">
        <a href="${pageContext.request.contextPath}/user/orders" class="btn btn-secondary">Quay lại</a>
    </div>
</div>

<jsp:include page="/WEB-INF/common/footer.jsp"/>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>