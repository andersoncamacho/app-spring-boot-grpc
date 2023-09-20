package br.com.communicationbetweensystems.grpc.service;

import br.com.communicationbetweensystems.grpc.dto.ProductInputDTO;
import br.com.communicationbetweensystems.grpc.dto.ProductOutputDTO;

import java.util.List;

public interface ProductService {
    ProductOutputDTO create(ProductInputDTO inputDTO);
    ProductOutputDTO findByID(Long id);
    void delete(Long id);
    List<ProductOutputDTO> findAll();
}
