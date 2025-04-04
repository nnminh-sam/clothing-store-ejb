<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Muji PTITHCM</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <style>
        .sidebar {
            height: 100%;
            padding: 20px;
            border-right: 1px solid #ddd;
        }
        .content {
            padding: 20px;
        }
        .sidebar .nav-link.active {
            background-color: #f0f0f0;
            font-weight: bold;
        }
        .btn-custom {
            background-color: #007bff;
            color: white;
            border-radius: 20px;
            padding: 5px 12px;
            font-size: 14px;
            text-decoration: none;
            transition: background-color 0.3s ease;
        }
        .btn-custom:hover {
            background-color: #0056b3;
            color: white;
        }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/common/header.jsp"/>

<div class="container-fluid mt-4">
    <div class="row">
        <div class="col-md-2 sidebar">
            <h4>Tài khoản</h4>
            <div class="nav flex-column">
                <a class="nav-link" href="${pageContext.request.contextPath}/user">Thông tin cá nhân</a>
                <a class="nav-link active" href="${pageContext.request.contextPath}/user/orders">Đơn hàng</a>
            </div>
        </div>

        <div class="col-md-9 content">
            <h3>Danh sách đơn hàng</h3>

            <c:choose>
                <c:when test="${empty orders}">
                    <div class="alert alert-info">Bạn chưa có đơn hàng nào.</div>
                </c:when>
                <c:otherwise>
                    <table class="table table-bordered">
                        <thead class="table-light">
                        <tr>
                            <th>Mã đơn hàng</th>
                            <th>Ngày đặt</th>
                            <th>Trạng thái</th>
                            <th>Tổng tiền</th>
                            <th>Phương thức thanh toán</th>
                            <th>Thao tác</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${orders}" var="order">
                            <tr>
                                <td>${order.id}</td>
                                <td>${order.date}</td>
                                <td>${order.status}</td>
                                <td>${order.total_price} VNĐ</td>
                                <td>${order.paymentMethod}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/user/orders/detail?id=${order.id}"
                                       class="btn btn-outline-primary btn-sm">
                                        Xem chi tiết
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/common/footer.jsp"/>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>