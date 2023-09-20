package br.com.communicationbetweensystems.grpc.service.impl;

import br.com.communicationbetweensystems.grpc.domain.Product;
import br.com.communicationbetweensystems.grpc.dto.ProductInputDTO;
import br.com.communicationbetweensystems.grpc.dto.ProductOutputDTO;
import br.com.communicationbetweensystems.grpc.exception.ProductAlreadyExistsException;
import br.com.communicationbetweensystems.grpc.exception.ProductNotFoundException;
import br.com.communicationbetweensystems.grpc.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.groups.Tuple.tuple;

@ExtendWith(MockitoExtension.class)
public class DefaultProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private DefaultProductService defaultProductService;

    @Test
    @DisplayName("Quando o serviço de criação de produto é chamado com dados válidos, um produto é retornado")
    public void createProductSucessTest() {
        Product product = new Product(1L, "product name", 10.00, 10);

        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);

        ProductInputDTO inputDTO = new ProductInputDTO("product name", 10.00, 10);
        ProductOutputDTO outputDTO = defaultProductService.create(inputDTO);

        Assertions.assertThat(outputDTO)
                .usingRecursiveComparison()
                .isEqualTo(product);

    }

    @Test
    @DisplayName("Quando o serviço de criação de produto é chamado com nome duplicado, throw ProductAlreadyExistsException")
    public void createProductExceptionTest() {
        Product product = new Product(1L, "product name", 10.00, 10);

        Mockito.when(productRepository.findByNameIgnoreCase(Mockito.any())).thenReturn(Optional.of(product));

        ProductInputDTO inputDTO = new ProductInputDTO("product name", 10.00, 10);

        Assertions.assertThatExceptionOfType(ProductAlreadyExistsException.class)
                .isThrownBy(()-> defaultProductService.create(inputDTO));
    }

    @Test
    @DisplayName("Quando findById product chama id um id é retornado")
    public void findByIdSucessTest() {
        Long id = 1L;
        Product product = new Product(1L, "product name", 10.00, 10);

        Mockito.when(productRepository.findById(Mockito.any())).thenReturn(Optional.of(product));

        ProductOutputDTO outputDTO = defaultProductService.findByID(id);

        Assertions.assertThat(outputDTO)
                .usingRecursiveComparison()
                .isEqualTo(product);

    }

    @Test
    @DisplayName("Quando findById product chama id ivalid se torna uma exception, throws ProductNotFoundException")
    public void findByIdExceptionTest() {
        Long id = 1L;

        Mockito.when(productRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ProductNotFoundException.class)
                .isThrownBy(()-> defaultProductService.findByID(id));
    }

    @Test
    @DisplayName("when delete product is call with id should does not throw")
    public void deleteSuccessTest() {
        Long id = 1L;
        Product product = new Product(1L, "product name", 10.00, 10);

        Mockito.when(productRepository.findById(Mockito.any())).thenReturn(Optional.of(product));

        Assertions.assertThatNoException().isThrownBy(() -> defaultProductService.findByID(id));
    }

    @Test
    @DisplayName("when delete product is call with invalid id throws ProductNotFoundException")
    public void deleteExceptionTest() {
        Long id = 1L;

        Mockito.when(productRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ProductNotFoundException.class)
                .isThrownBy(() -> defaultProductService.delete(id));
    }

    @Test
    @DisplayName("when findAll product is call a list of product is returned")
    public void findAllSuccessTest() {
        List<Product> products = List.of(
                new Product(1L, "product name", 10.00, 10),
                new Product(2L, "other product name", 10.00, 100)
        );

        Mockito.when(productRepository.findAll()).thenReturn(products);

        List<ProductOutputDTO> outputDTOS = defaultProductService.findAll();

        Assertions.assertThat(outputDTOS)
                .extracting("id", "name", "price", "quantityInStock")
                .contains(
                        tuple(1L, "product name", 10.00, 10),
                        tuple(2L, "other product name", 10.00, 100)
                );
    }

}
