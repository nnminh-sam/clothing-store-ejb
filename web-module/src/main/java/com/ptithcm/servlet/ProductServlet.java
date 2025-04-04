package com.ptithcm.servlet;

import com.ptithcm.ejb.entity.Product;
import com.ptithcm.ejb.entity.ProductVariant;
import com.ptithcm.ejb.service.ProductServiceRemote;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)

@WebServlet("/product")
public class ProductServlet extends HttpServlet {

    @EJB
    private ProductServiceRemote productService;

    @Override
    protected void doGet(
            HttpServletRequest request, HttpServletResponse response
    ) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/addProduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request, HttpServletResponse response
    ) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String uploadPath = request.getServletContext().getRealPath("/uploads");

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        try {
            String productName = request.getParameter("productName");
            String productDesc = request.getParameter("productDesc");
            String productBrand = request.getParameter("productBrand");
            String productCategory = request.getParameter("productCategory");
            double productPrice = Double.parseDouble(request.getParameter("productPrice"));
            int productStock = Integer.parseInt(request.getParameter("productStock"));

            Part thumbnailPart = request.getPart("productThumbnail");
            String thumbnailFileName = Paths.get(thumbnailPart.getSubmittedFileName()).getFileName().toString();
            String thumbnailPath = "uploads/" + thumbnailFileName;
            if (!thumbnailFileName.isEmpty()) {
                thumbnailPart.write(uploadPath + File.separator + thumbnailFileName);
            }

            List<String> detailImagePaths = new ArrayList<>();
            for (Part part: request.getParts()) {
                if (part.getName().equals("productImages") && part.getSize() > 0) {
                    String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                    String filePath = "uploads/" + fileName;
                    part.write(uploadPath + File.separator + fileName);
                    detailImagePaths.add(filePath);
                }
            }

            Product product = new Product();
            product.setName(productName);
            product.setDescription(productDesc);
            product.setBrand(productBrand);
            product.setCategory(productCategory);
            product.setPrice(String.valueOf(productPrice));
            product.setStock_quantity(productStock);
            product.setThumbnail(thumbnailPath);
            product.setImages(detailImagePaths);

            String[] colors = request.getParameterValues("color");
            String[] sizes = request.getParameterValues("size");
            String[] prices = request.getParameterValues("price");
            String[] quantities = request.getParameterValues("quantity");

            List<ProductVariant> variants = new ArrayList<>();
            if (colors != null) {
                for (int i = 0; i < colors.length; i++) {
                    ProductVariant variant = new ProductVariant();
                    variant.setColor(colors[i]);
                    variant.setSize(sizes[i]);
                    variant.setPrice(prices[i].isEmpty() ? productPrice : Double.parseDouble(prices[i]));
                    variant.setQuantity(Integer.parseInt(quantities[i]));

                    Part imagePart = request.getPart("image" + i);
                    if (imagePart != null && imagePart.getSize() > 0) {
                        String imageFileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
                        String imagePath = "uploads/" + imageFileName;
                        imagePart.write(uploadPath + File.separator + imageFileName);
                        variant.setImage(imagePath);
                    }

                    variant.setProduct(product);
                    variants.add(variant);
                }
            }

            product.setProductVariantList(variants);
            productService.create(product);

            response.sendRedirect(request.getContextPath() + "/product");

        } catch (Exception e) {
            request.setAttribute("error", "Lỗi khi lưu sản phẩm: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/addProduct.jsp").forward(request, response);
        }
    }

}
