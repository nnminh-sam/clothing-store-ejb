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
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/common/header.jsp"/>

<div class="container-fluid mt-4">
    <div class="row">
        <div class="col-md-2 sidebar">
            <h4>Tài khoản</h4>
            <div class="nav flex-column">
                <a class="nav-link active" href="#">Thông tin cá nhân</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/user/orders">Đơn hàng</a>
            </div>
        </div>

        <div class="col-md-9 content">
            <h3>Thông tin người dùng</h3>
            <table class="table table-bordered">
                <tr>
                    <th>Họ và tên</th>
                    <td>${user.fullName}</td>
                </tr>
                <tr>
                    <th>Email</th>
                    <td>${user.email}</td>
                </tr>
                <tr>
                    <th>Số điện thoại</th>
                    <td>${user.phone}</td>
                </tr>
                <tr>
                    <th>Địa chỉ</th>
                    <td>${user.address}</td>
                </tr>
                <tr>
                    <th>Ngày sinh</th>
                    <td>${user.birthday}</td>
                </tr>
                <tr>
                    <th>Giới tính</th>
                    <td>${user.gender}</td>
                </tr>
            </table>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/common/footer.jsp"/>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>