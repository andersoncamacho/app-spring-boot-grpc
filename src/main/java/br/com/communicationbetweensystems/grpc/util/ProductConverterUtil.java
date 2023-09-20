package br.com.communicationbetweensystems.grpc.util;

import br.com.communicationbetweensystems.grpc.domain.Product;
import br.com.communicationbetweensystems.grpc.dto.ProductInputDTO;
import br.com.communicationbetweensystems.grpc.dto.ProductOutputDTO;

public class ProductConverterUtil {

    public static ProductOutputDTO productToProductOutPutDto(Product product) {
        return new ProductOutputDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantityInStock());
    }

    public static Product productInpuDtotProduct(ProductInputDTO product) {
        return new Product(
                null,
                product.getName(),
                product.getPrice(),
                product.getQuantityInStock());
    }

}
