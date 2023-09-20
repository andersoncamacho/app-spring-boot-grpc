package br.com.communicationbetweensystems.grpc.util;

import br.com.communicationbetweensystems.grpc.domain.Product;
import br.com.communicationbetweensystems.grpc.dto.ProductInputDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProductConverterUtilTest {

    @Test
    public void productToProductOutputDtoTest() {
        var product = new Product(1L, "product name", 10.00, 10);
        var productOutputDto = ProductConverterUtil.productToProductOutPutDto(product);

        Assertions.assertThat(product).usingRecursiveComparison().isEqualTo(productOutputDto);
    }

    @Test
    public void productInputToProductTest() {
        var productInput = new ProductInputDTO( "product name", 10.00, 10);
        var product = ProductConverterUtil.productInpuDtotProduct(productInput);

        Assertions.assertThat(productInput).usingRecursiveComparison().isEqualTo(product);
    }

}
