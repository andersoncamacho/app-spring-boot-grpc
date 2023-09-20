package br.com.communicationbetweensystems.grpc.service.impl;

import br.com.communicationbetweensystems.grpc.dto.ProductInputDTO;
import br.com.communicationbetweensystems.grpc.dto.ProductOutputDTO;
import br.com.communicationbetweensystems.grpc.exception.ProductAlreadyExistsException;
import br.com.communicationbetweensystems.grpc.exception.ProductNotFoundException;
import br.com.communicationbetweensystems.grpc.repository.ProductRepository;
import br.com.communicationbetweensystems.grpc.service.ProductService;
import br.com.communicationbetweensystems.grpc.util.ProductConverterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultProductService implements ProductService {

    @Autowired
    private final ProductRepository productRepository;

    public DefaultProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductOutputDTO create(ProductInputDTO inputDTO) {
        checkDuplicity(inputDTO.getName());
        var product = ProductConverterUtil.productInpuDtotProduct(inputDTO);
        var productCreated = this.productRepository.save(product);
        return ProductConverterUtil.productToProductOutPutDto(productCreated);
    }

    @Override
    public ProductOutputDTO findByID(Long id) {
        var product = this.productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException(id));
        return ProductConverterUtil.productToProductOutPutDto(product);
    }

    @Override
    public void delete(Long id) {
        var product = this.productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException(id));
        this.productRepository.delete(product);
    }

    @Override
    public List<ProductOutputDTO> findAll() {
        var products = this.productRepository.findAll();
        return products.stream()
                .map(ProductConverterUtil::productToProductOutPutDto)
                .collect(Collectors.toList());
    }

    private void checkDuplicity(String name) {
        this.productRepository.findByNameIgnoreCase(name)
                .ifPresent(e ->{
                    throw new ProductAlreadyExistsException(name);
                });
    }
}
